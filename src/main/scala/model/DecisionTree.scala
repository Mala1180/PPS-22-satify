package model

import model.EmptyExpression
import model.Expression.*
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
        extractModelFromExpression(partialExpression).map {
          case PartialVariable(name, _) if name == varName => PartialVariable(varName, Option(assignment))
          case v => v
        },
        mapExpression(partialExpression, varName, assignment)))
    )
  }

  private def extractModelFromExpression(partialExpression: PartialExpression): PartialModel =
    partialExpression match
      case Symbol(v@PartialVariable(_, _)) => Set(v)
      case And(e1, e2) => extractModelFromExpression(e1) ++ extractModelFromExpression(e2)
      case Or(e1, e2) => extractModelFromExpression(e1) ++ extractModelFromExpression(e2)
      case Not(e) => extractModelFromExpression(e)

  private def mapExpression(partialExpression: PartialExpression,
                            varName: String, assignment: Boolean): PartialExpression =
    partialExpression match
      case Symbol(PartialVariable(name, _)) if name == varName => Symbol(PartialVariable(name, Option(assignment)))
      case And(e1, e2) => And(mapExpression(e1, varName, assignment), mapExpression(e2, varName, assignment))
      case Or(e1, e2) => Or(mapExpression(e1, varName, assignment), mapExpression(e2, varName, assignment))
      case Not(e) => Not(mapExpression(e, varName, assignment))
      case _ => partialExpression
