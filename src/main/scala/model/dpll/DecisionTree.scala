package model.dpll

import model.*
import model.Expression.*
import DPLLUtils.*

import scala.collection.immutable.Queue

type PartialModel = Set[PartialVariable]
case class Decision(varName: String,
                    partialModel: PartialModel,
                    partialExpression: PartialExpression)
case class DecisionTree(decisions: Queue[Decision])
case class VariableConstraint(varName: String, value: Boolean)

object DecisionTree:

  def apply(emptyExpression: EmptyExpression)
           (varConstraint: VariableConstraint): DecisionTree = {
    val partialExpression = cast(emptyExpression, classOf[PartialVariable])
    DecisionTree(
      Queue(Decision(varConstraint.varName,
        assignVariable(varConstraint, extractModelFromExpression(partialExpression)),
        mapExpression(partialExpression, varConstraint)))
    )
  }

  def decision(decisionTree: DecisionTree, varConstraint: VariableConstraint): DecisionTree =
    decisionTree match
      case DecisionTree(queue) => queue.last match
        case Decision(_, partialModel, partialExpression) =>
          DecisionTree(queue :+
            Decision(varConstraint.varName, assignVariable(varConstraint, partialModel),
              mapExpression(partialExpression, varConstraint)))


  private def assignVariable(varConstraint: VariableConstraint, partialModel: PartialModel): PartialModel =
    partialModel.map {
      case PartialVariable(name, _) if name == varConstraint.varName =>
        PartialVariable(name, Option(varConstraint.value))
      case v => v
    }