package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.*
import satify.model.dpll.OrderedSeq.{given_Ordering_Variable, seq}
import satify.model.dpll.{Constraint, Decision, PartialModel}
import satify.model.{CNF, Variable}
import satify.update.solver.dpll.Dpll.dpll
import satify.update.solver.dpll.utils.PartialModelUtils.*

class PartialModelUtilsTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnf: CNF = And(Symbol(varA), Symbol(varB))

  val Dpll: CNF => Set[PartialModel] = cnf =>
    val s =
      for pmSet <- extractSolutionsFromDT(dpll(Decision(extractModelFromCnf(cnf), cnf)))
      yield explodeSolutions(pmSet)
    s.flatten

  "A PartialModel" should "be extractable from a CNF" in {
    extractModelFromCnf(cnf) shouldBe seq(varA, varB)
    val allCnf: CNF =
      And(Symbol(Variable("c")), And(Or(Symbol(Variable("d")), Not(Symbol(Variable("e")))), Symbol(Variable("f"))))
    extractModelFromCnf(allCnf) shouldBe seq(Variable("c"), Variable("d"), Variable("e"), Variable("f"))
  }

  "Unconstrained Variable(s)" should "be filtered from a PartialModel" in {
    val parModel: PartialModel = seq(varA, Variable("c", Some(true)), varB, Variable("d", Some(false)))
    filterUnconstrVars(parModel) shouldBe seq(varA, varB)
  }

  "A PartialModel" should "be updated after a Constraint is applied" in {
    val parModel = extractModelFromCnf(cnf)
    updateParModel(parModel, Constraint("a", true)) shouldBe seq(Variable("a", Some(true)), varB)
    updateParModel(parModel, Constraint("b", false)) shouldBe seq(varA, Variable("b", Some(false)))
  }

  "All solutions" should "be extractable from a DecisionTree" in {
    extractSolutionsFromDT(dpll(Decision(extractModelFromCnf(cnf), cnf))) shouldBe
      Set(seq(Variable("a", Some(true)), Variable("b", Some(true))))
    Dpll(And(Symbol(varA), Or(Symbol(varB), Symbol(varC)))) shouldBe
      Set(
        seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(true))),
        seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(false))),
        seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c", Some(true)))
      )
  }

  "All solutions" should "be extractable from a PartialModel" in {
    val pm: PartialModel = seq(Variable("a"), Variable("b"), Variable("c", Some(true)))
    explodeSolutions(pm) shouldBe
      Set(
        seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(true))),
        seq(Variable("a", Some(false)), Variable("b", Some(true)), Variable("c", Some(true))),
        seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c", Some(true))),
        seq(Variable("a", Some(false)), Variable("b", Some(false)), Variable("c", Some(true)))
      )
  }
