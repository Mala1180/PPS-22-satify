package satify.update.solver.dpll

import satify.model.Result.*
import satify.model.cnf.Bool.True
import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.model.dpll.*
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.PartialAssignment.*
import satify.model.expression.SymbolGeneration.{encodingVarPrefix, tseitinVarPrefix}
import satify.model.{Assignment, Result, Solution}
import satify.update.solver.dpll.DpllDecision.decide
import satify.update.solver.dpll.DpllOneSol.{dpll, resume}
import satify.update.solver.dpll.cnf.CNFSat.{isSat, isUnsat}
import satify.update.solver.dpll.cnf.CNFSimplification.simplifyCnf

import scala.annotation.tailrec
import scala.util.Random

/** Save a run of DPLL algorithm.
  * @param dt decision tree
  * @param s solution
  */
case class DpllRun(dt: DecisionTree, s: Solution)

object DpllOneSol:

  val rnd: Random = Random(42)
  private var prevRun: Option[DpllRun] = None

  /** Runs the DPLL algorithm given a CNF in input.
    * @param cnf with the constraints to be satisfied.
    * @return a solution with a unique assignment, if it exists
    */
  def dpll(cnf: CNF): Solution =
    buildTree(Decision(extractParAssignmentFromCnf(cnf), cnf)) match
      case (dt, SAT) =>
        val solution: Solution = Solution(SAT, List(extractAssignment(dt, None)))
        prevRun = Some(DpllRun(dt, solution))
        solution
      case (_, UNSAT) => Solution(UNSAT, Nil)

  /** Runs the DPLL algorithm resuming a previous run, if it exists.
    * @return another assignment, different from the previous', if any.
    */
  def dpll(): Assignment =
    prevRun match
      case Some(DpllRun(dt, s)) =>
        extractAssignment(dt, prevRun) match
          case Assignment(Nil) =>
            resume(dt) match
              case (dt, SAT) =>
                val assignment: Assignment = extractAssignment(dt, prevRun)
                prevRun = Some(DpllRun(dt, Solution(SAT, assignment +: s.assignment)))
                assignment
              case (_, UNSAT) => Assignment(Nil)
          case assignment @ _ =>
            prevRun = Some(DpllRun(dt, Solution(SAT, assignment +: s.assignment)))
            assignment
      case None => throw new NoSuchElementException("No previous instance of DPLL")

  /** Resume the computation given an existing instance of decision tree.
    * @param dt decision tree returned on the previous run.
    * @return the updated decision tree along with the new result.
    */
  def resume(dt: DecisionTree): (DecisionTree, Result) = dt match
    case Leaf(d @ Decision(_, cnf)) =>
      val checkUnsat = isUnsat(cnf)
      val checkSat = isSat(cnf)
      if !checkSat && !checkUnsat then buildTree(d)
      else (dt, if checkSat then SAT else UNSAT)

    case Branch(d, left, right) =>
      resume(left) match
        case (ldt, SAT) if ldt != left => (Branch(d, ldt, right), SAT)
        case (ldt, lres) =>
          resume(right) match
            case (rdt, rres) => (Branch(d, ldt, rdt), if lres == SAT then SAT else rres)

  /** Build the decision tree given a decision in input.
    * It returns a new decision tree with a new SAT leaf, if any.
    * Otherwise, the updated decision tree with all UNSAT leafs.
    * @param d decision to be made
    * @return updated decision tree along with the result
    */
  private def buildTree(d: Decision): (DecisionTree, Result) =
    if !isUnsat(d.cnf) && !isSat(d.cnf) then
      decide(d) match
        case ::(head, next) =>
          buildTree(head) match
            case (dtl, SAT) => (Branch(d, dtl, Leaf(next.head)), SAT)
            case (dtl, UNSAT) =>
              buildTree(next.head) match
                case (dtr, res) => (Branch(d, dtl, dtr), res)
        case Nil => (Leaf(d), if isSat(d.cnf) then SAT else UNSAT)
    else (Leaf(d), if isSat(d.cnf) then SAT else UNSAT)

  /** Extract a new assignment from the decision tree s.t. it is not contained in the previous
    * DPLL run given in input.
    * @param dt decision tree where to extract the solutions
    * @param prevRun previous DPLL run with the current extracted solutions.
    * @return a filled assignment if it exists, or an empty one.
    */
  private def extractAssignment(dt: DecisionTree, prevRun: Option[DpllRun]): Assignment =

    /** Filter generated variables (from encodings/Tseitin) and do the cartesian product
      * of all the possible assignments
      * @param pa partial assignment
      * @return assignments
      */
    def filterAndExplore(pa: PartialAssignment): List[Assignment] = pa match
      case PartialAssignment(optVariables) =>
        PartialAssignment(
          optVariables.filter(v =>
            v match
              case OptionalVariable(name, _) if name.startsWith(encodingVarPrefix) || name.startsWith(tseitinVarPrefix) => false
              case _ => true
          )
        ).toAssignments

    /** Return a filled assignment if it exists an assignment inside [[assignments]]
      * which is not containted in [[prevRun]], an empty one otherwise.
      * @param assignments to filter
      * @param prevRun filters assignments
      * @return an assignment
      */
    def nextAssignment(assignments: List[Assignment], prevRun: Option[DpllRun]): Assignment =
      prevRun match
        case Some(pr) =>
          val newAssignments = assignments.filter(a => !(pr.s.assignment contains a))
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
