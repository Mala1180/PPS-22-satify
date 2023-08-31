package satify.modelUtils.tree

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.*
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree

class TreeTest extends AnyFlatSpec with Matchers:

  "A Tree" should "be mapped to another Tree" in {
    val tree: Tree[String] = BinaryBranch(
      "x",
      BinaryBranch(
        "y",
        UnaryBranch("a", Leaf(Some("b"))),
        Leaf(Some("c"))
      ),
      Leaf(Some("z"))
    )
    tree.map {
      case BinaryBranch("y", _, _) => BinaryBranch("k", Leaf(Some("a")), Leaf(Some("b")))
      case node => node
    } shouldBe BinaryBranch("x", BinaryBranch("k", Leaf(Some("a")), Leaf(Some("b"))), Leaf(Some("z")))

  }
