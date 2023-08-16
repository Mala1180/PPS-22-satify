package satify.DPLLTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.{CNF, DecisionTree, PartialModel, Decision, Variable}
import satify.model.DecisionTree.*
import satify.model.CNF.*
import satify.model.Constant.{False, True}
import satify.update.dpll.CNFSimplification.*
import satify.update.dpll.PartialModelUtils.*
import satify.update.dpll.DPLL.*

class DPLLTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnf: CNF = And(Symbol(varA), Symbol(varB))

  val Dpll: CNF => Set[PartialModel] = cnf =>
    val s =
      for pmSet <- extractSolutionsFromDT(dpll(Decision(extractModelFromCnf(cnf), cnf)))
      yield explodeSolutions(pmSet)
    s.flatten

  "DPLL" should "accept a Decision and return a DecisionTree of type Branch" in {
    dpll(Decision(extractModelFromCnf(cnf), cnf)).getClass shouldBe classOf[Branch]
  }

  "DPLL" should "return a specific DecisionTree for a specific CNF" in {
    dpll(Decision(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(
          List(Variable("a"), Variable("b")),
          And(Symbol(Variable("a")), Symbol(Variable("b")))
        ),
        Leaf(Decision(List(Variable("a"), Variable("b", Some(false))), Symbol(False))),
        Branch(
          Decision(List(Variable("a"), Variable("b", Some(true))), Symbol(Variable("a"))),
          Leaf(Decision(List(Variable("a", Some(false)), Variable("b", Some(true))), Symbol(False))),
          Leaf(Decision(List(Variable("a", Some(true)), Variable("b", Some(true))), Symbol(True)))
        )
      )

  }

  "All solutions" should "be extractable from a DecisionTree" in {
    extractSolutionsFromDT(dpll(Decision(extractModelFromCnf(cnf), cnf))) shouldBe
      Set(Seq(Variable("a", Some(true)), Variable("b", Some(true))))
    Dpll(And(Symbol(varA), Or(Symbol(varB), Symbol(varC)))) shouldBe
      Set(
        Seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(true))),
        Seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(false))),
        Seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c", Some(true)))
      )
  }

  "DPLL" should "be SAT" in {
    Dpll(cnf).size should be > 0
    Dpll(And(Symbol(varA), Or(Symbol(varB), Symbol(varC)))).size should be > 0
  }

  "DPLL" should "be UNSAT" in {
    Dpll(And(Symbol(varA), Not(Symbol(varA)))) shouldBe Set.empty
    Dpll(And(Symbol(varA), And(Or(Symbol(varB), Symbol(varC)), Not(Symbol(varA))))) shouldBe Set.empty
    Dpll(
      And(
        Or(Symbol(varA), Symbol(varB)),
        And(
          Or(Not(Symbol(varA)), Symbol(varB)),
          And(Or(Symbol(varA), Not(Symbol(varB))), Or(Not(Symbol(varA)), Not(Symbol(varB))))
        )
      )
    ) shouldBe Set.empty
  }
