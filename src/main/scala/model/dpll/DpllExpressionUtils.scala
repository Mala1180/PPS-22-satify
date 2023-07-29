package model.dpll

import model.Expression.{And, Not, Or, Symbol}
import model.{AssignedExpression, AssignedVariable, EmptyExpression, EmptyVariable, Expression, PartialExpression, PartialVariable, Variable}

object DpllExpressionUtils:

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
  def updateExpression(parExp: PartialExpression, varConstr: VariableConstraint): PartialExpression =
    parExp match
      case Symbol(PartialVariable(name, _)) if name == varConstr.varName =>
        Symbol(PartialVariable(name, Option(varConstr.value)))
      case And(e1, e2) => And(updateExpression(e1, varConstr), updateExpression(e2, varConstr))
      case Or(e1, e2) => Or(updateExpression(e1, varConstr), updateExpression(e2, varConstr))
      case Not(e) => Not(updateExpression(e, varConstr))
      case _ => parExp

  /**
   * Maps an EmptyExpression to a PartialExpression
   * @param emptyExp EmptyExpression to be mapped
   * @return PartialExpression
   */
  def mapEmptyExpToPar(emptyExp: EmptyExpression): PartialExpression =
    mapExp(emptyExp, classOf[PartialVariable])

  /**
   * Maps a PartialExpression to an AssignedExpression
   * @param parExp PartialExpression to be mapped
   * @return AssignedExpression
   */
  def mapParExpToAss(parExp: PartialExpression): AssignedExpression =
    mapExp(parExp, classOf[AssignedVariable])

  /**
   * Map an Expression[V] to an Expression[T].
   * It maps both an EmptyExpression to a PartialExpression and a PartialExpression to an AssignedExpression
   * @param exp Expression to be mapped
   * @param toType Variable subtype
   * @tparam V Domain Variable subtype
   * @tparam T Codomain Variable subtype
   * @return The mapped Expression
   */
  private def mapExp[V <: Variable, T <: Variable](exp: Expression[V], toType: Class[T]): Expression[T] = {
    exp match
      case Symbol(EmptyVariable(name)) => toType match
        case _: Class[PartialVariable] => Symbol(PartialVariable(name, Option.empty))
        case _ => throw Exception(s"Illegal codomain type")
      case Symbol(PartialVariable(name, value)) => toType match
        case _: Class[AssignedVariable] => value match
          case None => throw Exception(s"Variable $name has no value")
          case Some(v) => Symbol(AssignedVariable(name, v))
        case _ => throw Exception(s"Illegal codomain type")
      case And(e1, e2) => And(mapExp(e1, toType), mapExp(e2, toType))
      case Or(e1, e2) => Or(mapExp(e1, toType), mapExp(e2, toType))
      case Not(e) => Not(mapExp(e, toType))
      case _ => throw Exception(s"Illegal parameter type")
  }