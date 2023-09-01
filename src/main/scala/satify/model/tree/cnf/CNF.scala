package satify.model.tree.cnf

/** An enum representing a Conjunction Normal Form (CNF) expression. */
import satify.model.tree.Value.{and, not, or, symbol}
import satify.model.tree.{BinaryBranch, Leaf, Tree, UnaryBranch, Value}
import satify.model.tree.cnf.CNF.*

case class Variable(name: String, value: Option[Boolean] = None)

enum Bool:
  case True
  case False

import Value.*
import satify.model.tree.expression.Symbol

type Literal = Symbol | Not

trait CNF:
  val tree: Tree[Value]

object CNF:

  def apply(tree: Tree[Value]): Option[CNF] = tree match
    case b @ BinaryBranch(Value.and, _, _) => andNode(b)
    case b @ _ => commonNode(b)

  private def commonNode(value: Tree[Value]): Option[Or | Literal] = value match
    case UnaryBranch(Value.not, Leaf(Some(Symbol(s)))) => Some(Not(Symbol(s)))
    case Leaf(Some(Value.symbol(s: (Variable | Bool)))) => Some(Symbol(s))
    case BinaryBranch(Value.or, left, right) =>
      commonNode(left) match
        case Some(l) =>
          commonNode(right) match
            case Some(r) => Some(Or(l, r))
            case _ => None
        case _ => None
    case _ => None

  private def andNode(value: Tree[Value]): Option[And | Or | Literal] = value match
    case BinaryBranch(Value.and, left, right) =>
      commonNode(left) match
        case Some(l) =>
          andNode(right) match
            case Some(r) => Some(And(l, r))
            case _ => None
        case _ => None
    case _ => commonNode(value)

case class And(left: Or | Literal, right: And | Or | Literal) extends CNF:
  val tree: BinaryBranch[Value] = BinaryBranch(and, left.tree, right.tree)

case class Or(left: Or | Literal, right: Or | Literal) extends CNF:
  val tree: BinaryBranch[Value] = BinaryBranch(or, left.tree, right.tree)

case class Symbol(v: Variable | Bool) extends CNF:
  val tree: Leaf[Value] = Leaf(Some(symbol(v)))

case class Not(symbol: Symbol) extends CNF:
  val tree: UnaryBranch[Value] = UnaryBranch(not, symbol.tree)
