package satify.update.dpll

import satify.model.CNF.*
import satify.model.Constraint
import satify.model.*

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
      case And(left, right) => And(updateCnf(left, constr), updateCnf(right, constr)).asInstanceOf[A]
      case Or(left, right) => Or(updateCnf(left, constr), updateCnf(right, constr)).asInstanceOf[A]
      case Not(symbol) => Not(updateCnf(symbol, constr)).asInstanceOf[A]
      case _ => e

  /**
  Simplify CNF:
    - If a Literal in a clause is set to true s.t. v = true or Not(v) = true, simplify the clause
      (e.g. the closest Or near the root of the CNF expression) substituting it with a Variable set to true.
    - If a Literal in a clause is set to false s.t. v = false or Not(v) = false, simplify the clause
      by deleting the Variable inside that clause (e.g. substituting the closest Or with the other branch).
    - If an And have left and right true substitute it with a Variable set to true.
  N.B.:
    - Do not simplify when all the Literals of a clause are false
    - Do not simplify when at least a Literal of an And is false
  **/
  def simplifyCnf(cnf: CNF, constr: Constraint): CNF =
    simplifyUppermostOr(cnf, constr)

  private def simplifyUppermostOr[A <: CNF](cnf: A, constr: Constraint): A = cnf match
    case Symbol(value) if value.name == constr.name && constr.value =>
      Symbol(Variable(value.name, Some(true))).asInstanceOf[A]
    case Not(Symbol(value)) if value.name == constr.name && !constr.value =>
      Symbol(Variable(value.name, Some(true))).asInstanceOf[A]

    case And(left, right) => And(simplifyUppermostOr(left, constr), simplifyUppermostOr(right, constr))
      .asInstanceOf[A]
    case Or(left, right) => left match
      case Symbol(value) if value.name == constr.name || value.name == "*" && constr.value =>
        Symbol(Variable("*", Some(true))).asInstanceOf[A]
      case Not(Symbol(value)) if value.name == constr.name && !constr.value =>
        Symbol(Variable("*", Some(true))).asInstanceOf[A]
      case _ => right match
        case Symbol(value) if value.name == constr.name || value.name == "*" => Symbol(Variable("*", Some(true))).asInstanceOf[A]
        case Not(Symbol(value)) if value.name == constr.name && !constr.value =>
          Symbol(Variable("*", Some(true))).asInstanceOf[A]
        case _ => Or(simplifyUppermostOr(left, constr), simplifyUppermostOr(right, constr)).asInstanceOf[A]
    case e@_ => e

