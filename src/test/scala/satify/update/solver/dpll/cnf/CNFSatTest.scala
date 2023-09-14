package satify.update.solver.dpll.cnf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.CNF
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.dpll.Constraint
import satify.update.solver.dpll.cnf.CNFSat.isUnsat
import satify.update.solver.dpll.cnf.CNFSimplification.simplifyCnf

class CNFSatTest extends AnyFlatSpec with Matchers:

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val cnf: CNF = And(Or(sA, Or(sB, Not(sA))), sC)

  "Expressions in CNF" should "not be UNSAT" in {
    isUnsat(simplifyCnf(cnf, Constraint("c", true))) shouldBe false
    val cnfOr = Or(sA, Or(sB, Not(sC)))
    isUnsat(simplifyCnf(cnfOr, Constraint("c", true))) shouldBe false
    val cnfPosLit = sA
    isUnsat(simplifyCnf(cnfPosLit, Constraint("a", true))) shouldBe false
    val cnfNegLit = Not(sB)
    isUnsat(simplifyCnf(cnfNegLit, Constraint("b", false))) shouldBe false
  }

  "Expressions in CNF" should "be UNSAT" in {
    isUnsat(simplifyCnf(cnf, Constraint("c", false))) shouldBe true
    val anCnf = And(sA, Or(sB, sB))
    isUnsat(simplifyCnf(anCnf, Constraint("b", false))) shouldBe true
  }
