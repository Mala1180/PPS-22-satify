package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Bool.{False, True}
import satify.model.CNF.{And, Not, Or, Symbol}
import satify.model.Result.*
import satify.model.dpll.{Decision, DecisionTree}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.OrderedSeq.seq
import satify.model.{CNF, Result, Variable}
import satify.update.solver.dpll.DpllOneSol.{dpll, resume}
import satify.update.solver.dpll.utils.PartialModelUtils.extractModelFromCnf
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions

class DpllOneSolTest extends AnyFlatSpec with Matchers:

  import satify.model.dpll.OrderedSeq.given_Ordering_Variable

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnf: CNF = And(Or(Symbol(varA), Symbol(varB)), Or(Symbol(varB), Symbol(varC)))

  val expectedFirst: DecisionTree =
    Branch(
      Decision(
        seq(varA, varB, varC),
        And(
          Or(Symbol(varA), Symbol(varB)),
          Or(Symbol(varB), Symbol(varC))
        )
      ),
      Branch(
        Decision(
          seq(Variable("a", Some(true)), varB, varC),
          Or(Symbol(varB), Symbol(varC))
        ),
        Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(true)), varC), Symbol(True))),
        Leaf(
          Decision(
            seq(Variable("a", Some(true)), Variable("b", Some(false)), varC),
            Symbol(varC)
          )
        )
      ),
      Leaf(
        Decision(
          seq(Variable("a", Some(false)), varB, varC),
          And(Symbol(varB), Or(Symbol(varB), Symbol(varC)))
        )
      )
    )

  val expectedSec: DecisionTree =
    Branch(
      Decision(
        seq(varA, varB, varC),
        And(
          Or(Symbol(varA), Symbol(varB)),
          Or(Symbol(varB), Symbol(varC))
        )
      ),
      Branch(
        Decision(
          seq(Variable("a", Some(true)), varB, varC),
          Or(Symbol(varB), Symbol(varC))
        ),
        Leaf(Decision(seq(Variable("a", Some(true)), Variable("b", Some(true)), varC), Symbol(True))),
        Branch(
          Decision(
            seq(Variable("a", Some(true)), Variable("b", Some(false)), varC),
            Symbol(varC)
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
          seq(Variable("a", Some(false)), varB, varC),
          And(Symbol(varB), Or(Symbol(varB), Symbol(varC)))
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
