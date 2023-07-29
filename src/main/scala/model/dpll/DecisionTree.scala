package model.dpll

import model.*
import model.Expression.*
import DpllExpressionUtils.*

import scala.collection.immutable.Queue

type Model[T <: Variable] = Set[T]
type PartialModel = Model[PartialVariable]

/**
 * Decision is a node of DecisionTree.
 * @param varName The name of the variable which is constrained in the partialModel.
 * @param partialModel The current state of the PartialModel with varName's PartialVariable constrained.
 * @param partialExpression Updated expression after the decision.
 */
case class Decision(varName: String,
                    partialModel: PartialModel,
                    partialExpression: PartialExpression)

/**
 * DecisionTree is the main data structure for the DPLL algorithm.
 * It is represented as a Queue of Decision(s).
 * @param decisions
 */
case class DecisionTree(decisions: Queue[Decision])

/**
 * VariableConstraint is defined as an assignment of a PartialVariable to a Boolean variable.
 * @param varName Name of the variable
 * @param value Boolean value
 */
case class VariableConstraint(varName: String, value: Boolean)


object DecisionTree:

  /**
   * Create an instance of a DecisionTree given an initial EmptyExpression and a VariableConstraint.
   * @param emptyExpression
   * @param varConstraint
   * @return DecisionTree
   */
  def apply(emptyExpression: EmptyExpression)
           (varConstraint: VariableConstraint): DecisionTree = {
    val partialExpression = mapEmptyExpToPar(emptyExpression)
    DecisionTree(
      Queue(Decision(varConstraint.varName,
        assignVariable(varConstraint, extractModelFromExpression(partialExpression)),
        updateExpression(partialExpression, varConstraint)))
    )
  }

  /**
   * Create an instance of a DecisionTree based on an existing one where a VariableConstraint is applied.
   * @param decisionTree
   * @param varConstraint
   * @return DecisionTree
   */
  def decision(decisionTree: DecisionTree, varConstraint: VariableConstraint): DecisionTree =
    decisionTree match
      case DecisionTree(queue) => queue.last match
        case Decision(_, partialModel, partialExpression) =>
          DecisionTree(queue :+
            Decision(varConstraint.varName, assignVariable(varConstraint, partialModel),
              updateExpression(partialExpression, varConstraint)))


  /**
   * Update a PartialModel given as parameter constraining a variable (VariableConstraint)
   * @param varConstraint
   * @param partialModel
   * @return Updated PartialModel
   */
  private def assignVariable(varConstraint: VariableConstraint, partialModel: PartialModel): PartialModel =
    partialModel.map {
      case PartialVariable(name, _) if name == varConstraint.varName =>
        PartialVariable(name, Option(varConstraint.value))
      case v => v
    }