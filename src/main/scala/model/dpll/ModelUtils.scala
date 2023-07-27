package model.dpll

import model.Expression.{And, Not, Or, Symbol}
import model.{PartialExpression, PartialVariable}

object ModelUtils:

  def extractModelFromExpression(partialExpression: PartialExpression): PartialModel =
    partialExpression match
      case Symbol(v@PartialVariable(_, _)) => Set(v)
      case And(e1, e2) => extractModelFromExpression(e1) ++ extractModelFromExpression(e2)
      case Or(e1, e2) => extractModelFromExpression(e1) ++ extractModelFromExpression(e2)
      case Not(e) => extractModelFromExpression(e)


  def mapExpression(partialExpression: PartialExpression,
                            varName: String, assignment: Boolean): PartialExpression =
    partialExpression match
      case Symbol(PartialVariable(name, _)) if name == varName => Symbol(PartialVariable(name, Option(assignment)))
      case And(e1, e2) => And(mapExpression(e1, varName, assignment), mapExpression(e2, varName, assignment))
      case Or(e1, e2) => Or(mapExpression(e1, varName, assignment), mapExpression(e2, varName, assignment))
      case Not(e) => Not(mapExpression(e, varName, assignment))
      case _ => partialExpression