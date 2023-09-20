package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.model.dpll.{Decision, PartialAssignment, OptionalVariable}
import satify.model.dpll.PartialAssignment.extractParAssignmentFromCnf
import satify.update.solver.dpll.DpllDecision.decide

import scala.util.Random

class DpllDecisionTest extends AnyFlatSpec with Matchers:

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val rnd: Random = Random(42)

  private def decisionFromCnf(cnf: CNF): Decision = Decision(extractParAssignmentFromCnf(cnf), cnf)

  "A new decision" should "make an assignment to the first positive unit literal" in {
    val cnf: CNF = And(sA, And(sB, Or(sB, sC)))
    decide(decisionFromCnf(cnf), rnd) should matchPattern {
      case Decision(
            PartialAssignment(
              OptionalVariable("a", Some(true)) ::
              OptionalVariable("b", None) ::
              OptionalVariable("c", None) :: Nil
            ),
            _
          ) :: _ =>
    }
  }

  "A new decision" should "make an assignment to the first negative unit literal" in {
    val cnf: CNF = Or(Not(sA), Or(Not(sA), sB))
    decide(decisionFromCnf(cnf), rnd) should matchPattern {
      case Decision(
            PartialAssignment(
              OptionalVariable("a", Some(false)) ::
              OptionalVariable("b", None) :: Nil
            ),
            _
          ) :: _ =>
    }
  }

  "A new decision" should "make an assignment to the first positive pure literal" in {
    val cnf: CNF =
      And(
        Or(Or(sA, Not(sB)), sB),
        And(Or(sB, sC), Or(sA, Not(sB)))
      )
    decide(decisionFromCnf(cnf), rnd) should matchPattern {
      case Decision(
            PartialAssignment(
              OptionalVariable("a", Some(true)) ::
              OptionalVariable("b", None) ::
              OptionalVariable("c", None) :: Nil
            ),
            _
          ) :: _ =>
    }
  }

  "A new decision" should "make an assignment to the first negative pure literal" in {
    val cnf: CNF =
      And(
        Or(Or(Not(sA), Not(sB)), sB),
        And(Or(sB, sC), Or(Not(sA), sC))
      )
    decide(decisionFromCnf(cnf), rnd) should matchPattern {
      case Decision(
            PartialAssignment(
              OptionalVariable("a", Some(false)) ::
              OptionalVariable("b", None) ::
              OptionalVariable("c", None) :: Nil
            ),
            _
          ) :: _ =>
    }
  }

  "A new decision" should "make a random assignment" in {
    val cnf: CNF = And(Or(sA, Not(sB)), Or(Not(sA), sB))
    decide(decisionFromCnf(cnf), Random(42)) should not be decide(decisionFromCnf(cnf), Random(67))
  }
