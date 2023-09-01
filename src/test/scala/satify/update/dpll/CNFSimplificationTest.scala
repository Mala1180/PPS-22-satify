package satify.update.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.dpll.{Constraint, PartialModel}
import satify.update.dpll.CNFSimplification.*
import satify.model.tree.cnf.{CNF, Variable, And, Or, Not, Symbol}
import satify.model.tree.cnf.Bool.*

class CNFSimplificationTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnf: CNF = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  "An expression in CNF" should "be simplified when a Literal inside a clause is set to true" in {
    val posLitCnf = cnf
    simplifyCnf(posLitCnf, Constraint("a", true)) shouldBe Symbol(varC)
    val negLitCnf: CNF = And(Or(Symbol(varA), Or(Not(Symbol(varB)), Symbol(varC))), Symbol(varC))
    simplifyCnf(negLitCnf, Constraint("b", false)) shouldBe Symbol(varC)
  }

  "An expression in CNF" should "be simplified for each occurrence of the constrained Literal" in {
    val cnf = And(Or(Symbol(varA), Symbol(varB)), And(Or(Symbol(varC), Or(Symbol(varA), Symbol(varB))), Symbol(varC)))
    simplifyCnf(cnf, Constraint("a", true)) shouldBe Symbol(varC)
  }

  "An expression in CNF" should "be simplified only on the branches where the Literal is found" in {
    val cnf: CNF = And(Or(Symbol(varA), Or(Not(Symbol(varB)), Symbol(varC))), Or(Symbol(varA), Symbol(varC)))
    simplifyCnf(cnf, Constraint("b", false)) shouldBe Or(Symbol(varA), Symbol(varC))
  }

  "An expression in CNF" should "be simplified when a Literal inside a clause is set to false" in {
    val posLitCnf = cnf
    simplifyCnf(posLitCnf, Constraint("a", false)) shouldBe And(Symbol(varB), Symbol(varC))
    val negLitCnf: CNF = And(Or(Symbol(varA), Not(Symbol(varB))), Symbol(varC))
    simplifyCnf(negLitCnf, Constraint("b", true)) shouldBe And(Symbol(varA), Symbol(varC))
    val complexCnf = And(Or(Symbol(varA), Or(Symbol(varA), Symbol(varB))), Symbol(varC))
    simplifyCnf(complexCnf, Constraint("a", false)) shouldBe And(Symbol(varB), Symbol(varC))
    val complexCnf1 = And(Or(Symbol(varA), Or(Symbol(varA), Or(Symbol(varB), Symbol(varC)))), Symbol(varC))
    simplifyCnf(complexCnf1, Constraint("a", false)) shouldBe And(Or(Symbol(varB), Symbol(varC)), Symbol(varC))
  }

  "An expression in CNF" should "be simplified when an And contains all true Literals" in {
    val cnf = And(Symbol(varA), Symbol(True))
    simplifyCnf(cnf, Constraint("a", true)) shouldBe Symbol(True)
  }

  "An expression in CNF" should "be simplified when an And contains at least a True Literal" in {
    val cnf = And(Symbol(True), And(Symbol(varB), Symbol(varC)))
    simplifyCnf(cnf, Constraint("b", true)) shouldBe Symbol(varC)
    val complexCnf = And(Symbol(varA), And(Symbol(varB), And(Symbol(varA), Symbol(varC))))
    simplifyCnf(complexCnf, Constraint("a", true)) shouldBe And(Symbol(varB), Symbol(varC))
  }

  "An expression in CNF" should "be simplified when a Literal appears in positive and negative form" in {
    val cnf = And(Symbol(varA), Or(Symbol(varB), Or(Symbol(varC), Not(Symbol(varA)))))
    simplifyCnf(cnf, Constraint("a", true)) shouldBe Or(Symbol(varB), Symbol(varC))
    val complexCnf = And(Not(Symbol(varA)), Or(Symbol(varB), Or(Not(Symbol(varA)), Not(Symbol(varA)))))
    simplifyCnf(complexCnf, Constraint("a", false)) shouldBe Symbol(True)
    val complexCnf1 = And(Symbol(varA), Or(Symbol(varB), Or(Not(Symbol(varA)), Not(Symbol(varA)))))
    simplifyCnf(complexCnf1, Constraint("a", false)) shouldBe Symbol(False)
  }

  "An expression in CNF" should "be simplified when it contains only Or(s) or Literal(s)" in {
    val cnfOr = Or(Not(Symbol(varA)), Or(Or(Symbol(varB), Not(Symbol(varA))), Not(Symbol(varC))))
    simplifyCnf(cnfOr, Constraint("a", false)) shouldBe Symbol(True)
    simplifyCnf(cnfOr, Constraint("a", true)) shouldBe Or(Symbol(varB), Not(Symbol(varC)))
    val cnfLit = Not(Symbol(varA))
    simplifyCnf(cnfLit, Constraint("a", false)) shouldBe Symbol(True)
    simplifyCnf(cnfLit, Constraint("a", true)) shouldBe Symbol(False)
  }
