package satify.update.solver.dpll.impl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Result.*
import satify.model.Status.*
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.solver.DecisionTree.{Branch, Leaf}
import satify.model.solver.{Decision, DecisionTree}
import satify.model.{Assignment, Result, Solution, Variable}
import satify.update.solver.dpll.impl.DpllFinder.{find, findNext, resume}
import satify.update.solver.dpll.utils.PartialAssignmentUtils.extractParAssignmentFromCnf

class DpllFinderTest extends AnyFlatSpec with Matchers:

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

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
    secondAssignment should matchPattern { case Some(_) => }
    (assignments filter (a => a != firstAssignment)) contains secondAssignment.get shouldBe true
    findNext() should matchPattern { case Some(_) => }
    findNext() shouldBe None
  }

  "Finder" should "return the same assignments in the same order between two different runs" in {
    def findAll(cnf: CNF): List[Assignment] =
      def findOthers: List[Assignment] =
        findNext().fold(Nil)(a => a +: findOthers)
      find(cnf).assignments ++ findOthers

    val cnf = Or(Or(sA, sB), Or(sB, sC))
    findAll(cnf) shouldBe findAll(cnf)
  }
