package model.dpll

import model.Expression.{And, Not, Or, Symbol}
import model.{PartialExpression, PartialVariable}

object DPLLUtils:

  def extractModelFromExpression(partialExpression: PartialExpression): PartialModel =
    partialExpression match
      case Symbol(v@PartialVariable(_, _)) => Set(v)
      case And(e1, e2) => extractModelFromExpression(e1) ++ extractModelFromExpression(e2)
      case Or(e1, e2) => extractModelFromExpression(e1) ++ extractModelFromExpression(e2)
      case Not(e) => extractModelFromExpression(e)

  def mapExpression(partialExpression: PartialExpression, varConstraint: VariableConstraint): PartialExpression =
    partialExpression match
      case Symbol(PartialVariable(name, _)) if name == varConstraint.varName =>
        Symbol(PartialVariable(name, Option(varConstraint.value)))
      case And(e1, e2) => And(mapExpression(e1, varConstraint), mapExpression(e2, varConstraint))
      case Or(e1, e2) => Or(mapExpression(e1, varConstraint), mapExpression(e2, varConstraint))
      case Not(e) => Not(mapExpression(e, varConstraint))
      case _ => partialExpression