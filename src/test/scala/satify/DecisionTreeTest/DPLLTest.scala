package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.{CNF, DecisionTree, PartialModel, TreeState, Variable}
import satify.model.DecisionTree.{Branch, *}
import satify.model.CNF.*
import satify.model.Constant.{False, True}
import satify.update.dpll.CNFSimplification.*
import satify.update.dpll.PartialModelUtils.*
import satify.update.dpll.DPLL.*

class DPLLTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")

  val cnf: CNF = And(Symbol(varA), Symbol(varB))

  "DPLL" should "accept a Decision and return a DecisionTree of type Branch" in {
    dpll(TreeState(extractModelFromCnf(cnf), cnf)).getClass shouldBe classOf[Branch]
  }

  "DPLL" should "return a specific DecisionTree for a specific CNF" in {
    dpll(TreeState(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        TreeState(extractModelFromCnf(cnf), cnf),
        Branch(
          TreeState(Seq(Variable("a", Some(true)), varB), Symbol(varB)),
          Branch(
            TreeState(Seq(Variable("a", Some(true)), Variable("b", Some(true))), Symbol(True)),
            SAT,
            SAT
          ),
          Branch(
            TreeState(Seq(Variable("a", Some(true)), Variable("b", Some(false))), Symbol(False)),
            UNSAT,
            UNSAT,
          )
        ),
        Branch(
          TreeState(Seq(Variable("a", Some(false)), varB), Symbol(False)),
          UNSAT,
          UNSAT
        )
      )
  }