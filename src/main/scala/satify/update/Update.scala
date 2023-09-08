package satify.update

import satify.dsl.Reflection.reflect
import satify.model.*
import satify.model.CNF.Symbol
import satify.model.errors.Error
import satify.model.errors.Error.*
import satify.model.expression.Expression
import satify.model.problems.NQueens.*
import satify.model.problems.ProblemChoice.{GraphColoring, NurseScheduling, NQueens as NQueensChoice}
import satify.model.problems.{NQueens, ProblemChoice}
import satify.update.Message.*
import satify.update.converters.Converter
import satify.update.converters.ConverterType.*
import satify.update.parser.DimacsCNF.*
import satify.update.solver.Solver
import satify.update.solver.SolverType.*

import java.io.File
import scala.io.Source

object Update:

  /** Update function to react to a message.
    * @param model current state.
    * @param message message to react to.
    * @return a new state.
    */
  def update(model: State, message: Message): State =
    message match
      case Input(char) => model
      case Solve(input) =>
        solveUpdate(input)
      case SolveProblem(problem, parameter) =>
        problemUpdate(problem, parameter)
      case Convert(input) =>
        converterUpdate(input)
      case Import(file) =>
        importUpdate(file)
      case NextSolution =>
        nextSolutionUpdate(model)

  /** Safely update the model by catching any exceptions and returning an error state.
    * @param f function to execute.
    * @param error error to return if an exception is thrown. For example, if the input is invalid, return InvalidInput.
    * @param input optional input to return if an exception is thrown. For example, if the input is invalid, return the input.
    * @return a state with the error and input if an exception is thrown, otherwise the state returned by f.
    */
  private def safeUpdate(f: () => State, error: Error, input: Option[String] = None): State =
    try f()
    catch
      case _: Exception =>
        if input.isEmpty then State(error) else State(input.get, error)

  /** Update function to react to the Solve message. This function will attempt to solve the input and return a state.
    * @param input input to solve
    * @return a state with the input, expression, and solution if no exception is thrown, otherwise a state with the input and the occurred error
    */
  private def solveUpdate(input: String): State =
    safeUpdate(
      () =>
        val exp = reflect(input)
        val sol: Solution = Solver(DPLL).solve(exp)
        State(input, exp, sol)
      ,
      InvalidInput,
      Some(input)
    )

  /** Update function to react to the SolveProblem message. This function will attempt to solve the problem and return a state.
    * @param problem problem to solve.
    * @param parameter parameter for the problem.
    * @return a state with the cnf and solution if no exception is thrown, otherwise a state with the error.
    */
  private def problemUpdate(problem: ProblemChoice, parameter: Int): State =
    safeUpdate(
      () =>
        val exp: Expression = problem match
          case NQueensChoice => NQueens(parameter).exp
          case GraphColoring => ??? // GraphColoring(parameter).exp
          case NurseScheduling => ??? // NurseScheduling(parameter).exp
        val cnf: CNF = Converter(Tseitin).convert(exp)
        State(cnf, Solver(DPLL).solve(exp), NQueens(parameter))
      ,
      InvalidInput
    )

  /** Update function to react to the Convert message. This function will attempt to convert the input and return a state.
    * @param input input to convert.
    * @return a state with the input, expression, and cnf if no exception is thrown, otherwise a state with the input and the occurred error
    */
  private def converterUpdate(input: String): State =
    safeUpdate(
      () =>
        val exp = reflect(input)
        val cnf: CNF = Converter(Tseitin).convert(exp)
        State(input, exp, cnf)
      ,
      InvalidInput,
      Some(input)
    )

  /** Update function to react to the Import message. This function will attempt to import the file and return a state.
    * @param file file to import.
    * @return a state with the input and cnf if no exception is thrown, otherwise a state with the error.
    */
  private def importUpdate(file: File): State =
    safeUpdate(
      () =>
        val s: Source = Source.fromFile(file)
        val lines = s.getLines.toSeq
        s.close()
        val cnf: CNF = parse(lines).getOrElse(Symbol(Variable("NO CNF")))
        val input = cnf.printAsDSL()
        State(input, cnf)
      ,
      InvalidImport
    )

  /** Update function to react to the NextSolution message. This function will attempt to find the next solution and return a state.
    * @param currentState current state.
    * @return a state with the input, expression, and solution if no exception is thrown, otherwise a state with the error.
    */
  private def nextSolutionUpdate(currentState: State): State =
    if currentState.problem.isDefined then
      safeUpdate(
        () =>
          State(
            Solution(
              currentState.solution.get.result,
              currentState.solution.get.assignment.tail ::: List(currentState.solution.get.assignment.head)
            ),
            currentState.problem.get
          ),
        EmptySolution
      )
    else
      safeUpdate(
        () =>
          State(
            currentState.input.get,
            currentState.expression.get,
            Solution(
              currentState.solution.get.result,
              currentState.solution.get.assignment.tail ::: List(currentState.solution.get.assignment.head)
            )
          ),
        EmptySolution
      )
