package satify.update.solver.dpll

import satify.model.cnf.Bool.False
import satify.model.dpll.OptionalVariable
import satify.model.cnf.CNF.Symbol
import satify.model.dpll.{Constraint, Decision}
import satify.update.solver.dpll.Optimizations.{pureLiteralIdentification, unitLiteralIdentification}
import satify.update.solver.dpll.cnf.CNFSimplification.simplifyCnf
import satify.model.dpll.PartialAssignment.{filterUnconstrVars, updatePartialAssignment}

import scala.util.Random

object DpllDecision:

  private val rnd = Random(42)

  /** Make decisions based on the previous one selecting the most appropriate variable and assignment.
    * If no optimization can be applied, it makes a random decision.
    *
    * @param d previous decision.
    * @return List of new Decisions
    */
  def decide(d: Decision): List[Decision] = d match
    case Decision(_, cnf) =>
      unitLiteralIdentification(cnf) match
        case Some(c) => unitPropagationDecision(d, c)
        case _ =>
          pureLiteralIdentification(d) match
            case Some(c) =>
              pureLiteralEliminationDecision(d, c)
            case _ => randomDecisions(d)

  /** Make random decisions based on the previous one given as parameter,
    * by choosing a random variable assigning a random value to it.
    *
    * @param d previous decision
    * @return List of new decisions
    */
  def randomDecisions(d: Decision): List[Decision] = d match
    case Decision(pm, cnf) =>
      val fv = filterUnconstrVars(pm)
      if fv.nonEmpty then
        val v = rnd.nextBoolean()
        fv(rnd.between(0, fv.size)) match
          case OptionalVariable(n, _) =>
            List(
              Decision(updatePartialAssignment(pm, Constraint(n, v)), simplifyCnf(cnf, Constraint(n, v))),
              Decision(updatePartialAssignment(pm, Constraint(n, !v)), simplifyCnf(cnf, Constraint(n, !v)))
            )
      else Nil

  /** Make unit propagation decision based on the previous decision and the constraint provided.
    *
    * @param d previous decision.
    * @param c constraint.
    * @return List of new decisions
    */
  def unitPropagationDecision(d: Decision, c: Constraint): List[Decision] =
    d match
      case Decision(pm, cnf) =>
        List(
          Decision(updatePartialAssignment(pm, c), simplifyCnf(cnf, c)),
          Decision(updatePartialAssignment(pm, Constraint(c.name, !c.value)), Symbol(False))
        )

  /** Make pure literals elimination decisions based on the previous decision and the
    * constraint provided.
    *
    * @param d previous decision.
    * @param c constraint.
    * @return List of new decisions
    */
  def pureLiteralEliminationDecision(d: Decision, c: Constraint): List[Decision] =
    d match
      case Decision(pm, cnf) =>
        List(
          Decision(updatePartialAssignment(pm, c), simplifyCnf(cnf, c)),
          Decision(
            updatePartialAssignment(pm, Constraint(c.name, !c.value)),
            simplifyCnf(cnf, Constraint(c.name, !c.value))
          )
        )
