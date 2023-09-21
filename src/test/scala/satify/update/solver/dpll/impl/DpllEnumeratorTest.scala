package satify.update.solver.dpll.impl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.cnf.CNF.*
import satify.update.solver.dpll.impl.DpllEnumerator.enumerate
import satify.model.Result.*
import satify.model.{Assignment, Variable}

class DpllEnumeratorTest extends AnyFlatSpec with Matchers:

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  "Enumerator" should "return SAT" in {
    enumerate(And(sA, sB)).result shouldBe SAT
    enumerate(And(sA, Or(sB, sC))).result shouldBe SAT
  }

  "Enumerator" should "return UNSAT" in {
    enumerate(And(sA, Not(sA))).result shouldBe UNSAT
    enumerate(And(sA, And(Or(sB, sC), Not(sA)))).result shouldBe UNSAT
    enumerate(
      And(
        Or(sA, sB),
        And(
          Or(Not(sA), sB),
          And(Or(sA, Not(sB)), Or(Not(sA), Not(sB)))
        )
      )
    ).result shouldBe UNSAT
  }

  "Enumerator" should "return all the assignments of a satisfiable expression" in {
    Set(enumerate(Or(sA, Or(sA, sB))).assignments: _*) shouldBe
      Set(
        Assignment(Variable("a", true) :: Variable("b", true) :: Nil),
        Assignment(Variable("a", true) :: Variable("b", false) :: Nil),
        Assignment(Variable("a", false) :: Variable("b", true) :: Nil)
      )
    Set(enumerate(And(sA, Or(sA, Or(sB, Not(sC))))).assignments: _*) shouldBe
      Set(
        Assignment(Variable("a", true) :: Variable("b", true) :: Variable("c", true) :: Nil),
        Assignment(Variable("a", true) :: Variable("b", false) :: Variable("c", true) :: Nil),
        Assignment(Variable("a", true) :: Variable("b", true) :: Variable("c", false) :: Nil),
        Assignment(Variable("a", true) :: Variable("b", false) :: Variable("c", false) :: Nil)
      )
  }
