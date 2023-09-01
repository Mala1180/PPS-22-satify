package satify.model.tree.expression

import satify.model.tree.*
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree
import Utils.symbolGenerator
import satify.model.tree.Value.{and, not, or, symbol}

trait Expression:
  val tree: Tree[Value]

case class And(left: Expression, right: Expression) extends Expression:
  val tree: BinaryBranch[Value] = BinaryBranch(and, left.tree, right.tree)

case class Or(left: Expression, right: Expression) extends Expression:
  val tree: BinaryBranch[Value] = BinaryBranch(or, left.tree, right.tree)

case class Not(branch: Expression) extends Expression:
  val tree: UnaryBranch[Value] = UnaryBranch(not, branch.tree)

case class Symbol(s: String) extends Expression:
  val tree: Leaf[Value] = Leaf(Some(symbol(s)))

object Expression:

  def apply(tree: Tree[Value]): Expression = tree match
    case BinaryBranch(value, left, right) =>
      value match
        case Value.and => And(Expression(left), Expression(right))
        case Value.or => Or(Expression(left), Expression(right))
    case UnaryBranch(value, branch) =>
      value match
        case Value.not => Not(Expression(branch))
    case Leaf(Some(symbol(s: String))) => Symbol(s)

  def map(exp: Expression, f: Expression => Expression): Expression =
    Expression(exp.tree.map(t => f(Expression(t)).tree))

  def zipWith(exp: Expression)(f: () => Symbol): List[(Symbol, Expression)] =
    exp.tree.zipWith(() => f().tree).map((l, t) => l match
      case Leaf(Some(symbol(v: String))) => (Symbol(v), Expression(t)))

  def zipWithSymbol(exp: Expression): List[(Symbol, Expression)] =
    zipWith(exp)(symbolGenerator("X"))

    //exp.tree.zipWith(symbolGenerator("X")).map((value, tree) => value match
    //  case symbol(value) => (Symbol(value), Expression(tree))).toList

  def subexpressions(exp: Expression): List[Expression] =
    zipWithSymbol(exp).map(_._2)
