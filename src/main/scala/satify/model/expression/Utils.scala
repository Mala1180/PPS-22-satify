package satify.model.expression

import satify.model.expression.Expression.{And, Not, Or, Symbol}

object Utils:

  def encodingVarPrefix = "ENC"
  def tseitinVarPrefix = "TSEITIN"

  trait SymbolGenerator:
    var c = 0
    def generate(): Symbol =
      val s: Symbol = Symbol(prefix + c)
      c = c + 1
      s
    def prefix: String
    def hasToReset: Boolean
    def reset(): Unit = c = 0

  trait ResettableSymbolGenerator extends SymbolGenerator:
    override def hasToReset: Boolean = true

  trait IncrementalSymbolGenerator extends SymbolGenerator:
    override def hasToReset: Boolean = false

//  /** Creates a producer of new Symbols starting from the given prefix.
//    * @param prefix the prefix of the new Symbol.
//    * @return a function that produces a new Symbol starting from the given prefix.
//    */
//  def symbolProducer(prefix: String): () => Symbol =
//    var c = 0
//    () =>
//      val s: Symbol = Symbol(prefix + c)
//      c = c + 1
//      s
//
//  /** Generate a new Symbol starting from the given prefix.
//    * @param prefix the prefix of the new Symbol.
//    * @return a new Symbol.
//    */
//  def symbolGenerator(prefix: String): () => Symbol = symbolProducer(prefix)

  extension (exp: Expression)
    def printAsDSL(flat: Boolean = false): String =
      exp match
        case Symbol(value) => value
        case And(left, right) =>
          if flat then s"(${left.printAsDSL(flat)} and ${right.printAsDSL(flat)})"
          else s"(${left.printAsDSL(flat)} and\n${right.printAsDSL(flat)})"
        case Or(left, right) => s"(${left.printAsDSL(flat)} or ${right.printAsDSL(flat)})"
        case Not(branch) => s"not(${branch.printAsDSL(flat)})"

    def printAsFormal(flat: Boolean = false): String =
      var r = printAsDSL(flat)
        .replace("and", "∧")
        .replace("or", "∨")
        .replace("not", "¬")
      if flat then r = r.replace("\n", " ")
      r
