package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.*
import satify.update.dpll.CNFSimplification.*
import satify.model.{Constraint, PartialModel}
import satify.model.CNF.*
import satify.model.Constant.True

class CNFSimplificationTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnf: CNF = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  "A PartialModel" should "be extractable from a CNF" in {
    val parModel: PartialModel = Seq(Variable("a"), Variable("b"), Variable("c"))
    parModel shouldBe extractModelFromCnf(cnf)
  }

  "An expression in CNF" should "be simplified when a Literal inside a clause is set to true" in {
    val posLitCnf = cnf
    simplifyUppermostOr(posLitCnf, Constraint("a", true)) shouldBe And(Symbol(True), Symbol(varC))
    val negLitCnf: CNF = And(Or(Symbol(varA), Or(Not(Symbol(varB)), Symbol(varC))), Symbol(varC))
    simplifyUppermostOr(negLitCnf, Constraint("b", false)) shouldBe And(Symbol(True), Symbol(varC))
  }

  "An expression in CNF" should "be simplified for each occurrence of the constrained Literal" in {
    val cnf = And(Or(Symbol(varA), Symbol(varB)), And(Or(Symbol(varC), Or(Symbol(varA), Symbol(varB))), Symbol(varC)))
    simplifyUppermostOr(cnf, Constraint("a", true)) shouldBe
      And(Symbol(True), And(Symbol(True), Symbol(varC)))
  }

  "An expression in CNF" should "be simplified only on the branches where the Literal is found" in {
    val cnf: CNF = And(Or(Symbol(varA), Or(Not(Symbol(varB)), Symbol(varC))), Or(Symbol(varA), Symbol(varC)))
    simplifyUppermostOr(cnf, Constraint("b", false)) shouldBe And(Symbol(True), Or(Symbol(varA), Symbol(varC)))
  }

  "An expression in CNF" should "be simplified when a Literal inside a clause is set to false" in {
    val posLitCnf = cnf
    simplifyClosestOr(posLitCnf, Constraint("a", false)) shouldBe And(Symbol(varB), Symbol(varC))
    val negLitCnf: CNF = And(Or(Symbol(varA), Not(Symbol(varB))), Symbol(varC))
    simplifyClosestOr(negLitCnf, Constraint("b", true)) shouldBe And(Symbol(varA), Symbol(varC))
    val complexCnf = And(Or(Symbol(varA), Or(Symbol(varA), Symbol(varB))), Symbol(varC))
    simplifyClosestOr(complexCnf, Constraint("a", false)) shouldBe And(Symbol(varB), Symbol(varC))
    val complexCnf1 = And(Or(Symbol(varA), Or(Symbol(varA), Or(Symbol(varB), Symbol(varC)))), Symbol(varC))
    simplifyClosestOr(complexCnf1, Constraint("a", false)) shouldBe And(Or(Symbol(varB), Symbol(varC)), Symbol(varC))
  }
  
  
