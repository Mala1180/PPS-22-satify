package satify.update

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
        val exp = reflect(processInput(input))
        State()
      case Convert(input) =>
        val exp = reflect(processInput(input))
        val cnf = tseitin(exp)
        State(exp, cnf)
      case Import(file) =>
        val s: Source = Source.fromFile(file)
        val lines = s.getLines.toSeq
        s.close()
        val optCnf = parse(lines)
        State(Expression.Symbol("NO EXP"), optCnf.getOrElse(Symbol(Variable("NO CNF FOUND"))))

  private def processInput(input: String): String =
    // TODO: link these operators to the ones in the DSL
    val operators = List("and", "or", "not", "=>", "\\/", "/\\", "(", ")", "^")
    input.trim
      .split("[ ()]")
      .filterNot(_.isBlank)
      .map(symbol => if !operators.contains(symbol) then s"\"$symbol\"" else symbol)
      .reduce((a, b) => s"$a $b")

  private def reflect(code: String): Expression =
    val imports =
      """import satify.model.{Expression}
        |import satify.dsl.DSL.{*, given}
        |""".stripMargin
    println(imports + code)
    // TODO: does not work with only one symbol
    dotty.tools.repl.ScriptEngine().eval(imports + code).asInstanceOf[Expression]
