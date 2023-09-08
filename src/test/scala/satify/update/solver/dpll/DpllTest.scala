package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF.*
import satify.model.cnf.{CNF, Variable}
import satify.model.dpll.DecisionTree.*
import satify.model.dpll.OrderedSeq.*
import satify.model.dpll.{Decision, DecisionTree}
import satify.model.cnf.{CNF, Variable}
import satify.update.solver.dpll.Dpll.*
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions
import satify.update.solver.dpll.utils.PartialModelUtils.*

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

  "DPLL" should "do unit propagation when there's only a Variable inside a clause and it is in positive form" in {
    val cnf: CNF = And(Symbol(varA), And(Symbol(varB), Or(Symbol(varB), Symbol(varC))))
    dpll(Decision(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(seq(varA, varB, varC), cnf),
        Branch(
          Decision(seq(Variable("a", Some(true)), varB, varC), And(Symbol(varB), Or(Symbol(varB), Symbol(varC)))),
          Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(true)), varC), Symbol(True))),
          Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(false)), varC), Symbol(False)))
        ),
        Leaf(Decision(seq(Variable("a", Some(false)), varB, varC), Symbol(False)))
      )
  }

  "DPLL" should "do unit propagation when there's only a Variable inside a clause and it is in negative form" in {
    val cnf: CNF = And(Not(Symbol(varA)), Or(Symbol(varA), Symbol(varB)))
    dpll(Decision(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(seq(varA, varB), cnf),
        Branch(
          Decision(seq(Variable("a", Some(false)), varB), Symbol(varB)),
          Leaf(Decision(seq(Variable("a", Some(false)), Variable("b", Some(true))), Symbol(True))),
          Leaf(Decision(seq(Variable("a", Some(false)), Variable("b", Some(false))), Symbol(False)))
        ),
        Leaf(Decision(seq(Variable("a", Some(true)), varB), Symbol(False)))
      )
  }

  "DPLL" should "do pure literals elimination when the Literal appears only in negative form" in {
    val cnf: CNF = And(Or(Not(Symbol(varA)), Not(Symbol(varB))), Or(Not(Symbol(varA)), Symbol(varB)))
    dpll(Decision(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(seq(varA, varB), cnf),
        Leaf(Decision(seq(Variable("a", Some(false)), varB), Symbol(True))),
        Branch(
          Decision(seq(Variable("a", Some(true)), varB), And(Not(Symbol(varB)), Symbol(varB))),
          Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(false))), Symbol(False))),
          Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(true))), Symbol(False)))
        )
      )
  }

  "DPLL" should "do pure literals elimination when the Literal appears only in positive form" in {
    val cnf: CNF =
      And(
        Or(Or(Symbol(varA), Not(Symbol(varB))), Symbol(varB)),
        And(Or(Symbol(varB), Symbol(varC)), Or(Symbol(varA), Not(Symbol(varB))))
      )
    dpll(Decision(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(seq(varA, varB, varC), cnf),
        Branch(
          Decision(seq(Variable("a", Some(true)), varB, varC), Or(Symbol(varB), Symbol(varC))),
          Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(true)), varC), Symbol(True))),
          Branch(
            Decision(seq(Variable("a", Some(true)), Variable("b", Some(false)), varC), Symbol(varC)),
            Leaf(
              Decision(
                seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c", Some(true))),
                Symbol(True)
              )
            ),
            Leaf(
              Decision(
                seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c", Some(false))),
                Symbol(False)
              )
            )
          )
        ),
        Branch(
          Decision(
            seq(Variable("a", Some(false)), varB, varC),
            And(Or(Not(Symbol(varB)), Symbol(varB)), And(Or(Symbol(varB), Symbol(varC)), Not(Symbol(varB))))
          ),
          Branch(
            Decision(seq(Variable("a", Some(false)), Variable("b", Some(false)), varC), Symbol(varC)),
            Leaf(
              Decision(
                seq(Variable("a", Some(false)), Variable("b", Some(false)), Variable("c", Some(true))),
                Symbol(True)
              )
            ),
            Leaf(
              Decision(
                seq(Variable("a", Some(false)), Variable("b", Some(false)), Variable("c", Some(false))),
                Symbol(False)
              )
            )
          ),
          Leaf(Decision(seq(Variable("a", Some(false)), Variable("b", Some(true)), varC), Symbol(False)))
        )
      )
  }
