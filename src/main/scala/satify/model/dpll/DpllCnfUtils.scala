package satify.model.dpll

import satify.model.{Literal, *}
import satify.model.CNF.*

object DpllCnfUtils:

  /** Extract a PartialModel from an expression in CNF.
   *
   * @param cnf where to extract a Model
   * @return Correspondent model from CNF given as parameter.
   */
  def extractModelFromCnf(cnf: CNF): PartialModel =
    cnf match
      case Symbol(variable) => Seq(variable)
      case And(e1, e2) => extractModelFromCnf(e1) ++ extractModelFromCnf(e2)
      case Or(e1, e2) => extractModelFromCnf(e1) ++ extractModelFromCnf(e2)
      case Not(e) => extractModelFromCnf(e)

  /** Update a CNF expression after a variable constraint has been applied.
   *
   * @param e      CNF subexpression to be updated
   * @param constr constraint to apply
   * @return Updated CNF
   */
  def updateCnf[A <: CNF](e: A, constr: Constraint): A =
    e match
      case Symbol(variable) if variable.name == constr.name =>
        Symbol(Variable(constr.name, Option(constr.value))).asInstanceOf[A]
      case Or(left, right) => Or(updateCnf(left, constr), updateCnf(right, constr)).asInstanceOf[A]
      case Not(symbol) => Not(updateCnf(symbol, constr)).asInstanceOf[A]
      case _ => e