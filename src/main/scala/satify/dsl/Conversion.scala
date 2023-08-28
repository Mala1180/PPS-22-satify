package satify.dsl

import satify.model.expression.Expression.Symbol
object Conversion:

  given Conversion[String, Symbol] with
    def apply(s: String): Symbol = Symbol(s)
