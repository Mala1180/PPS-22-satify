package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.Bool.{False, True}
import satify.model.CNF.*
import satify.model.dpll.DecisionTree.*
import satify.model.dpll.OrderedSeq.*
import satify.model.dpll.{Decision, DecisionTree}
import satify.model.{CNF, Variable}
import satify.update.solver.dpll.Dpll.*
import satify.update.solver.dpll.Dpll.extractSolutions
import satify.update.solver.dpll.PartialModelUtils.*

class DpllTest extends AnyFlatSpec with Matchers:

  import satify.model.dpll.OrderedSeq.given_Ordering_Variable

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnf: CNF = And(Symbol(varA), Symbol(varB))

  "DPLL" should "be SAT" in {
    extractSolutions(cnf).size should be > 0
    extractSolutions(And(Symbol(varA), Or(Symbol(varB), Symbol(varC)))).size should be > 0
  }

  "DPLL" should "be UNSAT" in {
    extractSolutions(And(Symbol(varA), Not(Symbol(varA)))) shouldBe Set.empty
    extractSolutions(And(Symbol(varA), And(Or(Symbol(varB), Symbol(varC)), Not(Symbol(varA))))) shouldBe Set.empty
    extractSolutions(
      And(
        Or(Symbol(varA), Symbol(varB)),
        And(
          Or(Not(Symbol(varA)), Symbol(varB)),
          And(Or(Symbol(varA), Not(Symbol(varB))), Or(Not(Symbol(varA)), Not(Symbol(varB))))
        )
      )
    ) shouldBe Set.empty
  }