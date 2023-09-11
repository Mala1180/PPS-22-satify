package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF.*
import satify.model.cnf.CNF
import satify.model.dpll.DecisionTree.*
import satify.model.dpll.OrderedSeq.*
import satify.model.dpll.{Decision, DecisionTree, Variable}
import satify.update.solver.dpll.Dpll.*
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions
import satify.update.solver.dpll.utils.PartialModelUtils.*

class DpllTest extends AnyFlatSpec with Matchers:

  import satify.model.dpll.OrderedSeq.given_Ordering_Variable

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val cnf: CNF = And(sA, sB)

  "DPLL" should "be SAT" in {
    extractSolutions(cnf).size should be > 0
    extractSolutions(And(sA, Or(sB, sC))).size should be > 0
  }

  "DPLL" should "be UNSAT" in {
    extractSolutions(And(sA, Not(sA))) shouldBe Set.empty
    extractSolutions(And(sA, And(Or(sB, sC), Not(sA)))) shouldBe Set.empty
    extractSolutions(
      And(
        Or(sA, sB),
        And(
          Or(Not(sA), sB),
          And(Or(sA, Not(sB)), Or(Not(sA), Not(sB)))
        )
      )
    ) shouldBe Set.empty
  }

  "DPLL" should "do unit propagation when there's only a Variable inside a clause and it is in positive form" in {
    val cnf: CNF = And(sA, And(sB, Or(sB, sC)))
    dpll(Decision(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(seq(Variable("a"), Variable("b"), Variable("c")), cnf),
        Branch(
          Decision(seq(Variable("a", Some(true)), Variable("b"), Variable("c")), And(sB, Or(sB, sC))),
          Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c")), Symbol(True))),
          Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c")), Symbol(False)))
        ),
        Leaf(Decision(seq(Variable("a", Some(false)), Variable("b"), Variable("c")), Symbol(False)))
      )
  }

  "DPLL" should "do unit propagation when there's only a Variable inside a clause and it is in negative form" in {
    val cnf: CNF = And(Not(sA), Or(sA, sB))
    dpll(Decision(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(seq(Variable("a"), Variable("b")), cnf),
        Branch(
          Decision(seq(Variable("a", Some(false)), Variable("b")), sB),
          Leaf(Decision(seq(Variable("a", Some(false)), Variable("b", Some(true))), Symbol(True))),
          Leaf(Decision(seq(Variable("a", Some(false)), Variable("b", Some(false))), Symbol(False)))
        ),
        Leaf(Decision(seq(Variable("a", Some(true)), Variable("b")), Symbol(False)))
      )
  }

  "DPLL" should "do pure literals elimination when the Literal appears only in negative form" in {
    val cnf: CNF = And(Or(Not(sA), Not(sB)), Or(Not(sA), sB))
    dpll(Decision(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(seq(Variable("a"), Variable("b")), cnf),
        Leaf(Decision(seq(Variable("a", Some(false)), Variable("b")), Symbol(True))),
        Branch(
          Decision(seq(Variable("a", Some(true)), Variable("b")), And(Not(sB), sB)),
          Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(false))), Symbol(False))),
          Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(true))), Symbol(False)))
        )
      )
  }

  "DPLL" should "do pure literals elimination when the Literal appears only in positive form" in {
    val cnf: CNF =
      And(
        Or(Or(sA, Not(sB)), sB),
        And(Or(sB, sC), Or(sA, Not(sB)))
      )
    dpll(Decision(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(seq(Variable("a"), Variable("b"), Variable("c")), cnf),
        Branch(
          Decision(seq(Variable("a", Some(true)), Variable("b"), Variable("c")), Or(sB, sC)),
          Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c")), Symbol(True))),
          Branch(
            Decision(seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c")), sC),
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
            seq(Variable("a", Some(false)), Variable("b"), Variable("c")),
            And(Or(Not(sB), sB), And(Or(sB, sC), Not(sB)))
          ),
          Branch(
            Decision(seq(Variable("a", Some(false)), Variable("b", Some(false)), Variable("c")), sC),
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
          Leaf(Decision(seq(Variable("a", Some(false)), Variable("b", Some(true)), Variable("c")), Symbol(False)))
        )
      )
  }
