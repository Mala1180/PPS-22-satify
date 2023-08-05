
package satify.model.dpll

import satify.model.{Literal, *}
import satify.model.CNF.*

object DpllCnfUtils:

  /** Extract a PartialModel from an expression in CNF
   * @param exp where to extract a Model
   * @return Correspondent model from expression given as parameter.
   */
  def extractModelFromCnf(cnf: CNF): PartialModel =
    cnf match
      case Symbol(variable) => Seq(variable)
      case And(e1, e2) => extractModelFromCnf(e1) ++ extractModelFromCnf(e2)
      case Or(e1, e2) => extractModelFromCnf(e1) ++ extractModelFromCnf(e2)
      case Not(e) => extractModelFromCnf(e)

  /** Update a CNF expression after a variable constraint has been applied.
   * @param cnf to be updated
   * @param varConstr constraint to be applied
   * @return Updated PartialExpression
   */
  def updateCnf(cnf : CNF, varConstr: Constraint): CNF =
    cnf match
      case c: (Or | Literal) => updateCnfCommon(c, varConstr)
      case a@_ => updateCnfAnd(a, varConstr)

  private def updateCnfCommon(cnf: Or | Literal, varConstr: Constraint): Or | Literal =
    cnf match
      case Symbol(variable) if variable.name == varConstr.name =>
        Symbol(Variable(varConstr.name, Option(varConstr.value)))
      case Or(left, right) => Or(updateCnfCommon(left, varConstr), updateCnfCommon(right, varConstr))
      case Not(symbol) => Not(updateSymbol(symbol, varConstr))
      case _ => cnf

  private def updateSymbol(symbol: Symbol, varConstr: Constraint): Symbol =
    symbol match
      case Symbol(variable) if variable.name == varConstr.name =>
        Symbol(Variable(varConstr.name, Option(varConstr.value)))
      case _ => symbol

  private def updateCnfAnd(cnf: CNF, varConstr: Constraint): CNF =
    cnf match
      case Symbol(value) => updateSymbol(Symbol(value), varConstr)
      case And(left, right) => And(updateCnfCommon(left, varConstr), updateCnfAnd(right, varConstr))
      case Or(left, right) => updateCnfCommon(Or(left, right), varConstr)
      case Not(symbol) => Not(updateSymbol(symbol, varConstr))

