package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.update.dpll.PartialModelUtils.*
import satify.model.CNF.*
import satify.model.{CNF, Constraint, PartialModel, Variable}

class PartialModelUtilsTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")

  val cnf: CNF = And(Symbol(varA), Symbol(varB))

  "A PartialModel" should "be extractable from a CNF" in {
    extractModelFromCnf(cnf) shouldBe Seq(varA, varB)
    val allCnf: CNF =
      And(Symbol(Variable("c")), And(Or(Symbol(Variable("d")), Not(Symbol(Variable("e")))), Symbol(Variable("f"))))
    extractModelFromCnf(allCnf) shouldBe Seq(Variable("c"), Variable("d"), Variable("e"), Variable("f"))
  }

  "Unconstrained Variable(s)" should "be filtered from a PartialModel" in {
    val parModel: PartialModel = Seq(varA, Variable("c", Some(true)), varB, Variable("d", Some(false)))
    filterUnconstrVars(parModel) shouldBe Seq(varA, varB)
  }

  "A PartialModel" should "be updated after a Constraint is applied" in {
    val parModel = extractModelFromCnf(cnf)
    updateParModel(parModel, Constraint("a", true)) shouldBe Seq(Variable("a", Some(true)), varB)
    updateParModel(parModel, Constraint("b", false)) shouldBe Seq(varA, Variable("b", Some(false)))
  }
