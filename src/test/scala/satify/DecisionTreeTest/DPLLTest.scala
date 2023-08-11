package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.{CNF, DecisionTree, PartialModel, TreeState, Variable}
import satify.model.DecisionTree.*
import satify.model.CNF.*
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

  "A PartialModel" should "be extractable from a CNF" in {
    val parModel: PartialModel = Seq(Variable("a"), Variable("b"))
    extractModelFromCnf(cnf) shouldBe parModel
  }
