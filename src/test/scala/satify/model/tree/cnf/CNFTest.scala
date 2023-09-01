package satify.model.tree.cnf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.*
import satify.model.tree.TraversableOps.*
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree
import satify.model.tree.Value.*
import satify.model.tree.cnf.*

class CNFTest extends AnyFlatSpec with Matchers:

  val cnf: CNF = And(Symbol(Variable("a")), And(Symbol(Variable("b")), Symbol(Variable("c"))))

  "A CNF expression" should "have its respective Tree" in {
    cnf.tree shouldBe
      BinaryBranch(
        and,
        Leaf(Some(symbol(Variable("a")))),
        BinaryBranch(and, Leaf(Some(symbol(Variable("b")))), Leaf(Some(symbol(Variable("c")))))
      )
  }

  "A CNF expression" should "be able to be converted from Tree[Value] instance" in {
    val oCnf = CNF(
      BinaryBranch(
        and,
        Leaf(Some(symbol(Variable("a")))),
        BinaryBranch(and, Leaf(Some(symbol(Variable("b")))), Leaf(Some(symbol(Variable("c")))))
      )
    )
    oCnf shouldBe Some(cnf)
    CNF(
      BinaryBranch(
        and,
        BinaryBranch(and, Leaf(Some(symbol(Variable("a")))), Leaf(Some(symbol(Variable("b"))))),
        Leaf(Some(symbol(Variable("c"))))
      )
    ) shouldBe None
  }

  "A CNF expression" should "be able to use the Tree monad" in {
    val mCnf =
      CNF(
        for t <- cnf.tree
        yield t match
          case t if t == Symbol(Variable("c")).tree => Or(Symbol(Variable("c")), Symbol(Variable("d"))).tree
          case t => t
      )
    mCnf shouldBe Some(
      And(Symbol(Variable("a")), And(Symbol(Variable("b")), Or(Symbol(Variable("c")), Symbol(Variable("d")))))
    )
  }
