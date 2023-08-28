package satify.model.dsl

import satify.model.Expression.Symbol
object Conversion:

  given Conversion[String, Symbol] with
    def apply(s: String): Symbol = Symbol(s)
