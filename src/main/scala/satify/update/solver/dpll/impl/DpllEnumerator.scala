package satify.update.solver.dpll.impl

import satify.model.Result.*
import satify.model.Status.COMPLETED
import satify.model.cnf.CNF
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.{Constraint, Decision, DecisionTree, OptionalVariable}
import satify.model.{Assignment, Solution}
import satify.update.solver.dpll.DpllDecision.decide
import satify.update.solver.dpll.cnf.CNFSat.{isSat, isUnsat}
import satify.update.solver.dpll.cnf.CNFSimplification.simplifyCnf
import satify.update.solver.dpll.utils.PartialAssignmentUtils.*


import scala.annotation.tailrec
import scala.util.Random

private[solver] object DpllEnumerator:

  private val rnd = Random(42)

  /** Solves the SAT problem enumerating all satisfiable assignments,
    * by running the DPLL algorithm.
    * @param cnf expression in Conjunctive Normal Form.
    * @return a solution filled with the list of all satisfiable assignments if it's SAT,
    *         an empty list otherwise.
    */
  def enumerate(cnf: CNF): Solution =
    val assignments: List[Assignment] =
      (for partialAssignment <- extractParAssignments(dpll(Decision(extractParAssignmentFromCnf(cnf), cnf)))
      yield partialAssignment.toAssignments).flatten.distinct
    Solution(if assignments.nonEmpty then SAT else UNSAT, COMPLETED, assignments)

  /** Enumerator DPLL algorithm.
    * It returns a new decision tree whose leafs are either SAT or UNSAT solutions.
    * @param d decision to be made
    * @return updated decision tree along with the result
    */
  private def dpll(d: Decision): DecisionTree =

    case class Frame(d: Decision, done: List[DecisionTree], todos: List[Decision])

    @tailrec
    def step(stack: List[Frame]): DecisionTree = stack match
      case Frame(d, done, Nil) :: tail =>
        val ret =
          done match
            case ::(head, next) => Branch(d, next.head, head)
            case Nil => Leaf(d)
        tail match
          case Nil => ret
          case Frame(tn, td, tt) :: more =>
            step(Frame(tn, ret :: td, tt) :: more)
      case Frame(d, done, x :: xs) :: tail =>
        if isUnsat(d.cnf) || isSat(d.cnf) then step(Frame(d, Nil, Nil) :: tail)
        else step(Frame(x, Nil, decide(x, rnd)) :: Frame(d, done, xs) :: tail)
      case Nil => throw new Error("Stack should never be empty")

    step(List(Frame(d, Nil, decide(d, rnd))))
