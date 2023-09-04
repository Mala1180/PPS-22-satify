package satify.dsl

import satify.model.expression.Expression
import satify.model.expression.Expression.*

object Encodings:

  /** Converts a tuple to a list of [[Symbol]]
    * @param tuple the input [[Tuple]]
    * @return the list of [[Symbol]]
    */
  private def tupleToSymbols(tuple: Tuple): List[Symbol] =
    tuple.productIterator.toList.map(_.toString).map(Symbol.apply)

  extension (expressions: Seq[Symbol])

    /** Calls [[atMostOne]] if k is 1, [[atMostK]] otherwise.
      * @see [[atMostOne]] and [[atMostK]]
      * @return the [[Expression]] that represents the constraint
      */
    def atMost(k: Int): Expression = k match
      case 1 => atMostOne(expressions: _*)
      case _ => atMostK(k)(expressions: _*)

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
    def exactlyOne: Expression = Expression.exactlyOne(expressions: _*)
    // TODO to test
