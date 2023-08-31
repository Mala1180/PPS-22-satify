package satify.modelUtils.tree

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.*
import satify.model.tree.TraversableOps.*
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree

class TreeTest extends AnyFlatSpec with Matchers:

  val tree: Tree[String] = BinaryBranch(
    "a",
    BinaryBranch(
      "b",
      UnaryBranch("c", Leaf(Some("d"))),
      Leaf(Some("e"))
    ),
    Leaf(Some("f"))
  )

  "A Tree" should "be mapped to another Tree" in {
    tree.map {
      case BinaryBranch("b", _, _) => BinaryBranch("g", Leaf(Some("h")), Leaf(Some("i")))
      case node => node
    } shouldBe BinaryBranch("a", BinaryBranch("g", Leaf(Some("h")), Leaf(Some("i"))), Leaf(Some("f")))
  }

  "A Tree" should "be explored entirely" in {
    tree.foldLeft(List())((p: List[Tree[String]], c) => p :+ c) shouldBe
      List(
        tree,
        BinaryBranch("b", UnaryBranch("c", Leaf(Some("d"))), Leaf(Some("e"))),
        UnaryBranch("c", Leaf(Some("d"))),
        Leaf(Some("d")),
        Leaf(Some("e")),
        Leaf(Some("f"))
      )
  }

  "A Tree" should "use the be mapped to another Tree using the monad" in {
    val mTree =
      for t <- tree
      yield t match
        case t if t == UnaryBranch("c", Leaf(Some("d"))) => BinaryBranch("g", Leaf(Some("h")), Leaf(Some("i")))
        case t => t
    mTree shouldBe BinaryBranch(
      "a",
      BinaryBranch("b", BinaryBranch("g", Leaf(Some("h")), Leaf(Some("i"))), Leaf(Some("e"))),
      Leaf(Some("f"))
    )
  }
