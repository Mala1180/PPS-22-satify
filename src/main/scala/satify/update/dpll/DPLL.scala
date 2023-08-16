package satify.update.dpll

import satify.model.DecisionTree.*
import satify.model.{CNF, Constraint, Decision, DecisionTree, PartialModel, Variable}
import satify.model.CNF.*
import satify.update.dpll.CNFSimplification.*
import satify.update.dpll.PartialModelUtils.*
import satify.update.dpll.ConflictIdentification.isUnsat
import satify.model
import satify.model.Bool.{False, True}

import scala.language.postfixOps
import scala.util.Random

object DPLL:

  private val rnd = Random(42)

  /** DPLL (Davis-Putnam-Logemann-Loveland) algorithm.
    * @param dec Decision to apply
    * @return built DecisionTree from dec
    */
  def dpll(dec: Decision): DecisionTree =

    /** Recursively call dpll applying the given constraint.
      * @param dec the previous Decision
      * @param constr Constraint to apply
      * @return updated DecisionTree with the new Decision
      */
    def decide(dec: Decision, constr: Constraint): DecisionTree = dec match
      case Decision(parModel, cnf) => dpll(Decision(updateParModel(parModel, constr), simplifyCnf(cnf, constr)))

    /** Branch on a unit literal.
      * @param dec the previous Decision
      * @return an Option containing a Branch if there's a unit literal, None otherwise.
      */
    def unitPropDecision(dec: Decision): Option[Branch] = dec match
      case Decision(parModel, cnf) =>
        unitPropagation(cnf) match
          case Some(c @ Constraint(name, value)) =>
            Some(
              Branch(
                dec,
                decide(dec, c),
                Leaf(Decision(updateParModel(parModel, Constraint(name, !value)), Symbol(False)))
              )
            )
          case None => None

    /** Branch the decision tree by choosing a random variable among the available ones and by
      * applying a random constraint.
      * @param dec the previous Decision
      * @return updated DecisionTree with the random Decision.
      */
    def randomDecision(dec: Decision): DecisionTree = dec match
      case Decision(parModel, cnf) =>
        val unVars = filterUnconstrVars(extractModelFromCnf(cnf))
        if unVars.nonEmpty then
          val v = rnd.nextBoolean()
          unVars(rnd.between(0, unVars.size)) match
            case Variable(name, _) =>
              Branch(
                dec,
                decide(dec, Constraint(name, v)),
                decide(dec, Constraint(name, !v))
              )
        else Leaf(dec)

    dec match
      case Decision(parModel, cnf) =>
        if isUnsat(cnf) then Leaf(dec)
        else
          unitPropDecision(dec) match
            case Some(branch) => branch
            case None => randomDecision(dec)

  /** Apply unit propagation.
    * @param cnf where to search for a unit literal
    * @return an Option containing a Constraint if a unit literal is present, None otherwise.
    */
  private def unitPropagation(cnf: CNF): Option[Constraint] =

    val f: (CNF, Option[Constraint]) => Option[Constraint] = (cnf, d) =>
      cnf match
        case Symbol(Variable(name, _)) => Some(Constraint(name, true))
        case Not(Symbol(Variable(name, _))) => Some(Constraint(name, false))
        case _ => d

    f(
      cnf,
      cnf match
        case And(left, right) => f(left, f(right, unitPropagation(right)))
        case Or(left, right) => None
        case _ => None
    )
