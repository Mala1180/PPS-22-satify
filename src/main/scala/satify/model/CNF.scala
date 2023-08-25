package satify.model

/** An enum representing a Conjunction Normal Form (CNF) expression. */
import satify.model.CNF.*

case class Variable(name: String, value: Option[Boolean] = None)

enum Bool:
  case True
  case False

type Literal = Symbol | Not

enum CNF:
  case Symbol(value: Variable | Bool)
  case And(left: Or | Literal, right: And | Or | Literal)
  case Or(left: Or | Literal, right: Or | Literal)
  case Not(branch: Symbol)

object CNF:
  extension (cnf: CNF)
    def printAsFormal(flat: Boolean = false): String =
      cnf match
        case Symbol(value) =>
          value match
            case Variable(name, v) => name
            case v => v.toString
        case And(left, right) =>
          if flat then s"${left.printAsFormal(flat)} ∧ ${right.printAsFormal(flat)}" else s"${left.printAsFormal(flat)} ∧\n${right.printAsFormal(flat)}"
        case Or(left, right) => s"${left.printAsFormal(flat)} ∨ ${right.printAsFormal(flat)}"
        case Not(branch) => s"¬${branch.printAsFormal(flat)}"

    def printAsDSL(flat: Boolean = false): String =
      var r = printAsFormal(flat)
        .replace("∧", "and")
        .replace("∨", "or")
        .replace("¬", "not")
      if flat then r = r.replace("\n", " ")
      r
