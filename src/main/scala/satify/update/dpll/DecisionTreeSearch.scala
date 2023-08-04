package satify.update

import satify.model.dpll.DecisionTree.*
import satify.model.dpll.{Constraint, Decision, DecisionTree}
import satify.model.{PartialExpression, PartialVariable}

import scala.language.postfixOps

object DecisionTreeSearch:
  
  /**
   * Exhaustive search of all possible decisions to the partial model.
   * The three branches left assigning true to the first unconstrained variable, false to the right one, until
   * no more assignments are possible.
   * @param dec decision to make
   * @return decision tree from the current decision
   */
  def search(dec: Decision): DecisionTree = dec match
    case Decision(parModel, parExp) =>
      val unContrVars = filterUnconstrVars(parModel)
      if (unContrVars.nonEmpty)
        Branch(dec, recStep(unContrVars.head, parModel, parExp, true),
          recStep(unContrVars.head, parModel, parExp, false))
      else Unsat

  /**
   * Recursive search step.
   * @param parVar partial variable
   * @param parModel partial model
   * @param parExp partial expression
   * @param b boolean assignment
   * @return DecisionTree from search
   */
  private def recStep(parVar: PartialVariable, parModel: PartialModel,
                      parExp: PartialExpression, b: Boolean): DecisionTree =
    search(
      Decision(updateParModel(parVar match
        case PartialVariable(name, _) => Constraint(name, b), parModel), parExp)
  )

  /**
   * Filters unconstrained variables from the partial model
   * @param parModel partial model
   * @return filtered partial model
   */
  private def filterUnconstrVars(parModel: PartialModel): PartialModel =
    parModel.filter { case PartialVariable(_, o) => o.isEmpty }

  /**
   * Update a PartialModel given as parameter constraining a variable (VariableConstraint)
   * @param varConstr variable constraint
   * @param parModel partial model
   * @return Updated PartialModel
   */
  private def updateParModel(varConstr: Constraint, parModel: PartialModel): PartialModel =
    parModel.map {
      case PartialVariable(name, _) if name == varConstr.variable =>
        PartialVariable(name, Option(varConstr.value))
      case v => v
    }
