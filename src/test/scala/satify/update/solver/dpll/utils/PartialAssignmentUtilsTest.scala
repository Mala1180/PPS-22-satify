package satify.update.solver.dpll.utils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.{Assignment, Variable}
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.solver.DecisionTree.{Branch, Leaf}
import satify.model.solver.OrderedList.list
import satify.model.solver.{Constraint, Decision, DecisionTree, OptionalVariable, PartialAssignment}
import satify.update.solver.dpll.utils.PartialAssignmentUtils.*

class PartialAssignmentUtilsTest extends AnyFlatSpec with Matchers:

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
    extractParAssignmentFromCnf(cnf) shouldBe PartialAssignment(varA :: varB :: Nil)
    val allCnf: CNF =
      And(Symbol("c"), And(Or(Symbol("d"), Not(Symbol("e"))), Symbol("f")))
    extractParAssignmentFromCnf(allCnf) shouldBe
      PartialAssignment(
        OptionalVariable("c") ::
          OptionalVariable("d") ::
          OptionalVariable("e") ::
          OptionalVariable("f") :: Nil
      )
  }

  "Unconstrained variables" should "be filtered from a partial assignment" in {
    val partialAssignment: PartialAssignment =
      PartialAssignment(
        varA :: OptionalVariable("c", Some(true)) :: varB :: OptionalVariable("d", Some(false)) :: Nil
      )
    filterUnconstrVars(partialAssignment) shouldBe varA :: varB :: Nil
  }

  "A partial assignment" should "be updated after a constraint is applied" in {
    val partialAssignment = extractParAssignmentFromCnf(cnf)
    updatePartialAssignment(partialAssignment, Constraint("a", true)) shouldBe
      PartialAssignment(OptionalVariable("a", Some(true)) :: varB :: Nil)
    updatePartialAssignment(partialAssignment, Constraint("b", false)) shouldBe
      PartialAssignment(varA :: OptionalVariable("b", Some(false)) :: Nil)
  }

  "Partial assignments" should "be extractable from a decision tree" in {
    extractParAssignments(dt) shouldBe
      PartialAssignment(OptionalVariable("a", Some(true)) :: OptionalVariable("b", Some(true)) :: Nil) :: Nil
  }

  "All assignments" should "be extractable from a decision tree" in {
    val allAssignments =
      for partialAssignment <- extractParAssignments(dt)
      yield partialAssignment.toAssignments
    allAssignments.flatten shouldBe Assignment(Variable("a", true) :: Variable("b", true) :: Nil) :: Nil
  }
