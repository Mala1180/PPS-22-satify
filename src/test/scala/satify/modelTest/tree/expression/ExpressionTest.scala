package satify.modelTest.tree.expression

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.TraversableOps.*
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree
import satify.model.tree.Value.*
import satify.model.tree.expression.{And, Expression, Or}
import satify.model.tree.*

class ExpressionTest extends AnyFlatSpec with Matchers:

  val exp: Expression = And(Or(expression.Symbol("a"), expression.Symbol("b")), expression.Symbol("c"))

  "An Expression" should "have its respective Tree" in {
    exp.tree shouldBe
      BinaryBranch(and, BinaryBranch(or, Leaf(Some(symbol("a"))), Leaf(Some(symbol("b")))), Leaf(Some(symbol("c"))))
  }

  "Expression" should "be able to be converted from Tree[Value] instance" in {
    val exp1: Expression =
      expression.Expression(
        BinaryBranch(and, BinaryBranch(or, Leaf(Some(symbol("a"))), Leaf(Some(symbol("b")))), Leaf(Some(symbol("c"))))
      )
    exp1 shouldBe exp
  }

  "Expression" should "be able to use the Tree monad" in {
    val mExp =
      Expression(
        for t <- exp.tree
        yield t match
          case t if t == expression.Symbol("c").tree =>
            expression.And(expression.Symbol("c"), expression.Symbol("d")).tree
          case t => t
      )
    mExp shouldBe And(
      expression.Or(expression.Symbol("a"), expression.Symbol("b")),
      expression.And(expression.Symbol("c"), expression.Symbol("d"))
    )
  }
