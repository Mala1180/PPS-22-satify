package satify.update

import satify.dsl.Reflection.*
import satify.model.CNF.Symbol
import satify.model.{Expression, State, Variable}
import satify.update.Message.*
import satify.update.converters.TseitinTransformation.tseitin
import satify.update.parser.DimacsCNF.*

import scala.io.Source

object Update:

  def update(model: State, message: Message): State =
    message match
      case Input(char) => model
      case Solve(input) =>
        val exp = reflect(input)
        State()
      case Convert(input) =>
        val exp = reflect(input)
        val cnf = tseitin(exp)
        State(exp, cnf)
      case Import(file) =>
        val s: Source = Source.fromFile(file)
        val lines = s.getLines.toSeq
        s.close()
        val optCnf = parse(lines)
        State(Expression.Symbol("NO EXP"), optCnf.getOrElse(Symbol(Variable("NO CNF FOUND"))))
