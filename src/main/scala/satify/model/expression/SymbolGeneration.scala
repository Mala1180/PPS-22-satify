package satify.model.expression

import satify.model.expression.Expression.*

object SymbolGeneration:

  val encodingVarPrefix = "ENC"
  val tseitinVarPrefix = "TSTN"

  /** A generator of new Symbols starting from a prefix. */
  trait SymbolGenerator:
    private var c = 0

    def reset(): Unit = c = 0

    /** Generate a new Symbol starting from the given prefix.
      * @return a new Symbol.
      */
    def generate(): Symbol =
      val s: Symbol = Symbol(prefix + c)
      c = c + 1
      s

    def prefix: String

    def hasToReset: Boolean = false

  trait ErasableSymbolGenerator extends SymbolGenerator:
    override def hasToReset: Boolean = true
