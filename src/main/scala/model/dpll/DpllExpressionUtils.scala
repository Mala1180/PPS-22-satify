package model.dpll

import model.Expression.{And, Not, Or, Symbol, replace}
import model.{AssignedExpression, AssignedVariable, EmptyExpression, EmptyVariable, Expression, PartialExpression, PartialVariable, Variable}

object DpllExpressionUtils:

  /**
   * Generic Expression map.
   * @param e Expression to be mapped
   * @param f Variable mapping
   * @tparam T Domain Variable type
   * @tparam V Codomain Variable type
   * @return Mapped expression
   */
  def genMapExp[T <: Variable, V <: Variable]
  (e: Expression[T], f: T => V): Expression[V] =
    e match
      case Symbol(variable: T) => Symbol(f(variable))
      case And(e1, e2) => And(genMapExp(e1, f), genMapExp(e2, f))
      case Or(e1, e2) => Or(genMapExp(e1, f), genMapExp(e2, f))
      case Not(ne) => Not(genMapExp(ne, f))

  /**
   * Extract a Model[V] from an Expression[V]
   * @param exp where to extract a Model[V]
   * @return Correspondent model from expression given as parameter.
   */
  def extractModelFromExpression[T <: Variable](exp: Expression[T]): Model[T] =
    exp match
      case Symbol(variable) => Set(variable)
      case And(e1, e2) => extractModelFromExpression(e1) ++ extractModelFromExpression(e2)
      case Or(e1, e2) => extractModelFromExpression(e1) ++ extractModelFromExpression(e2)
      case Not(e) => extractModelFromExpression(e)


  /**
   * Update a PartialExpression after a variable constraint has been applied.
   * @param parExp to be updated
   * @param varConstr constraint to be applied
   * @return Updated PartialExpression
   */
  def updateParExp(parExp: PartialExpression, varConstr: VariableConstraint): PartialExpression =
    genMapExp(parExp, {
      case PartialVariable(varName, _) if varName == varConstr.varName =>
        PartialVariable(varName, Option(varConstr.value))
      case pv => pv
    })

  /**
   * Maps an EmptyExpression to a PartialExpression
   * @param emptyExp EmptyExpression to be mapped
   * @return PartialExpression
   */
  def mapEmptyExpToPar(emptyExp: EmptyExpression): PartialExpression =
    genMapExp(emptyExp, {
      case EmptyVariable(name) => PartialVariable(name, Option.empty)
    })

  /**
   * Maps a PartialExpression to an AssignedExpression.
   * if a PartialVariable is None, AssignedVariable is false by default.
   * @param parExp PartialExpression to be mapped
   * @return AssignedExpression
   */
  def mapParExpToAss(parExp: PartialExpression): AssignedExpression =
    genMapExp(parExp, {
      case PartialVariable(name, value) if value.isDefined => AssignedVariable(name, value.get)
      case PartialVariable(name, _) => AssignedVariable(name, false)
    })