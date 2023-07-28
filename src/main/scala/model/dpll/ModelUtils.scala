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

  def mapExpression(partialExpression: PartialExpression, assignment: Assignment): PartialExpression =
    partialExpression match
      case Symbol(PartialVariable(name, _)) if name == assignment.varName =>
        Symbol(PartialVariable(name, Option(assignment.value)))
      case And(e1, e2) => And(mapExpression(e1, assignment), mapExpression(e2, assignment))
      case Or(e1, e2) => Or(mapExpression(e1, assignment), mapExpression(e2, assignment))
      case Not(e) => Not(mapExpression(e, assignment))
      case _ => partialExpression