package satify.model.tree.expression

import satify.model.tree.expression.{And, Expression, Not, Or, Symbol}

object ExpressionUtils:

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
    def printAsFormal(flat: Boolean = false): String =
      exp match
        case Symbol(value) => value
        case And(left, right) =>
          if flat then s"${left.printAsFormal(flat)} and ${right.printAsFormal(flat)}"
          else s"${left.printAsFormal(flat)} and\n${right.printAsFormal(flat)}"
        case Or(left, right) => s"${left.printAsFormal(flat)} or ${right.printAsFormal(flat)}"
        case Not(branch) => s"not(${branch.printAsFormal(flat)})"

    def printAsDSL(flat: Boolean = false): String =
      var r = printAsFormal(flat)
        .replace("and", "∧")
        .replace("or", "∨")
        .replace("not", "¬")
      if flat then r = r.replace("\n", " ")
      r
