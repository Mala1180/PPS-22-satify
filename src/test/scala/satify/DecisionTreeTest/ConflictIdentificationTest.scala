package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.{CNF, Constraint, Variable}
import satify.model.CNF.{And, Not, Or, Symbol}
import satify.model.Constant.{False, True}
import satify.update.dpll.ConflictIdentification.hasConflict
import satify.update.dpll.CNFSimplification.simplifyCnf

class ConflictIdentificationTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  "An expression in CNF" should "not contain a conflict" in {
    val cnfAll = And(Or(Symbol(varA), Or(Symbol(varB), Not(Symbol(varA)))), Symbol(varC))
    hasConflict(simplifyCnf(cnfAll, Constraint("c", true))) shouldBe false
    val cnfOr = Or(Symbol(varA), Or(Symbol(varB), Not(Symbol(varC))))
    hasConflict(simplifyCnf(cnfOr, Constraint("c", true))) shouldBe false
    val cnfPosLit = Symbol(varA)
    hasConflict(simplifyCnf(cnfPosLit, Constraint("a", true))) shouldBe false
    val cnfNegLit = Not(Symbol(varB))
    hasConflict(simplifyCnf(cnfNegLit, Constraint("b", true))) shouldBe false
  }
