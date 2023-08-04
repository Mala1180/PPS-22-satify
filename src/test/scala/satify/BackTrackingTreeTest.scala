package satify

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.*
import satify.model.{BacktrackingTree, CNF, Expression, Variable}

import scala.collection.immutable.Queue

class BackTrackingTreeTest extends AnyFlatSpec with Matchers:

  /* TODO:
    - Backtracking tree should be empty at first e.g. no decision has been made
    - Once an instance of BacktrackingTree has been created, decisions could be made
   */

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnfExp: CNF = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  "BacktrackingTree" should "be initially empty" in {
    BacktrackingTree(cnfExp) should equal(new BacktrackingTree(cnfExp, Queue.empty))
  }

  "BackTrackingTree" should "contain the first decision" in {}
