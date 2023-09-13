package satify.model.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.OrderedList.list
import satify.model.dpll.PartialAssignment.*
import satify.model.dpll.{Constraint, Decision, OptionalVariable, PartialAssignment}
import satify.model.{Assignment, Variable}

class PartialAssignmentTest extends AnyFlatSpec with Matchers:

  import satify.model.dpll.OrderedList.given

  val varA: OptionalVariable = OptionalVariable("a")
  val varB: OptionalVariable = OptionalVariable("b")

  val cnf: CNF = And(Symbol("a"), Symbol("b"))

  val dt: DecisionTree = Branch(
    Decision(
      PartialAssignment(List(varA, varB)),
      And(Symbol("a"), Symbol("b"))
    ),
    Branch(
      Decision(PartialAssignment(List(OptionalVariable("a", Some(true)), varB)), Symbol("b")),
      Leaf(
        Decision(
          PartialAssignment(List(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)))),
          Symbol(True)
        )
      ),
      Leaf(
        Decision(
          PartialAssignment(List(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(false)))),
          Symbol(False)
        )
      )
    ),
    Leaf(
      Decision(PartialAssignment(List(OptionalVariable("a", Some(false)), varB)), Symbol(False))
    )
  )

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

  "Partial assignments" should "be extractable from a decision tree" in {
    extractParAssignments(dt) shouldBe
      PartialAssignment(list(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)))) :: Nil
  }

  "All assignments" should "be extractable from a decision tree" in {
    val allAssignments =
      for partialAssignment <- extractParAssignments(dt)
      yield partialAssignment.toAssignments
    allAssignments.flatten shouldBe List(Assignment(Variable("a", true) :: Variable("b", true) :: Nil))
  }

  "All assignments" should "be extractable from a partial assignment" in {
    val partialAssignment: PartialAssignment =
      PartialAssignment(list(OptionalVariable("a"), OptionalVariable("b"), OptionalVariable("c", Some(true))))
    partialAssignment.toAssignments shouldBe
      List(
        Assignment(Variable("a", true) :: Variable("b", true) :: Variable("c", true) :: Nil),
        Assignment(Variable("a", false) :: Variable("b", true) :: Variable("c", true) :: Nil),
        Assignment(Variable("a", true) :: Variable("b", false) :: Variable("c", true) :: Nil),
        Assignment(Variable("a", false) :: Variable("b", false) :: Variable("c", true) :: Nil)
      )
  }
