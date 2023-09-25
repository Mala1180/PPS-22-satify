package satify.model.cnf

/** An enum representing a Conjunction Normal Form (CNF) expression. */
import satify.model.cnf.CNF.*

enum Bool:
  case True
  case False

type Literal = Symbol | Not

enum CNF:
  case Symbol(value: String | Bool)
  case Not(branch: Symbol)
  case And(left: Or | Literal, right: And | Or | Literal)
  case Or(left: Or | Literal, right: Or | Literal)

object CNF:
  extension (cnf: CNF)
    def asDSL(flat: Boolean = false): String =
      cnf match
        case Symbol(name) => name.toString
        case And(left, right) =>
          if flat then s"${left.asDSL(flat)} and ${right.asDSL(flat)}"
          else s"${left.asDSL(flat)} and\n${right.asDSL(flat)}"
        case Or(left, right) => s"${left.asDSL(flat)} or ${right.asDSL(flat)}"
        case Not(branch) => s"not(${branch.asDSL(flat)})"

    def asFormal(flat: Boolean = false): String =
      var r = asDSL(flat)
        .replace("and", "∧")
        .replace("or", "∨")
        .replace("not", "¬")
      if flat then r = r.replace("\n", " ")
      r
