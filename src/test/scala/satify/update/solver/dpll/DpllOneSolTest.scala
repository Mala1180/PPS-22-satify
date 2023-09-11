package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Bool.{False, True}
import satify.model.CNF.{And, Not, Or, Symbol}
import satify.model.Result.*
import satify.model.dpll.Decision
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.OrderedSeq.seq
import satify.model.{CNF, Variable}
import satify.update.solver.dpll.DpllOneSol.{dpll, resume}
import satify.update.solver.dpll.utils.PartialModelUtils.extractModelFromCnf

class DpllOneSolTest extends AnyFlatSpec with Matchers:

  import satify.model.dpll.OrderedSeq.given_Ordering_Variable

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnf: CNF = And(Or(Symbol(varA), Symbol(varB)), Or(Symbol(varB), Symbol(varC)))

  "DPLL" should "extract one solution at a time" in {
    val firstDt = dpll(cnf)
    println(firstDt)
    firstDt shouldBe
      (Branch(
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
      ), SAT)

    val secDt = resume(firstDt match
      case (dt, _) => dt
    )

    secDt shouldBe (Branch(
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
    ), SAT)

  }
