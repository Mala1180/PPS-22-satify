package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.DecisionTree
import satify.model.DecisionTree.*
import satify.model.{CNF, Decision, Variable}
import satify.model.CNF.*
import satify.update.dpll.DpllCnfUtils.*
import satify.update.dpll.DecisionTreeSearch.*

class DPLLTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")

  val cnf: CNF = And(Symbol(varA), Symbol(varB))

  "Function DPLL" should "accept a Decision and return a DecisionTree" in {
    dpll(Decision(extractModelFromCnf(cnf), cnf)).getClass should be equals classOf[DecisionTree]
  }

  "Function DPLL" should "explore all the possible assignments to the model" in {
    val emptyDec = Decision(extractModelFromCnf(cnf), cnf)
    dpll(emptyDec) should be equals
      Branch(
        emptyDec,
        Branch(
          Decision(
            Seq(Variable("a", Option(true)), varB),
            And(Symbol(Variable("a", Option(true))), Symbol(varB))
          ),
          Branch(
            Decision(
              Seq(Variable("a", Option(true)), Variable("b", Option(true))),
              And(Symbol(Variable("a", Option(true))), Symbol(Variable("b", Option(true))))
            ),
            Unsat,
            Unsat
          ),
          Branch(
            Decision(
              Seq(Variable("a", Option(true)), Variable("b", Option(false))),
              And(Symbol(Variable("a", Option(true))), Symbol(Variable("b", Option(false))))
            ),
            Unsat,
            Unsat
          )
        ),
        Branch(
          Decision(
            Seq(Variable("a", Option(false)), varB),
            And(Symbol(Variable("a", Option(false))), Symbol(varB))
          ),
          Branch(
            Decision(
              Seq(Variable("a", Option(false)), Variable("b", Option(true))),
              And(Symbol(Variable("a", Option(false))), Symbol(Variable("b", Option(true))))
            ),
            Unsat,
            Unsat
          ),
          Branch(
            Decision(
              Seq(Variable("a", Option(false)), Variable("b", Option(false))),
              And(Symbol(Variable("a", Option(false))), Symbol(Variable("b", Option(false))))
            ),
            Unsat,
            Unsat
          )
        )
      )
  }