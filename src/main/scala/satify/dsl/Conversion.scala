package satify.dsl

import satify.model.tree.expression.Expression.*
import satify.model.tree.*

object Conversion:

  given Conversion[String, expression.Symbol] with
    def apply(s: String): expression.Symbol = expression.Symbol(s)

  given Conversion[Tuple, Seq[expression.Symbol]] with
    def apply(tuple: Tuple): Seq[expression.Symbol] =
      tuple.productIterator.map(_.toString).map(expression.Symbol(_)).map(_.asInstanceOf[expression.Symbol]).toSeq
