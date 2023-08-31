package satify.modelUtils.tree

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.Value.*
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree
import satify.model.tree.TraversableOps.*
import satify.model.tree.*

class ExpressionTest extends AnyFlatSpec with Matchers:

  val exp: Expression = and(or(symbol("a"), symbol("b")), symbol("c"))

  "An Expression" should "be buildable using a Tree" in {
    exp.tree shouldBe
      BinaryBranch(And, BinaryBranch(Or, Leaf(Some(Symbol("a"))), Leaf(Some(Symbol("b")))), Leaf(Some(Symbol("c"))))
  }

  "Expression" should "be able to be converted from Tree[Value] instance" in {
    val exp1: Expression =
      Expression(
        BinaryBranch(And, BinaryBranch(Or, Leaf(Some(Symbol("a"))), Leaf(Some(Symbol("b")))), Leaf(Some(Symbol("c"))))
      )
    exp1 shouldBe exp
  }

  "Expression" should "be able to use the Tree monad" in {
    val mExp =
      Expression(
        for t <- exp.tree
        yield t match
          case t if t == symbol("c").tree => and(symbol("c"), symbol("d")).tree
          case t => t
      )
    mExp shouldBe and(or(symbol("a"), symbol("b")), and(symbol("c"), symbol("d")))
  }
