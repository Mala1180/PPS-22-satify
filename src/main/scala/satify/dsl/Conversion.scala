package satify.dsl

import satify.model.tree.expression.Symbol

object Conversion:

  given Conversion[String, Symbol] with
    def apply(s: String): Symbol = Symbol(s)

  given Conversion[Tuple, Seq[Symbol]] with
    def apply(tuple: Tuple): Seq[Symbol] =
      tuple.productIterator.map(_.toString).map(Symbol.apply).toSeq
