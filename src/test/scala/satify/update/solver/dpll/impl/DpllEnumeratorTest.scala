package satify.update.solver.dpll.impl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.model.dpll.DecisionTree.*
import satify.model.dpll.OrderedList.*
import satify.model.dpll.PartialAssignment.*
import satify.model.dpll.{Decision, DecisionTree, OptionalVariable, PartialAssignment}
import satify.update.solver.dpll.impl.DpllEnumerator.*

import scala.annotation.tailrec

class DpllEnumeratorTest extends AnyFlatSpec with Matchers:

  import satify.model.dpll.OrderedList.given

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val cnf: CNF = And(sA, sB)

  private def decisionFromCnf(cnf: CNF): Decision = Decision(extractParAssignmentFromCnf(cnf), cnf)

  @tailrec
  private def findLeftDecision(dt: DecisionTree, h: Int = 1): Option[Decision] = dt match
    case Leaf(d) if h == 0 => Some(d)
    case Branch(d, _, _) if h == 0 => Some(d)
    case Branch(_, left, _) => findLeftDecision(left, h - 1)
    case _ => None

  "Enumerator" should "return SAT" in {
    enumerate(cnf).assignments.size should be > 0
    enumerate(And(sA, Or(sB, sC))).assignments.size should be > 0
  }

  "Enumerator" should "return UNSAT" in {
    enumerate(And(sA, Not(sA))).assignments should have size 0
    enumerate(And(sA, And(Or(sB, sC), Not(sA)))).assignments should have size 0
    enumerate(
      And(
        Or(sA, sB),
        And(
          Or(Not(sA), sB),
          And(Or(sA, Not(sB)), Or(Not(sA), Not(sB)))
        )
      )
    ).assignments should have size 0
  }

  "Enumerator" should "do unit propagation when there's a unit literal in positive form" in {
    val cnf: CNF = And(sA, And(sB, Or(sB, sC)))
    dpll(Decision(extractParAssignmentFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(PartialAssignment(list(OptionalVariable("a"), OptionalVariable("b"), OptionalVariable("c"))), cnf),
        Branch(
          Decision(
            PartialAssignment(list(OptionalVariable("a", Some(true)), OptionalVariable("b"), OptionalVariable("c"))),
            And(sB, Or(sB, sC))
          ),
          Leaf(
            Decision(
              PartialAssignment(
                list(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)), OptionalVariable("c"))
              ),
              Symbol(True)
            )
          ),
          Leaf(
            Decision(
              PartialAssignment(
                list(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(false)), OptionalVariable("c"))
              ),
              Symbol(False)
            )
          )
        ),
        Leaf(
          Decision(
            PartialAssignment(list(OptionalVariable("a", Some(false)), OptionalVariable("b"), OptionalVariable("c"))),
            Symbol(False)
          )
        )
      )
  }

  "Enumerator" should "do unit propagation when there's a unit literal in negative form" in {
    val cnf: CNF = Or(sA, Or(sA, sB))
    findLeftDecision(dpll(decisionFromCnf(cnf))) should matchPattern {
      case Some(
            Decision(
              PartialAssignment(
                OptionalVariable("a", Some(false)) :: OptionalVariable("b", None) :: Nil
              ),
              _
            )
          ) =>
    }
  }

  "Enumerator" should "do pure literals elimination when there's a pure literal in positive form" in {
    val cnf: CNF =
      And(
        Or(Or(sA, Not(sB)), sB),
        And(Or(sB, sC), Or(sA, Not(sB)))
      )
    dpll(Decision(extractParAssignmentFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(PartialAssignment(list(OptionalVariable("a"), OptionalVariable("b"), OptionalVariable("c"))), cnf),
        Branch(
          Decision(
            PartialAssignment(list(OptionalVariable("a", Some(true)), OptionalVariable("b"), OptionalVariable("c"))),
            Or(sB, sC)
          ),
          Leaf(
            Decision(
              PartialAssignment(
                list(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)), OptionalVariable("c"))
              ),
              Symbol(True)
            )
          ),
          Branch(
            Decision(
              PartialAssignment(
                list(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(false)), OptionalVariable("c"))
              ),
              sC
            ),
            Leaf(
              Decision(
                PartialAssignment(
                  list(
                    OptionalVariable("a", Some(true)),
                    OptionalVariable("b", Some(false)),
                    OptionalVariable("c", Some(true))
                  )
                ),
                Symbol(True)
              )
            ),
            Leaf(
              Decision(
                PartialAssignment(
                  list(
                    OptionalVariable("a", Some(true)),
                    OptionalVariable("b", Some(false)),
                    OptionalVariable("c", Some(false))
                  )
                ),
                Symbol(False)
              )
            )
          )
        ),
        Branch(
          Decision(
            PartialAssignment(list(OptionalVariable("a", Some(false)), OptionalVariable("b"), OptionalVariable("c"))),
            And(Or(Not(sB), sB), And(Or(sB, sC), Not(sB)))
          ),
          Branch(
            Decision(
              PartialAssignment(
                list(OptionalVariable("a", Some(false)), OptionalVariable("b", Some(false)), OptionalVariable("c"))
              ),
              sC
            ),
            Leaf(
              Decision(
                PartialAssignment(
                  list(
                    OptionalVariable("a", Some(false)),
                    OptionalVariable("b", Some(false)),
                    OptionalVariable("c", Some(true))
                  )
                ),
                Symbol(True)
              )
            ),
            Leaf(
              Decision(
                PartialAssignment(
                  list(
                    OptionalVariable("a", Some(false)),
                    OptionalVariable("b", Some(false)),
                    OptionalVariable("c", Some(false))
                  )
                ),
                Symbol(False)
              )
            )
          ),
          Leaf(
            Decision(
              PartialAssignment(
                list(OptionalVariable("a", Some(false)), OptionalVariable("b", Some(true)), OptionalVariable("c"))
              ),
              Symbol(False)
            )
          )
        )
      )
  }
