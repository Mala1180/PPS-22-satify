package satify.update.solver.dpll.impl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Result.*
import satify.model.Status.*
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.{Decision, DecisionTree}
import satify.model.{Assignment, Result, Solution, Variable}
import satify.update.solver.dpll.impl.DpllFinder.{find, findNext, resume}
import satify.update.solver.dpll.utils.PartialAssignmentUtils.extractParAssignmentFromCnf


class DpllFinderTest extends AnyFlatSpec with Matchers:

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val filterAssignments: (List[Assignment], List[Assignment]) => List[Assignment] =
    (assignments, done) =>
      for
        assignment <- assignments
        if !(done contains assignment)
      yield assignment

  "Finder" should "return SAT" in {
    find(And(sA, sB)).result shouldBe SAT
    find(And(sA, Or(sB, sC))).result shouldBe SAT
  }

  "Finder" should "return UNSAT" in {
    find(And(sA, Not(sA))).result shouldBe UNSAT
    find(And(sA, And(Or(sB, sC), Not(sA)))).result shouldBe UNSAT
    find(
      And(
        Or(sA, sB),
        And(
          Or(Not(sA), sB),
          And(Or(sA, Not(sB)), Or(Not(sA), Not(sB)))
        )
      )
    ).result shouldBe UNSAT
  }

  "Finder" should "return one assignment at a time of a satisfiable expression" in {
    val cnf: CNF = And(Or(sA, sB), sC)
    val assignments: List[Assignment] =
      Assignment(Variable("a", true) :: Variable("b", true) :: Variable("c", true) :: Nil) ::
        Assignment(Variable("a", true) :: Variable("b", false) :: Variable("c", true) :: Nil) ::
        Assignment(Variable("a", false) :: Variable("b", true) :: Variable("c", true) :: Nil) :: Nil

    val firstAssignment = find(cnf).assignments.head
    assignments contains firstAssignment shouldBe true
    val secondAssignment = findNext()
    filterAssignments(assignments, firstAssignment :: Nil) contains secondAssignment shouldBe true
    val thirdAssignment = findNext()
    filterAssignments(assignments, firstAssignment :: secondAssignment :: Nil) contains thirdAssignment shouldBe true
    findNext() shouldBe Assignment(Nil)
  }
