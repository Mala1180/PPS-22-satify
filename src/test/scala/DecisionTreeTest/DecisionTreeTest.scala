package DecisionTreeTest

import model.Expression.*
import model.*
import model.dpll.{Decision, DecisionTree}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.Queue
import scala.language.postfixOps

class DecisionTreeTest extends AnyFlatSpec with Matchers:

  val varA: NamedVariable = NamedVariable("a")
  val varB: NamedVariable = NamedVariable("b")
  val varC: NamedVariable = NamedVariable("c")
  
  val emptyExpression: EmptyExpression = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))


  "DecisionTree" should "is not defined until a decision is made" in {
    DecisionTree(emptyExpression).getClass should not equal classOf[DecisionTree]
  }

  "DecisionTree" should "append a Decision when an assignment is made" in {
    val pA = PartialVariable("a", Option(true))
    val pB = PartialVariable("b", Option.empty)
    val pC = PartialVariable("c", Option.empty)
    val expectedExpression = And(Or(Symbol(pA), Symbol(pB)), Symbol(pC))
    assert(DecisionTree(emptyExpression)("a", true) ===
      DecisionTree(Queue(Decision("a", Set(pA, pB, pC), expectedExpression))))
  }