package satify.update.solver.dpll

import satify.model.cnf.Bool.False
import satify.model.cnf.CNF.Symbol
import satify.model.dpll.PartialAssignment.{filterUnconstrVars, updatePartialAssignment}
import satify.model.dpll.{Constraint, Decision, OptionalVariable}
import satify.update.solver.dpll.Optimizations.{pureLiteralIdentification, unitLiteralIdentification}
import satify.update.solver.dpll.cnf.CNFSimplification.simplifyCnf

import scala.util.Random

private[dpll] object DpllDecision:

  /** Make decisions based on the previous one selecting the most appropriate variable and constraint.
    * If no optimization can be applied, it makes a random decision.
    * @param d previous decision.
    * @param rnd random number generator.
    * @return List of new Decisions.
    */
  def decide(d: Decision, rnd: Random): List[Decision] = d match
    case Decision(_, cnf) =>
      unitLiteralIdentification(cnf) match
        case Some(c) => unitPropagationDecision(d, c)
        case _ =>
          pureLiteralIdentification(d) match
            case Some(c) =>
              pureLiteralEliminationDecision(d, c)
            case _ => randomDecisions(d, rnd)

  /** Make random decisions based on the previous one given as parameter,
    * by choosing a random variable assigning a random value to it.
    * @param d previous decision.
    * @param rnd random number generator.
    * @return list of new decisions.
    */
  private def randomDecisions(d: Decision, rnd: Random): List[Decision] = d match
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

  /** Make unit propagation decision based on the previous one and the unit literal constraint.
    * @param d previous decision.
    * @param c constraint to the unit literal.
    * @return list of new decisions.
    */
  private def unitPropagationDecision(d: Decision, c: Constraint): List[Decision] =
    d match
      case Decision(pm, cnf) =>
        List(
          Decision(updatePartialAssignment(pm, c), simplifyCnf(cnf, c)),
          Decision(updatePartialAssignment(pm, Constraint(c.name, !c.value)), Symbol(False))
        )

  /** Make pure literals elimination decisions based on the previous one and the
    * pure literal constraint.
    * @param d previous decision.
    * @param c constraint to the pure literal.
    * @return list of new decisions.
    */
  private def pureLiteralEliminationDecision(d: Decision, c: Constraint): List[Decision] =
    d match
      case Decision(pm, cnf) =>
        List(
          Decision(updatePartialAssignment(pm, c), simplifyCnf(cnf, c)),
          Decision(
            updatePartialAssignment(pm, Constraint(c.name, !c.value)),
            simplifyCnf(cnf, Constraint(c.name, !c.value))
          )
        )
