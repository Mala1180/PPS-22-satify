package DecisionTreeTest

import model.Expression.*
import model.*
import model.dpll.{VariableConstraint, Decision, DecisionTree}
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
    DecisionTree(emptyExpression)(VariableConstraint("b", true)) should be equals classOf[DecisionTree]
  }

  "DecisionTree" should "append a Decision when a variable is constrained" in {
    val pA = PartialVariable("a", Option(true))
    val pB = PartialVariable("b", Option.empty)
    val pC = PartialVariable("c", Option.empty)
    val expectedExpression = And(Or(Symbol(pA), Symbol(pB)), Symbol(pC))
    DecisionTree(emptyExpression)(VariableConstraint("a", true)) should be equals
      DecisionTree(Queue(Decision("a", Set(pA, pB, pC), expectedExpression)))
  }

  "DecisionTree" should "append Decision(s) when invoking decision() method" in {
    val pA = PartialVariable("a", Option(true))
    val firstPB = PartialVariable("b", Option.empty)
    val secondPB = PartialVariable("b", Option(false))
    val pC = PartialVariable("c", Option.empty)
    val firstExpectedExp = And(Or(Symbol(pA), Symbol(firstPB)), Symbol(pC))
    val secondExpectedExp = And(Or(Symbol(pA), Symbol(secondPB)), Symbol(pC))

    val decisionTree = DecisionTree(emptyExpression)(VariableConstraint("a", true))
    DecisionTree.decision(decisionTree, VariableConstraint("b", false)) should be equals
      DecisionTree(Queue(
        Decision("a", Set(pA, firstPB, pC), firstExpectedExp),
        Decision("b", Set(pA, secondPB, pC), secondExpectedExp)
      ))
  }

