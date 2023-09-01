package satify.update

import satify.dsl.Reflection.reflect
import satify.model.tree.cnf.Symbol
import satify.model.State
import satify.model.tree.cnf.{CNF, Variable}
import satify.update.Message.*
import satify.update.converters.CNFConverter
import satify.update.converters.TseitinTransformation.tseitin
import satify.update.parser.DimacsCNF.*
import satify.model.tree.expression.{Expression, Symbol as ExpSymbol}
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
      case Convert(input) =>
        val exp = reflect(input)
        val cnf = tseitin(exp)
        State(exp, cnf)
      case Import(file) =>
        val s: Source = Source.fromFile(file)
        val lines = s.getLines.toSeq
        s.close()
        val optCnf = parse(lines)
        State(ExpSymbol("NO EXP"), optCnf.getOrElse(Symbol(Variable("NO CNF FOUND"))))
