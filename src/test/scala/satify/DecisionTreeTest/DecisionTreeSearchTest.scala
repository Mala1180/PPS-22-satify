package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.*
import satify.model.CNF.*
import satify.model.dpll.DecisionTree.*
import satify.model.dpll.DecisionTreeSearch.*
//import satify.model.dpll.DpllExpressionUtils.*
import satify.model.dpll.{Decision, DecisionTree, DecisionTreeSearch}

import scala.language.postfixOps

/*class DecisionTreeSearchTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a", Option.empty)
  val varB: Variable = Variable("b", Option.empty)

  val parExp: CNF = And(Symbol(varA), Symbol(varB))

  "Decision tree search" should "accept a Decision and return a DecisionTree" in {
    search(Decision(extractModelFromExp(parExp), parExp)).getClass should be equals classOf[DecisionTree]
  }

  "Decision tree search" should "explore all the possible assignments to the model" in {
    val emptyDec = Decision(extractModelFromExp(parExp), parExp)
    search(emptyDec) should be equals
      Branch(
        emptyDec,
        Branch(
          Decision(
            Seq(PartialVariable("a", Option(true)), varB),
            And(Symbol(PartialVariable("a", Option(true))), Symbol(varB))
          ),
          Branch(
            Decision(
              Seq(PartialVariable("a", Option(true)), PartialVariable("b", Option(true))),
              And(Symbol(PartialVariable("a", Option(true))), Symbol(PartialVariable("b", Option(true))))
            ),
            Unsat,
            Unsat
          ),
          Branch(
            Decision(
              Seq(PartialVariable("a", Option(true)), PartialVariable("b", Option(false))),
              And(Symbol(PartialVariable("a", Option(true))), Symbol(PartialVariable("b", Option(false))))
            ),
            Unsat,
            Unsat
          )
        ),
        Branch(
          Decision(
            Seq(PartialVariable("a", Option(false)), varB),
            And(Symbol(PartialVariable("a", Option(false))), Symbol(varB))
          ),
          Branch(
            Decision(
              Seq(PartialVariable("a", Option(false)), PartialVariable("b", Option(true))),
              And(Symbol(PartialVariable("a", Option(false))), Symbol(PartialVariable("b", Option(true))))
            ),
            Unsat,
            Unsat
          ),
          Branch(
            Decision(
              Seq(PartialVariable("a", Option(false)), PartialVariable("b", Option(false))),
              And(Symbol(PartialVariable("a", Option(false))), Symbol(PartialVariable("b", Option(false))))
            ),
            Unsat,
            Unsat
          )
        )
      )
  }*/
