package satify.update.dpll

import satify.model.DecisionTree.*
import satify.model.{CNF, Constraint, DecisionTree, PartialModel, Decision, Variable}
import satify.model.CNF.*
import satify.update.dpll.CNFSimplification.*
import satify.update.dpll.PartialModelUtils.*
import satify.update.dpll.ConflictIdentification.isUnsat
import satify.model
import satify.model.Constant.{False, True}

import scala.language.postfixOps

object DPLL:

  /** Exhaustive search of all possible decisions to the partial model.
    * The three branches left assigning true to the first unconstrained variable, false to the right one, until
    * no more assignments are possible.
    * @param dec decision to make
    * @return decision tree from the current decision
    */
  def dpll(dec: Decision): DecisionTree =

    lazy val decide: (PartialModel, CNF, Constraint) => DecisionTree =
      (parModel, cnf, constr) =>
        dpll(
          Decision(updateParModel(parModel, constr), simplifyCnf(cnf, constr))
        )

    dec match
      case Decision(parModel, cnf) =>
        if isUnsat(cnf) then Leaf(dec)
        else
          filterUnconstrVars(extractModelFromCnf(cnf)) match
            case Variable(name, _) +: _ =>
              Branch(
                dec,
                decide(parModel, cnf, Constraint(name, true)),
                decide(parModel, cnf, Constraint(name, false))
              )
            case _ => Leaf(dec)

  /** Get all SAT solutions, e.g. all Leaf nodes where the CNF has been simplified to Symbol(True).
    * @param dt DecisionTree
    * @return a set of PartialModel(s).
    */
  def extractSolutionsFromDT(dt: DecisionTree): Set[PartialModel] =
    dt match
      case Leaf(Decision(pm, cnf)) =>
        cnf match
          case Symbol(True) => Set(pm)
          case _ => Set.empty
      case Branch(_, left, right) => extractSolutionsFromDT(left) ++ extractSolutionsFromDT(right)
