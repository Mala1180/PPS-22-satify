package satify.DPLLTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.tree.cnf.Bool.{False, True}
import satify.model.dpll.Constraint
import satify.model.tree.cnf.{CNF, Variable}
import satify.update.dpll.ConflictIdentification.isUnsat
import satify.update.dpll.CNFSimplification.simplifyCnf

class ConflictIdentificationTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnf: CNF = And(Or(Symbol(varA), Or(Symbol(varB), Not(Symbol(varA)))), Symbol(varC))

  "Expressions in CNF" should "not be UNSAT" in {
    isUnsat(simplifyCnf(cnf, Constraint("c", true))) shouldBe false
    val cnfOr = Or(Symbol(varA), Or(Symbol(varB), Not(Symbol(varC))))
    isUnsat(simplifyCnf(cnfOr, Constraint("c", true))) shouldBe false
    val cnfPosLit = Symbol(varA)
    isUnsat(simplifyCnf(cnfPosLit, Constraint("a", true))) shouldBe false
    val cnfNegLit = Not(Symbol(varB))
    isUnsat(simplifyCnf(cnfNegLit, Constraint("b", false))) shouldBe false
  }

  "Expressions in CNF" should "be UNSAT" in {
    isUnsat(simplifyCnf(cnf, Constraint("c", false))) shouldBe true
    val anCnf = And(Symbol(varA), Or(Symbol(varB), Symbol(varB)))
    isUnsat(simplifyCnf(anCnf, Constraint("b", false))) shouldBe true
  }
