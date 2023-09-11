package satify.update.solver.dpll

import satify.model.{Assignment, Result, Solution}
import satify.model.cnf.Bool.True
import satify.model.cnf.CNF.*
import satify.model.cnf.CNF
import satify.model.Result.*
import satify.model.dpll.{Constraint, Decision, DecisionTree, PartialModel, Variable}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.update.solver.dpll.DpllDecision.decide
import satify.update.solver.dpll.DpllOneSol.{dpll, resume}
import satify.update.solver.dpll.cnf.CNFSat.{isSat, isUnsat}
import satify.update.solver.dpll.cnf.CNFSimplification.simplifyCnf
import satify.update.solver.dpll.utils.PartialModelUtils.*

import scala.annotation.tailrec
import scala.util.Random

case class DpllRun(dt: DecisionTree, s: Solution)

object DpllOneSol:

  val rnd: Random = Random(42)
  private var prevRun: Option[DpllRun] = None

  def dpll(cnf: CNF): Solution =
    buildTree(Decision(extractModelFromCnf(cnf), cnf)) match
      case (dt, SAT) =>
        println(dt)
        val solution: Solution = Solution(SAT, List(extractSolution(dt, prevRun)))
        prevRun = Some(DpllRun(dt, solution))
        solution
      case (_, UNSAT) => Solution(UNSAT, Nil)

  def dpll(): Assignment =
    prevRun match
      case Some(DpllRun(dt, s)) =>
        extractSolution(dt, prevRun) match
          case Assignment(Nil) =>
            resume(dt) match
              case (dt, SAT) =>
                val assignment: Assignment = extractSolution(dt, prevRun)
                prevRun = Some(DpllRun(dt, Solution(SAT, assignment +: s.assignment)))
                assignment
              case (_, UNSAT) => Assignment(Nil)
          case assignment @ _ =>
            prevRun = Some(DpllRun(dt, Solution(SAT, assignment +: s.assignment)))
            assignment
      case None => throw new NoSuchElementException("No previous instance of DPLL")

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

  private def extractSolution(dt: DecisionTree, prevRun: Option[DpllRun]): Assignment =
    dt match
      case Leaf(Decision(pm, cnf)) =>
        cnf match
          case Symbol(True) =>
            val allSolutions = explodeSolutions(
              pm.filter(v =>
                v match
                  case Variable(name, _) if name.startsWith("ENC") || name.startsWith("TSTN") => false
                  case _ => true
              )
            ).map(parModel => Assignment(parModel)).toList
            prevRun match
              case Some(pr) =>
                val filteredList = allSolutions.filter(a => !(pr.s.assignment contains a))
                if filteredList.nonEmpty then filteredList.head else Assignment(Nil)
              case None if allSolutions.nonEmpty => allSolutions.head
              case _ => Assignment(Nil)
          case _ => Assignment(Nil)
      case Branch(_, left, right) =>
        extractSolution(left, prevRun) match
          case s @ Assignment(l) if l.nonEmpty => s
          case _ => extractSolution(right, prevRun)
