package satify.model

/** An enum representing a Conjunction Normal Form (CNF) expression. */
import satify.model.CNF.*
type Literal = Symbol | Not
enum CNF:
  case Symbol(value: Variable)
  case And(left: Or | Literal, right: And | Or | Literal)
  case Or(left: Or | Literal, right: Or | Literal)
  case Not(branch: Symbol)
