package model.dpll

import model.Expression.{And, Not, Or, Symbol}
import model.{PartialExpression, PartialVariable}

object DPLLUtils:

  /**
   * Extract a PartialModel from a PartialExpression
   * @param partialExpression
   * @return Correspondent PartialModel from partialExpression given as parameter.
   */
  def extractModelFromExpression(partialExpression: PartialExpression): PartialModel =
    partialExpression match
      case Symbol(v@PartialVariable(_, _)) => Set(v)
      case And(e1, e2) => extractModelFromExpression(e1) ++ extractModelFromExpression(e2)
      case Or(e1, e2) => extractModelFromExpression(e1) ++ extractModelFromExpression(e2)
      case Not(e) => extractModelFromExpression(e)

  /**
   * Update a PartialExpression after a variable constraint has been applied.
   * @param partialExpression
   * @param varConstraint
   * @return Updated PartialExpression
   */
  def updateExpression(partialExpression: PartialExpression, varConstraint: VariableConstraint): PartialExpression =
    partialExpression match
      case Symbol(PartialVariable(name, _)) if name == varConstraint.varName =>
        Symbol(PartialVariable(name, Option(varConstraint.value)))
      case And(e1, e2) => And(updateExpression(e1, varConstraint), updateExpression(e2, varConstraint))
      case Or(e1, e2) => Or(updateExpression(e1, varConstraint), updateExpression(e2, varConstraint))
      case Not(e) => Not(updateExpression(e, varConstraint))
      case _ => partialExpression