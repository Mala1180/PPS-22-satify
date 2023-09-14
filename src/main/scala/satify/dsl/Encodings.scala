package satify.dsl

import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.expression.SymbolGeneration.SymbolGenerator

object Encodings:

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
    def atLeast(k: Int): Expression = atLeastK(k)(expressions: _*)

    /** Calls [[exactlyK]]
      * @see [[exactlyK]]
      * @return the [[Expression]] that represents the constraint
      */
    def exactly(k: Int)(using SymbolGenerator): Expression = exactlyK(k)(expressions: _*)
