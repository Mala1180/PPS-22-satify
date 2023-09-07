package satify.update.solver.dpll

import satify.model.Bool.{False, True}
import satify.model.CNF.{And, Not, Or, Symbol}
import satify.model.{CNF, Variable}
import satify.model.dpll.{Constraint, Decision, DecisionTree, PartialModel}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.update.solver.dpll.CNFSimplification.simplifyCnf
import satify.update.solver.dpll.ConflictIdentification.isUnsat
import satify.update.solver.dpll.Optimizations.unitPropagation
import satify.update.solver.dpll.PartialModelUtils.*

import java.util.concurrent.Executors
import scala.annotation.tailrec
import scala.util.Random

object Dpll:

  private val rnd = Random(64)

  def dpll(dec: Decision): DecisionTree =

    case class Frame(d: Decision, done: List[DecisionTree], todos: List[Decision])

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
        if isUnsat(d.cnf) || d.cnf == Symbol(True) then step(Frame(d, Nil, Nil) :: tail)
        else step(Frame(x, Nil, decide(x)) :: Frame(d, done, xs) :: tail)
      case Nil => throw new Error("Shouldn't happen")

    def decide(d: Decision): List[Decision] = d match
      case Decision(_, cnf) =>
        unitPropagation(cnf) match
          case Some(c) =>
            // println(s"unit prop choosing variable $c")
            unitPropDecision(d, c)
          case _ => randomDecision(d)
      /*case _ => pureLiteralElimination(d) match
            case Some(c) =>
              println(s"pure lit el variable $c")
              pureLitEliminationDecision(d, c)
            case _ => randomDecision(d)
       */

    step(List(Frame(dec, Nil, decide(dec))))

  def randomDecision(d: Decision): List[Decision] = d match
    case Decision(pm, cnf) =>
      val fv = filterUnconstrVars(pm)
      if fv.nonEmpty then
        val v = rnd.nextBoolean()
        fv(rnd.between(0, fv.size)) match
          case Variable(n, _) =>
            // println(s"randomly choosing $n with value $v")
            List(
              Decision(updateParModel(pm, Constraint(n, v)), simplifyCnf(cnf, Constraint(n, v))),
              Decision(updateParModel(pm, Constraint(n, !v)), simplifyCnf(cnf, Constraint(n, !v)))
            )
      else Nil

  def unitPropDecision(d: Decision, c: Constraint): List[Decision] = d match
    case Decision(pm, cnf) =>
      List(
        Decision(updateParModel(pm, c), simplifyCnf(cnf, c)),
        Decision(updateParModel(pm, Constraint(c.name, !c.value)), Symbol(False))
      )

  def pureLitEliminationDecision(d: Decision, c: Constraint): List[Decision] = d match
    case Decision(pm, cnf) =>
      List(
        Decision(updateParModel(pm, c), simplifyCnf(cnf, c)),
        Decision(updateParModel(pm, Constraint(c.name, !c.value)), simplifyCnf(cnf, Constraint(c.name, !c.value)))
      )
