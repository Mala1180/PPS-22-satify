package satify.update

import satify.dsl.Reflection.reflect
import satify.model.cnf.CNF
import satify.model.cnf.CNF.Symbol
import satify.model.errors.Error
import satify.model.errors.Error.*
import satify.model.problems.Problem
import satify.model.{Assignment, Solution, State}
import satify.update.Message.*
import satify.update.Utils.Chronometer
import satify.update.converters.Converter
import satify.update.converters.ConverterType.*
import satify.update.parser.DimacsParser.*
import satify.update.solver.Solver
import satify.update.solver.SolverType.*

import java.io.{File, FileNotFoundException}
import scala.io.Source
import scala.util.Success

object Update:

  /** Update function to react to a message.
    * @param model current state.
    * @param message message to react to.
    * @return a new state.
    */
  def update(model: State, message: Message): State = message match
    case SolveAll(input) => solveAllUpdate(input)
    case Solve(input) => solveUpdate(input)
    case SolveProblem(problem) => problemUpdate(problem)
    case Convert(input) => converterUpdate(input)
    case ConvertProblem(problem) => converterProblemUpdate(problem)
    case Import(file) => importUpdate(file)
    case NextSolution() => nextSolutionUpdate(model)

  /** Safely update the model by catching any exceptions and returning an error state.
    * @param f function to execute.
    * @param input optional input to return if an exception is thrown. For example, if the input is invalid, return the input.
    * @return a state with the error and input if an exception is thrown, otherwise the state returned by f.
    */
  private def safeUpdate(f: () => State, input: Option[String] = None): State =
    try Success(f()).get
    catch
      case e: Throwable =>
        e.printStackTrace()
        e match
          case _: IllegalArgumentException => State(input.getOrElse(""), InvalidInput())
          case _: IllegalStateException => State(NoPreviousSolution())
          case _: FileNotFoundException => State(InvalidImport())
          case _: StackOverflowError => State(StackOverflow())
          case _ => State(input.getOrElse(""), Unknown())

  /** Update function to react to the SolveAll message. This function will attempt to solve the input and return a state.
    * @param input input to solve
    * @return a state with the input, expression, and solution if no exception is thrown, otherwise a state with the input and the occurred error
    */
  private def solveAllUpdate(input: String): State =
    val update: () => State = () =>
      val exp = reflect(input)
      Chronometer.start()
      val sol: Solution = Solver(DPLL).solveAll(exp, true)
      Chronometer.stop()
      State(input, exp, sol, Chronometer.elapsed())
    safeUpdate(update, Some(input))

  /** Update function to react to the Solve message. This function will attempt to solve the input and return a state.
    * @param input input to solve
    * @return a state with the input, expression, and solution if no exception is thrown, otherwise a state with the input and the occurred error
    */
  private def solveUpdate(input: String): State =
    val update: () => State = () =>
      val exp = reflect(input)
      Chronometer.start()
      val sol: Solution = Solver(DPLL).solve(exp)
      Chronometer.stop()
      State(input, exp, sol, Chronometer.elapsed())
    safeUpdate(update, Some(input))

  /** Update function to react to the SolveProblem message. This function will attempt to solve the problem and return a state.
    * @param problem problem to solve.
    * @return a state with the cnf and solution if no exception is thrown, otherwise a state with the error.
    */
  private def problemUpdate(problem: Problem): State =
    val update: () => State = () =>
      Chronometer.start()
      val sol = Solver(DPLL).solve(problem.exp)
      Chronometer.stop()
      State(sol, problem, Chronometer.elapsed())
    safeUpdate(update)

  /** Update function to react to the Convert message. This function will attempt to convert the input and return a state.
    * @param input input to convert.
    * @return a state with the input, expression, and cnf if no exception is thrown, otherwise a state with the input and the occurred error
    */
  private def converterUpdate(input: String): State =
    val update: () => State = () =>
      val exp = reflect(input)
      Chronometer.start()
      val cnf: CNF = Converter(Tseitin).convert(exp)
      Chronometer.stop()
      State(input, exp, cnf, Chronometer.elapsed())
    safeUpdate(update, Some(input))

  /** Update function to react to the ConvertProblem message. This function will attempt to convert the selected problem and return a state.
    * @param problem problem to convert.
    * @return a state with the input, expression, and cnf if no exception is thrown, otherwise a state with the input and the occurred error
    */
  private def converterProblemUpdate(problem: Problem): State =
    val update: () => State = () =>
      Chronometer.start()
      val cnf: CNF = Converter(Tseitin).convert(problem.exp)
      Chronometer.stop()
      State(cnf, problem, Chronometer.elapsed())
    safeUpdate(update)

  /** Update function to react to the Import message. This function will attempt to import the file and return a state.
    * @param file file to import.
    * @return a state with the input and cnf if no exception is thrown, otherwise a state with the error.
    */
  private def importUpdate(file: File): State =
    val update: () => State = () =>
      val s: Source = Source.fromFile(file)
      val lines = s.getLines.toSeq
      s.close()
      val cnf: CNF = parse(lines).getOrElse(Symbol("NO CNF"))
      val input = cnf.asDSL()
      State(input, cnf)
    safeUpdate(update)

  /** Update function to react to the NextSolution message. This function will attempt to find the next solution and return a state.
    * @param currentState current state.
    * @return a state with the input, expression, and solution if no exception is thrown, otherwise a state with the error.
    */
  private def nextSolutionUpdate(currentState: State): State =
    val update: () => State = () =>
      Chronometer.start()
      val optNext: Option[Assignment] = Solver(DPLL).next
      Chronometer.stop()
      optNext match
        case None => currentState
        case Some(next) =>
          val sol = Solution(
            currentState.solution.get.result,
            currentState.solution.get.status,
            currentState.solution.get.assignments :+ next
          )
          if currentState.problem.isDefined then State(sol, currentState.problem.get, Chronometer.elapsed())
          else State(currentState.input.get, currentState.expression.get, sol, Chronometer.elapsed())
    safeUpdate(update)
