package satify.modelUtils.tree

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.*
import satify.model.tree.TraversableOps.*
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree

class TreeTest extends AnyFlatSpec with Matchers:

  val tree: Tree[String] = BinaryBranch(
    "x",
    BinaryBranch(
      "y",
      UnaryBranch("a", Leaf(Some("b"))),
      Leaf(Some("c"))
    ),
    Leaf(Some("z"))
  )

  "A Tree" should "be mapped to another Tree" in {
    tree.map {
      case BinaryBranch("y", _, _) => BinaryBranch("k", Leaf(Some("a")), Leaf(Some("b")))
      case node => node
    } shouldBe BinaryBranch("x", BinaryBranch("k", Leaf(Some("a")), Leaf(Some("b"))), Leaf(Some("z")))
  }

  "A Tree" should "be explored entirely" in {
    tree.foldLeft(List())((p: List[Tree[String]], c) => p :+ c) shouldBe
      List(
        tree,
        BinaryBranch("y", UnaryBranch("a", Leaf(Some("b"))), Leaf(Some("c"))),
        UnaryBranch("a", Leaf(Some("b"))),
        Leaf(Some("b")),
        Leaf(Some("c")),
        Leaf(Some("z"))
      )
  }
