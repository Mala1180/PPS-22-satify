package satify.dsl

import satify.model.tree.expression.Encodings.*
import satify.model.tree.expression.{Expression, Symbol}

object Encodings:

  /** Converts a tuple to a list of [[Symbol]]
    * @param tuple the input [[Tuple]]
    * @return the list of [[Symbol]]
    */
  private def tupleToSymbols(tuple: Tuple): List[Symbol] =
    tuple.productIterator.toList.map(_.toString).map(Symbol.apply)

  extension (expressions: Tuple)

    /** Calls [[atMostOne]] if k is 1, [[atMostK]] otherwise.
      * @see [[atMostOne]] and [[atMostK]]
      */
    def atMost(k: Int): Expression = k match
      case 1 => atMostOne(tupleToSymbols(expressions): _*)
      case _ => atMostK(k)(tupleToSymbols(expressions): _*)

    /** Calls [[atLeastOne]] if k is 1, [[atLeastK]] otherwise.
      * @see [[atLeastOne]] and [[atLeastK]]
      */
    def atLeast(k: Int): Expression = k match
      case 1 => atLeastOne(tupleToSymbols(expressions): _*)
      case _ => atLeastK(k)(tupleToSymbols(expressions): _*)
