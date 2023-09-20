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

class DpllEnumeratorTest extends AnyFlatSpec with Matchers:

  import satify.model.dpll.OrderedList.given

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val cnf: CNF = And(sA, sB)

  "Enumerator" should "return SAT" in {
    dpll(cnf).assignment.size should be > 0
    dpll(And(sA, Or(sB, sC))).assignment.size should be > 0
  }

  "Enumerator" should "return UNSAT" in {
    dpll(And(sA, Not(sA))).assignment should have size 0
    dpll(And(sA, And(Or(sB, sC), Not(sA)))).assignment should have size 0
    dpll(
      And(
        Or(sA, sB),
        And(
          Or(Not(sA), sB),
          And(Or(sA, Not(sB)), Or(Not(sA), Not(sB)))
        )
      )
    ).assignment should have size 0
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
    val cnf: CNF = And(Not(sA), Or(sA, sB))
    dpll(Decision(extractParAssignmentFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(PartialAssignment(list(OptionalVariable("a"), OptionalVariable("b"))), cnf),
        Branch(
          Decision(PartialAssignment(list(OptionalVariable("a", Some(false)), OptionalVariable("b"))), sB),
          Leaf(
            Decision(
              PartialAssignment(list(OptionalVariable("a", Some(false)), OptionalVariable("b", Some(true)))),
              Symbol(True)
            )
          ),
          Leaf(
            Decision(
              PartialAssignment(list(OptionalVariable("a", Some(false)), OptionalVariable("b", Some(false)))),
              Symbol(False)
            )
          )
        ),
        Leaf(Decision(PartialAssignment(list(OptionalVariable("a", Some(true)), OptionalVariable("b"))), Symbol(False)))
      )
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
