import model.BacktrackingTree
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.Queue

class BackTrackingTreeTest extends AnyFlatSpec with Matchers:

  /* TODO:
    - Backtracking tree should be empty at first e.g. no decision has been made
    - Once an instance of BacktrackingTree has been created, decisions could be made
  */

  "BacktrackingTree" should "be initially empty" in {
    BacktrackingTree() should equal (new BacktrackingTree(Queue.empty))
  }

  