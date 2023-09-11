/*package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.Result.*
import satify.model.Result
import satify.model.dpll.{Decision, DecisionTree, Variable}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.OrderedSeq.seq
import satify.model.cnf.CNF
import satify.update.solver.dpll.DpllOneSol.{dpll, resume}
import satify.update.solver.dpll.utils.PartialModelUtils.extractModelFromCnf
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions

class DpllOneSolTest extends AnyFlatSpec with Matchers:

  import satify.model.dpll.OrderedSeq.given_Ordering_Variable

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val cnf: CNF = And(Or(sA, sB), Or(sB, sC))

  val expectedFirst: DecisionTree =
    Branch(
      Decision(
        seq(Variable("a"), Variable("b"), Variable("c")),
        And(Or(sA, sB),
          Or(sB, sC)
        )
      ),
      Branch(
        Decision(
          seq(Variable("a", Some(true)), Variable("b"), Variable("c")),
          Or(sB, sC)
        ),
        Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c")), Symbol(True))),
        Leaf(
          Decision(
            seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c")),
            sC
          )
        )
      ),
      Leaf(
        Decision(
          seq(Variable("a", Some(false)), Variable("b"), Variable("c")),
          And(sB, Or(sB, sC))
        )
      )
    )

  val expectedSec: DecisionTree =
    Branch(
      Decision(
        seq(Variable("a"), Variable("b"), Variable("c")),
        And(
          Or(sA, sB),
          Or(sB, sC)
        )
      ),
      Branch(
        Decision(
          seq(Variable("a", Some(true)), Variable("b"), Variable("c")),
          Or(sB, sC)
        ),
        Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c")), Symbol(True))),
        Branch(
          Decision(
            seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c")),
            sC
          ),
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
      Leaf(
        Decision(
          seq(Variable("a", Some(false)), Variable("b"), Variable("c")),
          And(sB, Or(sB, sC))
        )
      )
    )

  "DPLL" should "extract complete the decision tree one solution at a time" in {
    val (firstDt, _) = dpll(cnf)
    firstDt shouldBe expectedFirst
    val (secDt, _) = dpll(
      firstDt,
      Set(
        seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(true))),
        seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(false)))
      )
    )
    secDt shouldBe expectedSec
  }

  "DPLL" should "extract one solution at a time" in {
    val (firstDt, firstPm) = dpll(cnf)
    firstPm shouldBe Some(seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(true))))
    val (secDt, secPm) = dpll(firstDt, Set(firstPm.get))
    secPm shouldBe Some(seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(false))))
    val (_, thirdPm) = dpll(secDt, Set(firstPm.get, secPm.get))
    thirdPm shouldBe Some(seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c", Some(true))))
  }
*/