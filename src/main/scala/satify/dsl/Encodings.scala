package satify.dsl

import satify.model.expression.Expression
import satify.model.expression.Expression.*

object Encodings:

  given SymbolGenerator with
    def hasToReset: Boolean = false
    def prefix: String = encodingVarPrefix

  extension (expressions: Seq[Symbol])

    /** Calls [[atMostK]]
      * @see [[atMostK]]
      * @return the [[Expression]] that represents the constraint
      */
    def atMost(k: Int)(using SymbolGenerator): Expression = atMostK(k)(expressions: _*)

    /** Calls [[atLeastOne]] if k is 1, [[atLeastK]] otherwise.
      * @see [[atLeastOne]] and [[atLeastK]]
      * @return the [[Expression]] that represents the constraint
      */
    def atLeast(k: Int): Expression = k match
      case 1 => atLeastOne(expressions: _*)
      case _ => atLeastK(k)(expressions: _*)

    /** Calls [[Expression.exactlyOne]]
      *  @see [[Expression.exactlyOne]]
      * @return the [[Expression]] that represents the constraint
      */
    def exactlyOne(using SymbolGenerator): Expression = Expression.exactlyOne(expressions: _*)
