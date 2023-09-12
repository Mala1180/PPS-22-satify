package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.{Assignment, Result, Solution, Variable}
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.Result.*
import satify.model.dpll.{Decision, DecisionTree}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.cnf.CNF
import satify.update.solver.dpll.DpllOneSol.{dpll, resume}
import satify.update.solver.dpll.utils.PartialAssignmentUtils.extractParAssignmentFromCnf
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions

class DpllOneSolTest extends AnyFlatSpec with Matchers:

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val cnf: CNF = And(Or(sA, sB), Or(sB, sC))

  "DPLL" should "extract one solution at a time" in {
    dpll(cnf) shouldBe
      Solution(
        SAT,
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
