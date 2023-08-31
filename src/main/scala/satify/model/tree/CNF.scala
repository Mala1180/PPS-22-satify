package satify.model.tree

import Value.*

type literal = cnfSymbol | cnfNot

trait CNF:
  val tree: Tree[Value]

object CNF:

  def apply(tree: Tree[Value]): Option[CNF] = tree match
    case b @ BinaryBranch(And, _, _) => andNode(b)
    case b @ _ => commonNode(b)

  private def commonNode(value: Tree[Value]): Option[cnfOr | literal] = value match
    case UnaryBranch(Not, Leaf(Some(Symbol(s)))) => Some(cnfNot(cnfSymbol(s)))
    case Leaf(Some(Symbol(s))) => Some(cnfSymbol(s))
    case BinaryBranch(Or, left, right) =>
      commonNode(left) match
        case Some(l) =>
          commonNode(right) match
            case Some(r) => Some(cnfOr(l, r))
            case _ => None
        case _ => None
    case _ => None

  private def andNode(value: Tree[Value]): Option[cnfAnd | cnfOr | literal] = value match
    case BinaryBranch(And, left, right) =>
      commonNode(left) match
        case Some(l) =>
          andNode(right) match
            case Some(r) => Some(cnfAnd(l, r))
            case _ =>
              commonNode(right) match
                case Some(r) => Some(cnfAnd(l, r))
                case _ => None
        case _ => None
    case _ => commonNode(value)

case class cnfAnd(left: cnfOr | literal, right: cnfAnd | cnfOr | literal) extends CNF:
  val tree: Tree[Value] = BinaryBranch(And, left.tree, right.tree)

case class cnfOr(left: cnfOr | literal, right: cnfOr | literal) extends CNF:
  val tree: Tree[Value] = BinaryBranch(Or, left.tree, right.tree)

case class cnfSymbol(s: String) extends CNF:
  val tree: Tree[Value] = Leaf(Some(Symbol(s)))

case class cnfNot(symbol: cnfSymbol) extends CNF:
  val tree: Tree[Value] = UnaryBranch(Not, symbol.tree)

@main def testCNF(): Unit = {
  val exp1: Expression = and(or(symbol("a"), symbol("b")), symbol("c"))
  val cnf: CNF = cnfAnd(cnfOr(cnfSymbol("a"), cnfSymbol("b")), cnfAnd(cnfSymbol("c"), cnfSymbol("d")))
  println(exp1)
  println(cnf)
}
