package satify.update.solver.dpll

import satify.model.Bool.True
import satify.model.CNF.*
import satify.model.{CNF, Result, Solution, Variable}
import satify.model.Result.*
import satify.model.dpll.{Constraint, Decision, DecisionTree, PartialModel}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.update.solver.dpll.DpllDecision.decide
import satify.update.solver.dpll.DpllOneSol.{dpll, resume}
import satify.update.solver.dpll.cnf.CNFSat.{isSat, isUnsat}
import satify.update.solver.dpll.cnf.CNFSimplification.simplifyCnf
import satify.update.solver.dpll.utils.PartialModelUtils.{
  explodeSolutions,
  extractModelFromCnf,
  extractSolutionsFromDT,
  filterUnconstrVars,
  updateParModel
}

import scala.annotation.tailrec
import scala.util.Random

object DpllOneSol:

  val rnd: Random = Random(42)

  def dpll(cnf: CNF): (DecisionTree, Option[PartialModel]) =
    buildTree(Decision(extractModelFromCnf(cnf), cnf)) match
      case (dt, SAT) => (dt, extractSolution(dt, Set()))
      case (dt, UNSAT) => (dt, None)

  def dpll(dt: DecisionTree, prevSol: Set[PartialModel]): (DecisionTree, Option[PartialModel]) =
    extractSolution(dt, prevSol) match
      case None => resume(dt) match
        case (resDt, SAT) => (resDt, extractSolution(resDt, prevSol))
        case (resDt, UNSAT) => (resDt, None)
      case s @ Some(_) => (dt, s)

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

  private def extractSolution(dt: DecisionTree, prevSol: Set[PartialModel]): Option[PartialModel] =
    dt match
      case Leaf(Decision(pm, cnf)) =>
        cnf match
          case Symbol(True) =>
            explodeSolutions(
              pm.filter(v =>
                v match
                  case Variable(name, _)
                    if name.startsWith("X") || name.startsWith("ENC") ||
                      name.startsWith("TSTN") =>
                    false
                  case _ => true
              )
            ).find(fpm => !(prevSol contains fpm))
          case _ => None
      case Branch(_, left, right) =>
        extractSolution(left, prevSol) match
          case s @ Some(_) => s
          case _ => extractSolution(right, prevSol)
