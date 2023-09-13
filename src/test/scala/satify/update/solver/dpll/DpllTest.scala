package satify.update.solver.dpll

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF.*
import satify.model.cnf.CNF
import satify.model.dpll.DecisionTree.*
import satify.model.dpll.OrderedList.*
import satify.model.dpll.{Decision, DecisionTree, OptionalVariable, PartialAssignment}
import satify.update.solver.dpll.Dpll.*
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions
import satify.model.dpll.PartialAssignment.*

class DpllTest extends AnyFlatSpec with Matchers:

  import satify.model.dpll.OrderedList.given_Ordering_OptionalVariable

  val sA: Symbol = Symbol("a")
  val sB: Symbol = Symbol("b")
  val sC: Symbol = Symbol("c")

  val cnf: CNF = And(sA, sB)

  "DPLL" should "be SAT" in {
    extractSolutions(cnf).size should be > 0
    extractSolutions(And(sA, Or(sB, sC))).size should be > 0
  }

  "DPLL" should "be UNSAT" in {
    extractSolutions(And(sA, Not(sA))) shouldBe Nil
    extractSolutions(And(sA, And(Or(sB, sC), Not(sA)))) shouldBe Nil
    extractSolutions(
      And(
        Or(sA, sB),
        And(
          Or(Not(sA), sB),
          And(Or(sA, Not(sB)), Or(Not(sA), Not(sB)))
        )
      )
    ) shouldBe Nil
  }

  "DPLL" should "do unit propagation when there's only a OptionalVariable inside a clause and it is in positive form" in {
    val cnf: CNF = And(sA, And(sB, Or(sB, sC)))
    dpll(cnf) shouldBe
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

  "DPLL" should "do unit propagation when there's only a OptionalVariable inside a clause and it is in negative form" in {
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

  "DPLL" should "do pure literals elimination when the Literal appears only in negative form" in {
    val cnf: CNF = And(Or(Not(sA), Not(sB)), Or(Not(sA), sB))
    dpll(Decision(extractParAssignmentFromCnf(cnf), cnf)) shouldBe
      Branch(
        Decision(PartialAssignment(list(OptionalVariable("a"), OptionalVariable("b"))), cnf),
        Leaf(
          Decision(PartialAssignment(list(OptionalVariable("a", Some(false)), OptionalVariable("b"))), Symbol(True))
        ),
        Branch(
          Decision(PartialAssignment(list(OptionalVariable("a", Some(true)), OptionalVariable("b"))), And(Not(sB), sB)),
          Leaf(
            Decision(
              PartialAssignment(list(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(false)))),
              Symbol(False)
            )
          ),
          Leaf(
            Decision(
              PartialAssignment(list(OptionalVariable("a", Some(true)), OptionalVariable("b", Some(true)))),
              Symbol(False)
            )
          )
        )
      )
  }

  "DPLL" should "do pure literals elimination when the Literal appears only in positive form" in {
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
