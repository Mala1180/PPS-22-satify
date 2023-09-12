package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.CNF.*
import satify.model.cnf.CNF
import satify.model.dpll.OrderedSeq.{given_Ordering_OptionalVariable, seq}
import satify.model.dpll.{Constraint, Decision, PartialAssignment, OptionalVariable}
import satify.update.solver.dpll.Dpll.dpll
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions
import satify.update.solver.dpll.utils.PartialModelUtils.*

class PartialModelUtilsTest extends AnyFlatSpec with Matchers:

  val varA: OptionalVariable = OptionalVariable("a")
  val varB: OptionalVariable = OptionalVariable("b")

  val cnf: CNF = And(Symbol("a"), Symbol("b"))

  "A PartialModel" should "be extractable from a CNF" in {
    extractModelFromCnf(cnf) shouldBe seq(varA, varB)
    val allCnf: CNF =
      And(Symbol("c"), And(Or(Symbol("d"), Not(Symbol("e"))), Symbol("f")))
    extractModelFromCnf(allCnf) shouldBe seq(
      OptionalVariable("c"),
      OptionalVariable("d"),
      OptionalVariable("e"),
      OptionalVariable("f")
    )
  }

  "Unconstrained OptionalVariable(s)" should "be filtered from a PartialModel" in {
    val parModel: PartialAssignment =
      seq(varA, OptionalVariable("c", Some(true)), varB, OptionalVariable("d", Some(false)))
    filterUnconstrVars(parModel) shouldBe seq(varA, varB)
  }

  "A PartialModel" should "be updated after a Constraint is applied" in {
    val parModel = extractModelFromCnf(cnf)
    updateParModel(parModel, Constraint("a", true)) shouldBe seq(OptionalVariable("a", Some(true)), varB)
    updateParModel(parModel, Constraint("b", false)) shouldBe seq(varA, OptionalVariable("b", Some(false)))
  }

  "All solutions" should "be extractable from a DecisionTree" in {
    extractSolutionsFromDT(dpll(cnf)) shouldBe
      Set(seq(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true))))
    extractSolutions(And(Symbol("a"), Or(Symbol("b"), Symbol("c")))) shouldBe
      Set(
        seq(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)), OptionalVariable("c", Some(true))),
        seq(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)), OptionalVariable("c", Some(false))),
        seq(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(false)), OptionalVariable("c", Some(true)))
      )
  }

  "All solutions" should "be extractable from a PartialModel" in {
    val pm: PartialAssignment = seq(OptionalVariable("a"), OptionalVariable("b"), OptionalVariable("c", Some(true)))
    explodeSolutions(pm) shouldBe
      Set(
        seq(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)), OptionalVariable("c", Some(true))),
        seq(OptionalVariable("a", Some(false)), OptionalVariable("b", Some(true)), OptionalVariable("c", Some(true))),
        seq(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(false)), OptionalVariable("c", Some(true))),
        seq(OptionalVariable("a", Some(false)), OptionalVariable("b", Some(false)), OptionalVariable("c", Some(true)))
      )
  }
