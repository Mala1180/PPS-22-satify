package satify.dsl

import satify.model.Expression
import satify.model.Expression.*

import scala.annotation.targetName
import scala.language.postfixOps

object DSL:

  given Conversion[String, Symbol] with
    def apply(s: String): Symbol = Symbol(s)

  extension (exp1: Expression)
    def and(exp2: Expression): Expression = And(exp1, exp2)

    def or(exp2: Expression): Expression = Or(exp1, exp2)

    def not: Expression = Not(exp1)

    @targetName("andSymbol")
    def /\(exp2: Expression): Expression = and(exp2)
    @targetName("orSymbol")
    def \/(exp2: Expression): Expression = or(exp2)

    @targetName("notSymbol")
    def unary_! : Expression = not
