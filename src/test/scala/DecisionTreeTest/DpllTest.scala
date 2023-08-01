package DecisionTreeTest

import model.Expression.*
import model.*
import model.dpll.DecisionTree.*
import model.dpll.DpllExpressionUtils.*
import model.dpll.{Decision, DecisionTree, Dpll, VarConstr}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.Queue
import scala.language.postfixOps

class DpllTest extends AnyFlatSpec with Matchers:

  val varA: PartialVariable = PartialVariable("a", Option.empty)
  val varB: PartialVariable = PartialVariable("b", Option.empty)

  val parExp: PartialExpression = And(Symbol(varA), Symbol(varB))

  "DPLL" should "accept a Decision and return a DecisionTree" in {
    Dpll.dpll(Decision(extractModelFromExp(parExp), parExp)).getClass should be equals classOf[DecisionTree]
  }

  "DPLL" should "explore all the possible assignments to the model" in {
    val emptyDec = Decision(extractModelFromExp(parExp), parExp)
    Dpll.dpll(emptyDec) should be equals
      // height: 0
      Branch(emptyDec,
        // height: 1
        Branch(
          Decision(Seq(PartialVariable("a", Option(true)), varB), And(Symbol(PartialVariable("a", Option(true))), Symbol(varB))),
          // height: 2
          Branch(
            Decision(Seq(PartialVariable("a", Option(true)), PartialVariable("b", Option(true))),
              And(Symbol(PartialVariable("a", Option(true))), Symbol(PartialVariable("b", Option(true))))), Unsat, Unsat),
          Branch(
            Decision(Seq(PartialVariable("a", Option(true)), PartialVariable("b", Option(false))),
              And(Symbol(PartialVariable("a", Option(true))), Symbol(PartialVariable("b", Option(false))))), Unsat, Unsat),
        ),
        Branch(
          Decision(Seq(PartialVariable("a", Option(false)), varB), And(Symbol(PartialVariable("a", Option(false))), Symbol(varB))),
          // height: 2
          Branch(
            Decision(Seq(PartialVariable("a", Option(false)), PartialVariable("b", Option(true))),
              And(Symbol(PartialVariable("a", Option(false))), Symbol(PartialVariable("b", Option(true))))), Unsat, Unsat),
          Branch(
            Decision(Seq(PartialVariable("a", Option(false)), PartialVariable("b", Option(false))),
              And(Symbol(PartialVariable("a", Option(false))), Symbol(PartialVariable("b", Option(false))))), Unsat, Unsat),
        ))
  }

