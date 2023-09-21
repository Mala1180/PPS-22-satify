package satify.update.solver.dpll.impl

import satify.model.Result.*
import satify.model.Status.*
import satify.model.cnf.Bool.True
import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.model.dpll.*
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.PartialAssignment
import satify.model.expression.SymbolGeneration.{encodingVarPrefix, converterVarPrefix}
import satify.model.{Assignment, Result, Solution}
import satify.update.solver.dpll.DpllDecision.decide
import satify.update.solver.dpll.cnf.CNFSat.{isSat, isUnsat}
import satify.update.solver.dpll.cnf.CNFSimplification.simplifyCnf
import satify.update.solver.dpll.impl.DpllFinder.{findNext, resume}
import satify.update.solver.dpll.utils.PartialAssignmentUtils.*


import scala.util.Random

private[solver] object DpllFinder:

  /** Util product type to save a run of DPLL.
    * @param dt decision tree.
    * @param s solution.
    */
  private case class DpllRun(dt: DecisionTree, s: Solution)

  private var prevRun: Option[DpllRun] = None
  private val rnd = Random(42)

  /** Solves the SAT problem finding a solution with a unique assignment,
    * by running the DPLL algorithm.
    * @param cnf expression in Conjunctive Normal Form.
    * @return a solution with a unique assignment, filled with a list of variables if it's SAT,
    *         an empty list otherwise.
    */
  def find(cnf: CNF): Solution =
    dpll(Decision(extractParAssignmentFromCnf(cnf), cnf)) match
      case (dt, SAT) =>
        val solution: Solution = Solution(SAT, PARTIAL, List(extractAssignment(dt, None)))
        prevRun = Some(DpllRun(dt, solution))
        solution
      case (_, UNSAT) => Solution(UNSAT, COMPLETED, Nil)

  /** Runs the DPLL algorithm resuming a previous run, if present.
    * @return an assignment, different from the previous ones, filled with a list
    *         of variables if there's another satisfiable, an empty list otherwise.
    */
  def findNext(): Assignment =
    prevRun match
      case Some(DpllRun(dt, s)) =>
        extractAssignment(dt, prevRun) match
          case Assignment(Nil) =>
            resume(dt) match
              case (dt, SAT) =>
                val assignment: Assignment = extractAssignment(dt, prevRun)
                prevRun = Some(DpllRun(dt, Solution(SAT, PARTIAL, assignment +: s.assignments)))
                assignment
              case (_, UNSAT) => Assignment(Nil)
          case assignment @ _ =>
            prevRun = Some(DpllRun(dt, Solution(SAT, PARTIAL, assignment +: s.assignments)))
            assignment
      case None => throw new NoSuchElementException("No previous instance of DPLL")

  /** Finder DPLL algorithm.
    * It returns a new decision tree with a new SAT leaf, if any.
    * Otherwise, the updated decision tree with all UNSAT leafs.
    * @param d decision to be made
    * @return updated decision tree along with the result
    */
  private def dpll(d: Decision): (DecisionTree, Result) =
    if !isUnsat(d.cnf) && !isSat(d.cnf) then
      decide(d, rnd) match
        case ::(head, next) =>
          dpll(head) match
            case (dtl, SAT) => (Branch(d, dtl, Leaf(next.head)), SAT)
            case (dtl, UNSAT) =>
              dpll(next.head) match
                case (dtr, res) => (Branch(d, dtl, dtr), res)
        case Nil => (Leaf(d), if isSat(d.cnf) then SAT else UNSAT)
    else (Leaf(d), if isSat(d.cnf) then SAT else UNSAT)

  /** Resume the computation of DPLL given an existing instance of decision tree.
    * @param dt decision tree returned on the previous run.
    * @return the updated decision tree along with the new result.
    */
  private def resume(dt: DecisionTree): (DecisionTree, Result) = dt match
    case Leaf(d @ Decision(_, cnf)) =>
      val checkUnsat = isUnsat(cnf)
      val checkSat = isSat(cnf)
      if !checkSat && !checkUnsat then dpll(d)
      else (dt, if checkSat then SAT else UNSAT)

    case Branch(d, left, right) =>
      resume(left) match
        case (ldt, SAT) if ldt != left => (Branch(d, ldt, right), SAT)
        case (ldt, lres) =>
          resume(right) match
            case (rdt, rres) => (Branch(d, ldt, rdt), if lres == SAT then SAT else rres)

  /** Extract a new assignment from the decision tree s.t. it is not contained in the previous
    * DPLL run given in input.
    * @param dt decision tree where to extract the assignment
    * @param prevRun previous DPLL run with the current extracted solution.
    * @return a filled assignment if it exists, or an empty one.
    */
  private def extractAssignment(dt: DecisionTree, prevRun: Option[DpllRun]): Assignment =

    /** Filter generated variables (from encodings / converter) and do the cartesian product
      * of all the possible assignments
      * @param pa partial assignment
      * @return assignments
      */
    def filterAndExplore(pa: PartialAssignment): List[Assignment] = pa match
      case PartialAssignment(optVariables) =>
        PartialAssignment(
          optVariables.filter(v =>
            v match
              case OptionalVariable(name, _)
                  if name.startsWith(encodingVarPrefix) || name.startsWith(converterVarPrefix) =>
                false
              case _ => true
          )
        ).toAssignments

    /** Returns the next assignment.
      * @param assignments to filter
      * @param prevRun filters assignments.
      * @return a filled assignment from the list of [[assignments]] given in input s.t. it is
      *          not containted in [[prevRun]], an empty one otherwise.
      */
    def nextAssignment(assignments: List[Assignment], prevRun: Option[DpllRun]): Assignment =
      prevRun match
        case Some(pr) =>
          val newAssignments = assignments.filter(a => !(pr.s.assignments contains a))
          if newAssignments.nonEmpty then newAssignments.head else Assignment(Nil)
        case None if assignments.nonEmpty => assignments.head
        case _ => Assignment(Nil)

    dt match
      case Leaf(Decision(s @ PartialAssignment(_), cnf)) =>
        cnf match
          case Symbol(True) => nextAssignment(filterAndExplore(s), prevRun)
          case _ => Assignment(Nil)
      case Branch(_, left, right) =>
        extractAssignment(left, prevRun) match
          case s @ Assignment(l) if l.nonEmpty => s
          case _ => extractAssignment(right, prevRun)
