package satify.update.solver.dpll

import satify.model.CNF.*
import satify.model.{CNF, Result, Variable}
import satify.model.Result.*
import satify.model.dpll.{Constraint, Decision, DecisionTree}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.update.solver.dpll.DpllDecision.decide
import satify.update.solver.dpll.DpllOneSol.{dpll, resume}
import satify.update.solver.dpll.cnf.CNFSat.{isSat, isUnsat}
import satify.update.solver.dpll.cnf.CNFSimplification.simplifyCnf
import satify.update.solver.dpll.utils.PartialModelUtils.{extractModelFromCnf, filterUnconstrVars, updateParModel}

import scala.annotation.tailrec
import scala.util.Random

object DpllOneSol:

  val rnd: Random = Random(42)

  def dpll(cnf: CNF): (DecisionTree, Result) = dpll(Decision(extractModelFromCnf(cnf), cnf))

  def dpll(d: Decision): (DecisionTree, Result) =
    if !isUnsat(d.cnf) && !isSat(d.cnf) then
      decide(d) match
        case ::(head, next) =>
          dpll(head) match
            case (dtl, SAT) => (Branch(d, dtl, Leaf(next.head)), SAT)
            case (dtl, UNSAT) =>
              dpll(next.head) match
                case (dtr, res) => (Branch(d, dtl, dtr), res)
        case Nil => (Leaf(d), if isSat(d.cnf) then SAT else UNSAT)
    else (Leaf(d), if isSat(d.cnf) then SAT else UNSAT)

  def resume(dt: DecisionTree): (DecisionTree, Result) = dt match
    case Leaf(d @ Decision(_, cnf)) =>
      val checkUnsat = isUnsat(cnf)
      val checkSat = isSat(cnf)
      if !checkSat && !checkUnsat then DpllOneSol.dpll(d)
      else (dt, if checkSat then SAT else UNSAT)

    case Branch(d, left, right) =>
      resume(left) match
        case (ldt, SAT) if ldt != left => (Branch(d, ldt, right), SAT)
        case (ldt, lres) =>
          resume(right) match
            case (rdt, rres) => (Branch(d, ldt, rdt), if lres == SAT then SAT else rres)
      
