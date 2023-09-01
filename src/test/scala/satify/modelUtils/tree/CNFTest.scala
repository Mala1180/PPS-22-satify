package satify.modelUtils.tree

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.Value.*
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree
import satify.model.tree.TraversableOps.*
import satify.model.tree.*

class CNFTest extends AnyFlatSpec with Matchers:

  val cnf: CNF = cnfAnd(cnfSymbol("a"), cnfAnd(cnfSymbol("b"), cnfSymbol("c")))

  "A CNF expression" should "have its respective Tree" in {
    cnf.tree shouldBe
      BinaryBranch(and, Leaf(Some(symbol("a"))), BinaryBranch(and, Leaf(Some(symbol("b"))), Leaf(Some(symbol("c")))))
  }

  "A CNF expression" should "be able to be converted from Tree[Value] instance" in {
    val oCnf = CNF(
      BinaryBranch(and, Leaf(Some(symbol("a"))), BinaryBranch(and, Leaf(Some(symbol("b"))), Leaf(Some(symbol("c")))))
    )
    oCnf shouldBe Some(cnf)
    CNF(
      BinaryBranch(and, BinaryBranch(and, Leaf(Some(symbol("a"))), Leaf(Some(symbol("b")))), Leaf(Some(symbol("c"))))
    ) shouldBe None
  }

  "A CNF expression" should "be able to use the Tree monad" in {
    val mCnf =
      CNF(
        for t <- cnf.tree
        yield t match
          case t if t == cnfSymbol("c").tree => cnfOr(cnfSymbol("c"), cnfSymbol("d")).tree
          case t => t
      )
    mCnf shouldBe Some(cnfAnd(cnfSymbol("a"), cnfAnd(cnfSymbol("b"), cnfOr(cnfSymbol("c"), cnfSymbol("d")))))
  }
