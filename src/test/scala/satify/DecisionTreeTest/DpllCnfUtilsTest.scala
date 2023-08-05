package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.*
import satify.model.CNF.{And, Or, Symbol, Not}
import satify.model.dpll.DpllCnfUtils.*
import satify.model.dpll.{Constraint, PartialModel}

class DpllCnfUtilsTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnf: CNF = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  "A PartialModel" should "be extractable from a CNF" in {
    val parModel: PartialModel = Seq(Variable("a"), Variable("c"), Variable("c"))
    parModel should be equals extractModelFromCnf(cnf)
  }

  "An expression in CNF" should "be mapped to another CNF by setting a variable" in {
    updateCnf(cnf, Constraint("b", false)) should be equals
      And(Or(Symbol(Variable("a")), Symbol(Variable("b", Option(false)))), Symbol(Variable("c")))

    updateCnf(And(Or(Symbol(varA), Symbol(varB)), Not(Symbol(varC))), Constraint("c", true)) should be equals
      And(Or(Symbol(Variable("a")), Symbol(Variable("b"))), Symbol(Variable("c", Option(true))))
  }
