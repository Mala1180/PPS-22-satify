package satify.update.dpll

import satify.model.DecisionTree.*
import satify.model.{CNF, Constraint, Decision, DecisionTree, PartialModel, Variable}
import satify.update.dpll.DpllCnfUtils.*
import satify.model

import scala.language.postfixOps

object DecisionTreeSearch:
  
  /**
   * Exhaustive search of all possible decisions to the partial model.
   * The three branches left assigning true to the first unconstrained variable, false to the right one, until
   * no more assignments are possible.
   * @param dec decision to make
   * @return decision tree from the current decision
   */
  def dpll(dec: Decision): DecisionTree = dec match
    case Decision(parModel, cnf) =>
      val unContrVars = filterUnconstrVars(parModel)
      if (unContrVars.nonEmpty)
        Branch(dec, recStep(unContrVars.head, parModel, updateCnf(cnf, Constraint(unContrVars.head.name, true)), true),
          recStep(unContrVars.head, parModel, updateCnf(cnf, Constraint(unContrVars.head.name, false)), false))
      else Unsat

  /**
   * Recursive search step.
   * @param parVar partial variable
   * @param parModel partial model
   * @param cnf CNF expression
   * @param b boolean assignment
   * @return DecisionTree from dpll
   */
  private def recStep(parVar: Variable, parModel: PartialModel,
                      cnf: CNF, b: Boolean): DecisionTree =
    dpll(
      Decision(updateParModel(parVar match
        case Variable(name, _) => Constraint(name, b), parModel), cnf)
  )

  /**
   * Filters unconstrained variables from the partial model
   * @param parModel partial model
   * @return filtered partial model
   */
  private def filterUnconstrVars(parModel: PartialModel): PartialModel =
    parModel.filter { case Variable(_, o) => o.isEmpty }

  /**
   * Update a PartialModel given as parameter constraining a variable (VariableConstraint)
   * @param varConstr variable constraint
   * @param parModel partial model
   * @return Updated PartialModel
   */
  private def updateParModel(varConstr: Constraint, parModel: PartialModel): PartialModel =
    parModel.map {
      case Variable(name, _) if name == varConstr.name =>
        Variable(name, Option(varConstr.value))
      case v => v
    }
