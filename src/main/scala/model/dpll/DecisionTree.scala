package model.dpll

import model.*
import model.Expression.*
import ModelUtils.*

import scala.collection.immutable.Queue

type PartialModel = Set[PartialVariable]
case class Decision(varName: String,
                    partialModel: PartialModel,
                    partialExpression: PartialExpression)
case class DecisionTree(decisions: Queue[Decision])

object DecisionTree:

  def apply(emptyExpression: EmptyExpression)
           (varName: String, assignment: Boolean): DecisionTree = {
    val partialExpression = convert(emptyExpression, classOf[PartialVariable])
    DecisionTree(
      Queue(Decision(varName,
        assignVariable(varName, assignment, extractModelFromExpression(partialExpression)),
        mapExpression(partialExpression, varName, assignment)))
    )
  }

  private def assignVariable(varName: String, assignment: Boolean, partialModel: PartialModel): PartialModel =
    partialModel.map {
      case PartialVariable(name, _) if name == varName => PartialVariable(varName, Option(assignment))
      case v => v
    }