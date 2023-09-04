package satify.model.expression

import satify.model.expression.Expression.{And, Not, Or, Symbol}

object Utils:

  /** Generate a new Symbol starting from the given prefix.
    * @param prefix the prefix of the new Symbol.
    * @return a new Symbol.
    */
  def symbolGenerator(prefix: String): () => Symbol =
    var c = 0
    () =>
      val s: Symbol = Symbol(prefix + c)
      c = c + 1
      s

  extension (exp: Expression)
    def printAsDSL(flat: Boolean = false): String =
      exp match
        case Symbol(value) => value
        case And(left, right) =>
          if flat then s"${left.printAsDSL(flat)} and ${right.printAsDSL(flat)}"
          else s"${left.printAsDSL(flat)} and\n${right.printAsDSL(flat)}"
        case Or(left, right) => s"${left.printAsDSL(flat)} or ${right.printAsDSL(flat)}"
        case Not(branch) => s"not(${branch.printAsDSL(flat)})"

    def printAsFormal(flat: Boolean = false): String =
      var r = printAsDSL(flat)
        .replace("and", "∧")
        .replace("or", "∨")
        .replace("not", "¬")
      if flat then r = r.replace("\n", " ")
      r
