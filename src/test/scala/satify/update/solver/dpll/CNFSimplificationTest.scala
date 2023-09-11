package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF.*
import satify.model.cnf.CNF
import satify.model.dpll.Constraint
import satify.update.solver.dpll.cnf.CNFSimplification.*

class CNFSimplificationTest extends AnyFlatSpec with Matchers:

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val cnf: CNF = And(Or(sA, sB), sC)

  "An expression in CNF" should "be simplified when a Literal inside a clause is set to true" in {
    val posLitCnf = cnf
    simplifyCnf(posLitCnf, Constraint("a", true)) shouldBe sC
    val negLitCnf: CNF = And(Or(sA, Or(Not(sB), sC)), sC)
    simplifyCnf(negLitCnf, Constraint("b", false)) shouldBe sC
  }

  "An expression in CNF" should "be simplified for each occurrence of the constrained Literal" in {
    val cnf = And(Or(sA, sB), And(Or(sC, Or(sA, sB)), sC))
    simplifyCnf(cnf, Constraint("a", true)) shouldBe sC
  }

  "An expression in CNF" should "be simplified only on the branches where the Literal is found" in {
    val cnf: CNF = And(Or(sA, Or(Not(sB), sC)), Or(sA, sC))
    simplifyCnf(cnf, Constraint("b", false)) shouldBe Or(sA, sC)
  }

  "An expression in CNF" should "be simplified when a Literal inside a clause is set to false" in {
    val posLitCnf = cnf
    simplifyCnf(posLitCnf, Constraint("a", false)) shouldBe And(sB, sC)
    val negLitCnf: CNF = And(Or(sA, Not(sB)), sC)
    simplifyCnf(negLitCnf, Constraint("b", true)) shouldBe And(sA, sC)
    val complexCnf = And(Or(sA, Or(sA, sB)), sC)
    simplifyCnf(complexCnf, Constraint("a", false)) shouldBe And(sB, sC)
    val complexCnf1 = And(Or(sA, Or(sA, Or(sB, sC))), sC)
    simplifyCnf(complexCnf1, Constraint("a", false)) shouldBe And(Or(sB, sC), sC)
  }

  "An expression in CNF" should "be simplified when an And contains all true Literals" in {
    val cnf = And(sA, Symbol(True))
    simplifyCnf(cnf, Constraint("a", true)) shouldBe Symbol(True)
  }

  "An expression in CNF" should "be simplified when an And contains at least a True Literal" in {
    val cnf = And(Symbol(True), And(sB, sC))
    simplifyCnf(cnf, Constraint("b", true)) shouldBe sC
    val complexCnf = And(sA, And(sB, And(sA, sC)))
    simplifyCnf(complexCnf, Constraint("a", true)) shouldBe And(sB, sC)
  }

  "An expression in CNF" should "be simplified when a Literal appears in positive and negative form" in {
    val cnf = And(sA, Or(sB, Or(sC, Not(sA))))
    simplifyCnf(cnf, Constraint("a", true)) shouldBe Or(sB, sC)
    val complexCnf = And(Not(sA), Or(sB, Or(Not(sA), Not(sA))))
    simplifyCnf(complexCnf, Constraint("a", false)) shouldBe Symbol(True)
    val complexCnf1 = And(sA, Or(sB, Or(Not(sA), Not(sA))))
    simplifyCnf(complexCnf1, Constraint("a", false)) shouldBe Symbol(False)
  }

  "An expression in CNF" should "be simplified when it contains only Or(s) or Literal(s)" in {
    val cnfOr = Or(Not(sA), Or(Or(sB, Not(sA)), Not(sC)))
    simplifyCnf(cnfOr, Constraint("a", false)) shouldBe Symbol(True)
    simplifyCnf(cnfOr, Constraint("a", true)) shouldBe Or(sB, Not(sC))
    val cnfLit = Not(sA)
    simplifyCnf(cnfLit, Constraint("a", false)) shouldBe Symbol(True)
    simplifyCnf(cnfLit, Constraint("a", true)) shouldBe Symbol(False)
  }
