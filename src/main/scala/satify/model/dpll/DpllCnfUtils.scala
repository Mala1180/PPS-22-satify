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
   * @param constr constraint to be applied
   * @return Updated PartialExpression
   */
  def updateCnf(cnf : CNF, constr: Constraint): CNF =
    cnf match
      case c: (Or | Literal) => updateCnfCommon(c, constr)
      case a: And => updateCnfAnd(a, constr)

  /**
   * Update an Or or a Literal
   *
   * @param cnf of type Or | Literal
   * @param constr constraint to be applied
   * @return Updated Or or Literal
   */
  private def updateCnfCommon(cnf: Or | Literal, constr: Constraint): Or | Literal =
    cnf match
      case Symbol(variable) => updateSymbol(Symbol(variable), constr)
      case Or(left, right) => Or(updateCnfCommon(left, constr), updateCnfCommon(right, constr))
      case Not(symbol) => Not(updateSymbol(symbol, constr))
      case _ => cnf

  /**
   * Update a Symbol
   *
   * @param cnf of type Or | Literal
   * @param constr constraint to be applied
   * @return updated symbol
   */
  private def updateSymbol(symbol: Symbol, constr: Constraint): Symbol =
    symbol match
      case Symbol(variable) if variable.name == constr.name =>
        Symbol(Variable(constr.name, Option(constr.value)))
      case _ => symbol

  /**
   * Update the right side of an And
   *
   * @param cnf of type
   * @param constr constraint to be applied
   * @return And | Or | Literal
   */
  private def updateCnfAnd(cnf: And | Or | Literal, constr: Constraint): And | Or | Literal =
    cnf match
      case Symbol(variable) => updateSymbol(Symbol(variable), constr)
      case And(left, right) => And(updateCnfCommon(left, constr), updateCnfAnd(right, constr))
      case Or(left, right) => updateCnfCommon(Or(left, right), constr)
      case Not(symbol) => Not(updateSymbol(symbol, constr))