import satify.model.{BacktrackingTree, EmptyModel, Expression, NamedVariable, Variable}
import satify.model.Expression.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.Queue

class BackTrackingTreeTest extends AnyFlatSpec with Matchers:

  /* TODO:
    - Backtracking tree should be empty at first e.g. no decision has been made
    - Once an instance of BacktrackingTree has been created, decisions could be made
  */

  val varA: NamedVariable = NamedVariable("a")
  val varB: NamedVariable = NamedVariable("b")
  val varC: NamedVariable = NamedVariable("c")
  
  val cnfExp: EmptyModel = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  "BacktrackingTree" should "be initially empty" in {
    BacktrackingTree(cnfExp) should equal (new BacktrackingTree(cnfExp, Queue.empty))
  }

  "BackTrackingTree" should "contain the first decision" in {
    
  }