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

  override def toString: String =
    this match
      case Symbol(value) => value match
        case Variable(name, v) => name
        case v => v.toString
      case And(left, right) => s"$left ∧\n$right"
      case Or(left, right) => s"($left ∨ $right)"
      case Not(branch) => s"¬$branch"
