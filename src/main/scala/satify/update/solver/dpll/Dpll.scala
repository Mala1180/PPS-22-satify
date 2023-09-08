package satify.update.solver.dpll

import satify.model.Bool.{False, True}
import satify.model.CNF.{And, Not, Or, Symbol}
import satify.model.{CNF, Variable}
import satify.model.dpll.{Constraint, Decision, DecisionTree, PartialModel}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.update.solver.dpll.CNFSimplification.simplifyCnf
import satify.update.solver.dpll.ConflictIdentification.{isSat, isUnsat}
import satify.update.solver.dpll.Optimizations.{pureLit, uProp}
import satify.update.solver.dpll.PartialModelUtils.*

import java.util.concurrent.Executors
import scala.annotation.tailrec
import scala.util.Random

object Dpll:

  private val rnd = Random(64)

  def dpll(dec: Decision): DecisionTree =

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
        else step(Frame(x, Nil, decide(x)) :: Frame(d, done, xs) :: tail)
      case Nil => throw new Error("Shouldn't happen")

    step(List(Frame(dec, Nil, decide(dec))))

  private def decide(d: Decision): List[Decision] = d match
    case Decision(_, cnf) =>
      uProp(cnf) match
        case Some(c) => uPropDec(d, c)
        case _ =>
          pureLit(d) match
            case Some(c) => pureLitDec(d, c)
            case _ => rndDec(d)

  private def rndDec(d: Decision): List[Decision] = d match
    case Decision(pm, cnf) =>
      val fv = filterUnconstrVars(pm)
      if fv.nonEmpty then
        val v = rnd.nextBoolean()
        fv(rnd.between(0, fv.size)) match
          case Variable(n, _) =>
            List(
              Decision(updateParModel(pm, Constraint(n, v)), simplifyCnf(cnf, Constraint(n, v))),
              Decision(updateParModel(pm, Constraint(n, !v)), simplifyCnf(cnf, Constraint(n, !v)))
            )
      else Nil

  private def uPropDec(d: Decision, c: Constraint): List[Decision] =
    d match
      case Decision(pm, cnf) =>
        List(
          Decision(updateParModel(pm, c), simplifyCnf(cnf, c)),
          Decision(updateParModel(pm, Constraint(c.name, !c.value)), Symbol(False))
        )

  private def pureLitDec(d: Decision, c: Constraint): List[Decision] =
    d match
      case Decision(pm, cnf) =>
        List(
          Decision(updateParModel(pm, c), simplifyCnf(cnf, c)),
          Decision(updateParModel(pm, Constraint(c.name, !c.value)), simplifyCnf(cnf, Constraint(c.name, !c.value)))
        )
