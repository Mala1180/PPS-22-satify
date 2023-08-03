package satify.dsl

import satify.model.Expression.*
import satify.model.{EmptyExpression, EmptyVariable, Expression}

import scala.language.postfixOps

object DSL:

  given Conversion[String, Symbol[EmptyVariable]] with
    def apply(s: String): Symbol[EmptyVariable] = Symbol(EmptyVariable(s))

  extension (exp1: EmptyExpression)
    def and(exp2: EmptyExpression): EmptyExpression = And(exp1, exp2)
    def or(exp2: EmptyExpression): EmptyExpression = Or(exp1, exp2)
    def not: EmptyExpression = Not(exp1)

@main def tryDSL(): Unit =
  import DSL.{*, given}
  val operators = List("and", "or", "not", "=>", "\\/", "/\\", "(", ")")
  val input = "((a)   and b and c)"
  val processed = input
    // split for spaces or parenthesis
    .split("[ ()]")
    .filterNot(_.isBlank)
    .map(symbol => if !operators.contains(symbol) then s""""$symbol"""" else symbol)
    .reduce((a, b) => s"$a $b")
  println(processed)

  val imports =
    """import satify.model.{Expression, EmptyVariable}
      |import satify.dsl.DSL.{*, given}
      |""".stripMargin

  val res = dotty.tools.repl.ScriptEngine().eval(imports + processed)
  println(res)
