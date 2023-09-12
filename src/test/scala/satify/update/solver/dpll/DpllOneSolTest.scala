package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Assignment
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.Result.*
import satify.model.{Result, Solution}
import satify.model.dpll.{Decision, DecisionTree, OptionalVariable}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.OrderedSeq.seq
import satify.model.cnf.CNF
import satify.update.solver.dpll.DpllOneSol.{dpll, resume}
import satify.update.solver.dpll.utils.PartialModelUtils.extractModelFromCnf
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions

class DpllOneSolTest extends AnyFlatSpec with Matchers:

  import satify.model.dpll.OrderedSeq.given_Ordering_OptionalVariable

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val cnf: CNF = And(Or(sA, sB), Or(sB, sC))

  "DPLL" should "extract one solution at a time" in {
    dpll(cnf) shouldBe
      Solution(
        SAT,
        List(
          Assignment(
            seq(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)), OptionalVariable("c", Some(true)))
          )
        )
      )
    dpll() shouldBe
      Assignment(
        seq(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)), OptionalVariable("c", Some(false)))
      )
    dpll() shouldBe
      Assignment(
        seq(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(false)), OptionalVariable("c", Some(true)))
      )
  }
