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
