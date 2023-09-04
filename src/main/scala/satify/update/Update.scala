package satify.update

import satify.dsl.Reflection.reflect
import satify.model.*
import satify.model.CNF.Symbol
import satify.model.expression.Expression
import satify.model.problems.NQueens.*
import satify.model.problems.ProblemChoice.{GraphColoring, NurseScheduling, NQueens as NQueensChoice}
import satify.model.problems.{NQueens, ProblemChoice}
import satify.update.Message.*
import satify.update.converters.CNFConverter
import satify.update.converters.TseitinTransformation.tseitin
import satify.update.parser.DimacsCNF.*

import scala.io.Source

object Update:

  def update(model: State, message: Message): State =
    message match
      case Input(char) => model
      case Solve(input) =>
        val exp = reflect(input)
        given CNFConverter = exp => tseitin(exp)
        State(input, exp, Solver().solve(exp))
      case SolveProblem(problem, parameter) =>
        val exp: Expression = problem match
          case NQueensChoice => NQueens(parameter).exp
          case GraphColoring => ??? // GraphColoring(parameter).exp
          case NurseScheduling => ??? // NurseScheduling(parameter).exp
        given CNFConverter = exp => tseitin(exp)
        val cnf: CNF = summon[CNFConverter].convert(exp)
        State(cnf, Solver().solve(exp), NQueens(parameter))
      case Convert(input) =>
        val exp = reflect(input)
        given CNFConverter = exp => tseitin(exp)
        val cnf: CNF = summon[CNFConverter].convert(exp)
        State(input, exp, cnf)
      case Import(file) =>
        val s: Source = Source.fromFile(file)
        val lines = s.getLines.toSeq
        s.close()
        val cnf: CNF = parse(lines).getOrElse(Symbol(Variable("NO CNF")))
        val input = cnf.printAsDSL()
        State(input, cnf)
      case NextSolution =>
        State(
          model.input.get,
          model.expression.get,
          Solution(
            model.solution.get.result,
            model.solution.get.assignment.tail ::: List(model.solution.get.assignment.head)
          )
        )
