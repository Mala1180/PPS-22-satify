package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.CNF.*
import satify.model.cnf.CNF
import satify.model.{Variable, Assignment}
import satify.model.dpll.OrderedList.{given_Ordering_OptionalVariable, list}
import satify.model.dpll.{Constraint, Decision, PartialAssignment, OptionalVariable}
import satify.update.solver.dpll.Dpll.dpll
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions
import satify.model.dpll.PartialAssignment.*

class PartialAssignmentUtilsTest extends AnyFlatSpec with Matchers:

  val varA: OptionalVariable = OptionalVariable("a")
  val varB: OptionalVariable = OptionalVariable("b")

  val cnf: CNF = And(Symbol("a"), Symbol("b"))

  "A partial assignment" should "be extractable from a CNF" in {
    extractParAssignmentFromCnf(cnf) shouldBe PartialAssignment(list(varA, varB))
    val allCnf: CNF =
      And(Symbol("c"), And(Or(Symbol("d"), Not(Symbol("e"))), Symbol("f")))
    extractParAssignmentFromCnf(allCnf) shouldBe
      PartialAssignment(
        list(
          OptionalVariable("c"),
          OptionalVariable("d"),
          OptionalVariable("e"),
          OptionalVariable("f")
        )
      )
  }

  "Unconstrained variables" should "be filtered from a partial assignment" in {
    val partialAssignment: PartialAssignment =
      PartialAssignment(list(varA, OptionalVariable("c", Some(true)), varB, OptionalVariable("d", Some(false))))
    filterUnconstrVars(partialAssignment) shouldBe list(varA, varB)
  }

  "A partial assignment" should "be updated after a constraint is applied" in {
    val partialAssignment = extractParAssignmentFromCnf(cnf)
    updatePartialAssignment(partialAssignment, Constraint("a", true)) shouldBe
      PartialAssignment(
        list(
          OptionalVariable("a", Some(true)),
          varB
        )
      )
    updatePartialAssignment(partialAssignment, Constraint("b", false)) shouldBe
      PartialAssignment(
        list(
          varA,
          OptionalVariable("b", Some(false))
        )
      )
  }

  "All assignments" should "be extractable from a decision tree" in {
    extractParAssignments(dpll(cnf)) shouldBe
      PartialAssignment(list(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)))) :: Nil
    extractSolutions(And(Symbol("a"), Or(Symbol("b"), Symbol("c")))) shouldBe
      List(
        Assignment(Variable("a", true) :: Variable("b", true) :: Variable("c", true) :: Nil),
        Assignment(Variable("a", true) :: Variable("b", true) :: Variable("c", false) :: Nil),
        Assignment(Variable("a", true) :: Variable("b", false) :: Variable("c", true) :: Nil)
      )
  }

  "All assignments" should "be extractable from a partial assignment" in {
    val partialAssignment: PartialAssignment =
      PartialAssignment(list(OptionalVariable("a"), OptionalVariable("b"), OptionalVariable("c", Some(true))))
    explodeAssignments(partialAssignment) shouldBe
      List(
        Assignment(Variable("a", true) :: Variable("b", true) :: Variable("c", true) :: Nil),
        Assignment(Variable("a", false) :: Variable("b", true) :: Variable("c", true) :: Nil),
        Assignment(Variable("a", true) :: Variable("b", false) :: Variable("c", true) :: Nil),
        Assignment(Variable("a", false) :: Variable("b", false) :: Variable("c", true) :: Nil)
      )
  }
