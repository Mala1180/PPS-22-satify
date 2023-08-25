package satify.update

import satify.model.{CNF, Expression, State}
import satify.update.Message.*
import satify.update.converters.CNFConverter
import satify.update.converters.TseitinTransformation.tseitin

object Update:

  def update(model: State, message: Message): State =
    message match
      case Input(char) => model
      case Solve(input) =>
        val exp = reflect(processInput(input))
        given CNFConverter with
          def convert(exp: Expression): CNF = tseitin(exp)

        State(exp, Solver().solve(exp))
      case Convert(input) =>
        val exp = reflect(processInput(input))
        val cnf = tseitin(exp)
        State(exp, cnf)

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
