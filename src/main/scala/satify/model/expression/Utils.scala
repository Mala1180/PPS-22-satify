package satify.model.expression

import satify.model.expression.Expression.{And, Not, Or, Symbol}

object Utils:

  extension (exp: Expression)
    def asDSL(flat: Boolean = false): String =
      exp match
        case Symbol(value) => value
        case And(left, right) =>
          if flat then s"(${left.asDSL(flat)} and ${right.asDSL(flat)})"
          else s"(${left.asDSL(flat)} and\n${right.asDSL(flat)})"
        case Or(left, right) => s"(${left.asDSL(flat)} or ${right.asDSL(flat)})"
        case Not(branch) => s"not(${branch.asDSL(flat)})"

    def asFormal(flat: Boolean = false): String =
      var r = asDSL(flat)
        .replace("and", "∧")
        .replace("or", "∨")
        .replace("not", "¬")
      if flat then r = r.replace("\n", " ")
      r
