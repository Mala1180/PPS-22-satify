package satify.modelUtils.tree

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.Value.*
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree
import satify.model.tree.TraversableOps.*
import satify.model.tree.*

class ExpressionTest extends AnyFlatSpec with Matchers:

  val exp: Expression = And(Or(Symbol("a"), Symbol("b")), Symbol("c"))

  "An Expression" should "have its respective Tree" in {
    exp.tree shouldBe
      BinaryBranch(and, BinaryBranch(or, Leaf(Some(symbol("a"))), Leaf(Some(symbol("b")))), Leaf(Some(symbol("c"))))
  }

  "Expression" should "be able to be converted from Tree[Value] instance" in {
    val exp1: Expression =
      Expression(
        BinaryBranch(and, BinaryBranch(or, Leaf(Some(symbol("a"))), Leaf(Some(symbol("b")))), Leaf(Some(symbol("c"))))
      )
    exp1 shouldBe exp
  }

  "Expression" should "be able to use the Tree monad" in {
    val mExp =
      Expression(
        for t <- exp.tree
        yield t match
          case t if t == Symbol("c").tree => And(Symbol("c"), Symbol("d")).tree
          case t => t
      )
    mExp shouldBe And(Or(Symbol("a"), Symbol("b")), And(Symbol("c"), Symbol("d")))
  }
