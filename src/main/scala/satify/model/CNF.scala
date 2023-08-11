package satify.model

/** An enum representing a Conjunction Normal Form (CNF) expression. */
import satify.model.CNF.*

case class Variable(name: String, value: Option[Boolean] = None)

enum Constant:
  case True
  case False

type Literal = Symbol | Not
//type CNF = And
enum CNF:
  case Symbol(value: Variable | Constant)
  case And(left: Or | Literal, right: And | Or | Literal)
  case Or(left: Or | Literal, right: Or | Literal)
  case Not(branch: Symbol)
