package satify.model.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.OrderedList.list
import satify.model.dpll.PartialAssignment
import satify.model.dpll.{Constraint, Decision, OptionalVariable, PartialAssignment}
import satify.model.{Assignment, Variable}

class PartialAssignmentTest extends AnyFlatSpec with Matchers:

  "A set of assignments" should
    "be extracted from a partial assignment by exploding its unconstrained variables" in {
      val partialAssignment: PartialAssignment =
        PartialAssignment(
          OptionalVariable("a") ::
            OptionalVariable("b") ::
            OptionalVariable("c", Some(true)) :: Nil
        )
      Set(partialAssignment.toAssignments: _*) shouldBe
        Set(
          Assignment(Variable("a", true) :: Variable("b", true) :: Variable("c", true) :: Nil),
          Assignment(Variable("a", false) :: Variable("b", true) :: Variable("c", true) :: Nil),
          Assignment(Variable("a", true) :: Variable("b", false) :: Variable("c", true) :: Nil),
          Assignment(Variable("a", false) :: Variable("b", false) :: Variable("c", true) :: Nil)
        )
    }

  "A unique assignment" should
    "be extracted from a partial assignment with all variables constrained" in {
      val partialAssignment =
        PartialAssignment(
          OptionalVariable("a", Some(true)) ::
            OptionalVariable("b", Some(true)) ::
            OptionalVariable("c", Some(true)) :: Nil
        )
      partialAssignment.toAssignments shouldBe
        Assignment(Variable("a", true) :: Variable("b", true) :: Variable("c", true) :: Nil) :: Nil
    }
