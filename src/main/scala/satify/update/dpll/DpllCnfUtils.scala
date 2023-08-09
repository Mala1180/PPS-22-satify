package satify.update.dpll

import satify.model.CNF.*
import satify.model.Constraint
import satify.model.*
import satify.model.Constant.True

object DpllCnfUtils:

  /** Extract a PartialModel from an expression in CNF.
   *
   * @param cnf where to extract a Model
   * @return Correspondent model from CNF given as parameter.
   */
  def extractModelFromCnf(cnf: CNF): PartialModel =
    cnf match
      case Symbol(variable: Variable) => Seq(variable)
      case And(e1, e2) => extractModelFromCnf(e1) ++ extractModelFromCnf(e2)
      case Or(e1, e2) => extractModelFromCnf(e1) ++ extractModelFromCnf(e2)
      case Not(e) => extractModelFromCnf(e)
      case _ => Seq.empty

  /** Update a CNF expression after a variable constraint has been applied.
   *
   * @param e      CNF subexpression to be updated
   * @param constr constraint to apply
   * @return Updated CNF
   */
  def updateCnf[T <: CNF](e: T, constr: Constraint): T =
    e match
      case Symbol(variable: Variable) if variable.name == constr.name =>
        Symbol(Variable(constr.name, Option(constr.value))).asInstanceOf[T]
      case And(left, right) => And(updateCnf(left, constr), updateCnf(right, constr)).asInstanceOf[T]
      case Or(left, right) => Or(updateCnf(left, constr), updateCnf(right, constr)).asInstanceOf[T]
      case Not(symbol) => Not(updateCnf(symbol, constr)).asInstanceOf[T]
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
    simplifyClosestOr(simplifyUppermostOr(cnf, constr), constr)

  private def simplifyUppermostOr[T <: CNF](cnf: T, constr: Constraint): T =
    def f[V <: CNF](e: V, cont: T): T = e match
      case Symbol(Variable(name, _)) if name == constr.name && constr.value => Symbol(True).asInstanceOf[T]
      case s@Symbol(_: Constant) => s.asInstanceOf[T]
      case Not(Symbol(Variable(name, _))) if name == constr.name && !constr.value => Symbol(True).asInstanceOf[T]
      case s@Not(Symbol(_: Constant)) => s.asInstanceOf[T]
      case _ => cont

    f(cnf, cnf) match
      case And(left, right) => And(simplifyUppermostOr(left, constr), simplifyUppermostOr(right, constr)).asInstanceOf[T]
      case Or(left, right) =>
        f(left, f(right, f(simplifyUppermostOr(left, constr), f(simplifyUppermostOr(right, constr), Or(left, right)
          .asInstanceOf[T]))))
      case e@_ => e


  private def simplifyClosestOr[T <: CNF](cnf: T, constr: Constraint): T =
    def g[V <: CNF](e: V, o: V, cont: V): V = e match
      case Symbol(Variable(name, _)) if name == constr.name && !constr.value => o
      case Not(Symbol(Variable(name, _))) if name == constr.name && constr.value => o
      case _ => cont

    cnf match
      case s@Symbol(_) => s
      case And(left, right) => And(simplifyClosestOr(left, constr), simplifyClosestOr(right, constr)).asInstanceOf[T]
      case Or(left, right) => g(left, right, g(right, left,
        Or(simplifyClosestOr(left, constr), simplifyClosestOr(right, constr)))).asInstanceOf[T]
      case e@_ => e
