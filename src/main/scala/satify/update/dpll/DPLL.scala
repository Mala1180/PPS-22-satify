package satify.update.dpll

import satify.model.DecisionTree.*
import satify.model.{CNF, Constraint, DecisionTree, PartialModel, TreeState, Variable}
import satify.model.CNF.*
import satify.update.dpll.CNFSimplification.*
import satify.update.dpll.PartialModelUtils.*
import satify.update.dpll.ConflictIdentification.isUnsat
import satify.model
import satify.model.Constant.True

import scala.language.postfixOps

object DPLL:

  /** Exhaustive search of all possible decisions to the partial model.
    * The three branches left assigning true to the first unconstrained variable, false to the right one, until
    * no more assignments are possible.
    * @param dec decision to make
    * @return decision tree from the current decision
    */
  def dpll(dec: TreeState): DecisionTree =

    lazy val recCall: (PartialModel, CNF, Constraint) => DecisionTree =
      (parModel, cnf, constr) =>
        dpll(
          TreeState(updateParModel(parModel, constr), simplifyCnf(cnf, constr))
        )

    dec match
      case TreeState(parModel, cnf) =>
        if (isUnsat(cnf))
          Branch(dec, UNSAT, UNSAT)
        else
          filterUnconstrVars(extractModelFromCnf(cnf)) match
            case Variable(name, _) +: _ =>
              Branch(
                dec,
                recCall(parModel, cnf, Constraint(name, true)),
                recCall(parModel, cnf, Constraint(name, false))
              )
            case _ => Branch(dec, SAT, SAT)
