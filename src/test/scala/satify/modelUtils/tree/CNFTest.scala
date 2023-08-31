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
      BinaryBranch(And, Leaf(Some(Symbol("a"))), BinaryBranch(And, Leaf(Some(Symbol("b"))), Leaf(Some(Symbol("c")))))
  }

  "A CNF expression" should "be able to be converted from Tree[Value] instance" in {
    val oCnf = CNF(
      BinaryBranch(And, Leaf(Some(Symbol("a"))), BinaryBranch(And, Leaf(Some(Symbol("b"))), Leaf(Some(Symbol("c")))))
    )
    oCnf shouldBe Some(cnf)
    CNF(
      BinaryBranch(And, BinaryBranch(And, Leaf(Some(Symbol("a"))), Leaf(Some(Symbol("b")))), Leaf(Some(Symbol("c"))))
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
