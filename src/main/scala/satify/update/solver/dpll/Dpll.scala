package satify.update.solver.dpll

import satify.model.Bool.{False, True}
import satify.model.CNF.{And, Not, Or, Symbol}
import satify.model.{CNF, Variable}
import satify.model.dpll.{Constraint, Decision, DecisionTree, PartialModel}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.update.solver.dpll.cnf.CNFSimplification.simplifyCnf
import satify.update.solver.dpll.cnf.CNFSat.{isSat, isUnsat}
import satify.update.solver.dpll.Optimizations.{pureLiteralIdentification, unitLiteralIdentification}
import satify.update.solver.dpll.utils.PartialModelUtils.*

import java.util.concurrent.Executors
import scala.annotation.tailrec
import scala.util.Random

object Dpll:

  private val rnd = Random(64)

  /** Main DPLL algorithm.
    * @param dec first decision
    * @return decision tree of the run.
    */
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
      case Nil => throw new Error("Stack should never be empty")

    step(List(Frame(dec, Nil, decide(dec))))

  /** Make decisions based on the previous one selecting the most appropriate variable and assignment.
    * If no optimization can be applied, it makes a random decision.
    * @param d previous decision.
    * @return List of new Decisions
    */
  private def decide(d: Decision): List[Decision] = d match
    case Decision(_, cnf) =>
      unitLiteralIdentification(cnf) match
        case Some(c) => unitPropagationDecision(d, c)
        case _ =>
          pureLiteralIdentification(d) match
            case Some(c) => pureLiteralEliminationDecision(d, c)
            case _ => randomDecisions(d)

  /** Make random decisions based on the previous one given as parameter,
    * by choosing a random variable assigning a random value to it.
    * @param d previous decision
    * @return List of new decisions
    */
  private def randomDecisions(d: Decision): List[Decision] = d match
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

  /** Make unit propagation decision based on the previous decision and the constraint provided.
    * @param d previous decision.
    * @param c constraint.
    * @return List of new decisions
    */
  private def unitPropagationDecision(d: Decision, c: Constraint): List[Decision] =
    d match
      case Decision(pm, cnf) =>
        List(
          Decision(updateParModel(pm, c), simplifyCnf(cnf, c)),
          Decision(updateParModel(pm, Constraint(c.name, !c.value)), Symbol(False))
        )

  /** Make pure literals elimination decisions based on the previous decision and the
    * constraint provided.
    * @param d previous decision.
    * @param c constraint.
    * @return List of new decisions
    */
  private def pureLiteralEliminationDecision(d: Decision, c: Constraint): List[Decision] =
    d match
      case Decision(pm, cnf) =>
        List(
          Decision(updateParModel(pm, c), simplifyCnf(cnf, c)),
          Decision(updateParModel(pm, Constraint(c.name, !c.value)), simplifyCnf(cnf, Constraint(c.name, !c.value)))
        )
