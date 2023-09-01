package satify.model.tree

import Value.*
import satify.model.tree.expression.Symbol

type literal = cnfSymbol | cnfNot

trait CNF:
  val tree: Tree[Value]

object CNF:

  def apply(tree: Tree[Value]): Option[CNF] = tree match
    case b @ BinaryBranch(Value.and, _, _) => andNode(b)
    case b @ _ => commonNode(b)

  private def commonNode(value: Tree[Value]): Option[cnfOr | literal] = value match
    case UnaryBranch(Value.not, Leaf(Some(Symbol(s)))) => Some(cnfNot(cnfSymbol(s)))
    case Leaf(Some(Value.symbol(s))) => Some(cnfSymbol(s))
    case BinaryBranch(Value.or, left, right) =>
      commonNode(left) match
        case Some(l) =>
          commonNode(right) match
            case Some(r) => Some(cnfOr(l, r))
            case _ => None
        case _ => None
    case _ => None

  private def andNode(value: Tree[Value]): Option[cnfAnd | cnfOr | literal] = value match
    case BinaryBranch(Value.and, left, right) =>
      commonNode(left) match
        case Some(l) =>
          andNode(right) match
            case Some(r) => Some(cnfAnd(l, r))
            case _ => None
        case _ => None
    case _ => commonNode(value)

case class cnfAnd(left: cnfOr | literal, right: cnfAnd | cnfOr | literal) extends CNF:
  val tree: BinaryBranch[Value] = BinaryBranch(and, left.tree, right.tree)

case class cnfOr(left: cnfOr | literal, right: cnfOr | literal) extends CNF:
  val tree: BinaryBranch[Value] = BinaryBranch(or, left.tree, right.tree)

case class cnfSymbol(s: String) extends CNF:
  val tree: Leaf[Value] = Leaf(Some(symbol(s)))

case class cnfNot(symbol: cnfSymbol) extends CNF:
  val tree: UnaryBranch[Value] = UnaryBranch(not, symbol.tree)
