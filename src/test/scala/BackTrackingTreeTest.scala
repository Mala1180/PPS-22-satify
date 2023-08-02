import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression.*
import satify.model.{BacktrackingTree, EmptyExpression, EmptyVariable, Expression}

import scala.collection.immutable.Queue

class BackTrackingTreeTest extends AnyFlatSpec with Matchers:

  /* TODO:
    - Backtracking tree should be empty at first e.g. no decision has been made
    - Once an instance of BacktrackingTree has been created, decisions could be made
   */

  val varA: EmptyVariable = EmptyVariable("a")
  val varB: EmptyVariable = EmptyVariable("b")
  val varC: EmptyVariable = EmptyVariable("c")

  val cnfExp: EmptyExpression = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  "BacktrackingTree" should "be initially empty" in {
    BacktrackingTree(cnfExp) should equal(new BacktrackingTree(cnfExp, Queue.empty))
  }

  "BackTrackingTree" should "contain the first decision" in {}
