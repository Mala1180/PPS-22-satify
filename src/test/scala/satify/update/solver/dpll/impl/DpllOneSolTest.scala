package satify.update.solver.dpll.impl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Result.*
import satify.model.Status.*
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.PartialAssignment.extractParAssignmentFromCnf
import satify.model.dpll.{Decision, DecisionTree}
import satify.model.{Assignment, Result, Solution, Variable}
import satify.update.solver.dpll.impl.DpllOneSol.{dpll, resume}

class DpllOneSolTest extends AnyFlatSpec with Matchers:

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val cnf: CNF = And(Or(sA, sB), Or(sB, sC))

  "DPLL" should "extract one solution at a time" in {
    dpll(cnf) shouldBe
      Solution(
        SAT,
        PARTIAL,
        List(
          Assignment(
            List(Variable("a", true), Variable("b", true), Variable("c", true))
          )
        )
      )
    dpll() shouldBe
      Assignment(
        List(Variable("a", true), Variable("b", true), Variable("c", false))
      )
    dpll() shouldBe
      Assignment(
        List(Variable("a", true), Variable("b", false), Variable("c", true))
      )
  }
