package satify.update

import satify.dsl.Reflection.reflect
import satify.model.CNF.Symbol
import satify.model.expression.Expression
import satify.model.expression.Expression.Symbol as ExpSymbol
import satify.model.problems.ProblemChoice.{GraphColoring, NurseScheduling, NQueens as NQueensChoice}
import satify.model.{CNF, State, Variable}
import satify.update.Message.*
import satify.update.converters.CNFConverter
import satify.update.converters.TseitinTransformation.{convertToCNF, tseitin}
import satify.update.parser.DimacsCNF.*
import satify.model.problems.{NQueens, ProblemChoice}
import satify.model.problems.NQueens.*

import scala.io.Source

object Update:

  def update(model: State, message: Message): State =
    message match
      case Input(char) => model
      case Solve(input) =>
        val exp = reflect(input)
        given CNFConverter with
          def convert(exp: Expression): CNF = tseitin(exp)

        State(exp, Solver().solve(exp))
      case SolveProblem(problem, parameter) =>
        var exp: Expression = ExpSymbol("NO EXP")
        problem match
          case NQueensChoice => exp = NQueens(parameter).exp
          case GraphColoring => ??? // GraphColoring(parameter).exp
          case NurseScheduling => ??? // NurseScheduling(parameter).exp
        given CNFConverter with
          def convert(exp: Expression): CNF = tseitin(exp)

        State(exp, Solver().solve(exp))
      case Convert(input) =>
        val exp = reflect(input)
        val cnf = tseitin(exp)
        State(exp, cnf)
      case Import(file) =>
        val s: Source = Source.fromFile(file)
        val lines = s.getLines.toSeq
        s.close()
        val optCnf = parse(lines)
        State(
          optCnf.getOrElse(Symbol(Variable("NO CNF FOUND")))
        )
