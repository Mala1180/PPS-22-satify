package satify.model.tree

import Value.*

trait Expression:
  val tree: Tree[Value]

object Expression:

  def apply(tree: Tree[Value]): Expression = tree match
    case BinaryBranch(value, left, right) =>
      value match
        case And => and(Expression(left), Expression(right))
        case Or => or(Expression(left), Expression(right))
    case UnaryBranch(value, branch) =>
      value match
        case Not => not(Expression(branch))
    case Leaf(Some(Symbol(s))) => symbol(s)

case class and(left: Expression, right: Expression) extends Expression:
  val tree: Tree[Value] = BinaryBranch(And, left.tree, right.tree)

case class or(left: Expression, right: Expression) extends Expression:
  val tree: Tree[Value] = BinaryBranch(Or, left.tree, right.tree)

case class not(branch: Expression) extends Expression:
  val tree: Tree[Value] = UnaryBranch(Not, branch.tree)

case class symbol(s: String) extends Expression:
  val tree: Tree[Value] = Leaf(Some(Symbol(s)))
