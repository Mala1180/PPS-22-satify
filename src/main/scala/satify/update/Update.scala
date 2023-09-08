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

import scala.io.Source

object Update:

  def update(model: State, message: Message): State =
    message match
      case Input(char) => model
      case Solve(input) =>
        try
          val exp = reflect(input)
          val sol: Solution = Solver(DPLL).solve(exp)
          State(input, exp, sol)
        catch
          case _: Exception =>
            State(input, InvalidInput)
      case SolveProblem(problem, parameter) =>
        try
          val exp: Expression = problem match
            case NQueensChoice => NQueens(parameter).exp
            case GraphColoring => ??? // GraphColoring(parameter).exp
            case NurseScheduling => ??? // NurseScheduling(parameter).exp
          val cnf: CNF = Converter(Tseitin).convert(exp)
          State(cnf, Solver().solve(exp), NQueens(parameter))
        catch
          case e: Exception =>
            State(InvalidInput)
      case Convert(input) =>
        try
          val exp = reflect(input)
          val cnf: CNF = Converter(Tseitin).convert(exp)
          State(input, exp, cnf)
        catch
          case e: Exception =>
            State(input, InvalidInput)
      case Import(file) =>
        try
          val s: Source = Source.fromFile(file)
          val lines = s.getLines.toSeq
          s.close()
          val cnf: CNF = parse(lines).getOrElse(Symbol(Variable("NO CNF")))
          val input = cnf.printAsDSL()
          State(input, cnf)
        catch
          case e: Exception =>
            State(InvalidImport)
      case NextSolution =>
        try
          State(
            model.input.get,
            model.expression.get,
            Solution(
              model.solution.get.result,
              model.solution.get.assignment.tail ::: List(model.solution.get.assignment.head)
            )
          )
        catch
          case e: Exception =>
            State(EmptySolution)
