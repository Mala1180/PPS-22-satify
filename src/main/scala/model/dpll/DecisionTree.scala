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
case class Assignment(varName: String, value: Boolean)

object DecisionTree:

  def apply(emptyExpression: EmptyExpression)
           (assignment: Assignment): DecisionTree = {
    val partialExpression = convert(emptyExpression, classOf[PartialVariable])
    DecisionTree(
      Queue(Decision(assignment.varName,
        assignVariable(assignment, extractModelFromExpression(partialExpression)),
        mapExpression(partialExpression, assignment)))
    )
  }

  private def assignVariable(assignment: Assignment, partialModel: PartialModel): PartialModel =
    partialModel.map {
      case PartialVariable(name, _) if name == assignment.varName =>
        PartialVariable(name, Option(assignment.value))
      case v => v
    }