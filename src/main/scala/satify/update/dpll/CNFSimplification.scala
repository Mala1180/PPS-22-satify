package satify.update.dpll

import satify.model.CNF.*
import satify.model.Constraint
import satify.model.*
import satify.model.Constant.{False, True}

object CNFSimplification:

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
    simplifyClosestAnd(simplifyClosestOr(simplifyUppermostOr(cnf, constr), constr))

  private def simplifyUppermostOr[T <: CNF](cnf: T, constr: Constraint): T =
    def f[V <: CNF](e: V, d: T): T = e match
      case Symbol(Variable(name, _)) if name == constr.name && constr.value => Symbol(True).asInstanceOf[T]
      case s@Symbol(_: Constant) => s.asInstanceOf[T]
      case Not(Symbol(Variable(name, _))) if name == constr.name && !constr.value => Symbol(True).asInstanceOf[T]
      case s@Not(Symbol(_: Constant)) => s.asInstanceOf[T]
      case _ => d

    f(cnf, cnf) match
      case And(left, right) => And(simplifyUppermostOr(left, constr), simplifyUppermostOr(right, constr)).asInstanceOf[T]
      case Or(left, right) =>
        f(left, f(right, f(simplifyUppermostOr(left, constr), f(simplifyUppermostOr(right, constr), Or(left, right)
          .asInstanceOf[T]))))
      case e@_ => e


  private def simplifyClosestOr[T <: CNF](cnf: T, constr: Constraint): T =
    def g[V <: CNF](e: V, o: V, d: V): V = e match
      case Symbol(Variable(name, _)) if name == constr.name && !constr.value => o
      case Not(Symbol(Variable(name, _))) if name == constr.name && constr.value => o
      case _ => d

    cnf match
      case And(left, right) => And(simplifyClosestOr(left, constr), simplifyClosestOr(right, constr)).asInstanceOf[T]
      case Or(left, right) =>
        g(left, simplifyClosestOr(right, constr),
          g(right, simplifyClosestOr(left, constr),
            Or(simplifyClosestOr(left, constr), simplifyClosestOr(right, constr)))).asInstanceOf[T]
      case e@_ => e


  private def simplifyClosestAnd[T <: CNF](cnf: T): T =
    def h[V <: CNF](e: V, o: V, d: V): V = e match
      case Symbol(True) => o
      case Not(Symbol(False)) => o
      case _ => d

    cnf match
      case Or(left, right) => Or(simplifyClosestAnd(left), simplifyClosestAnd(right)).asInstanceOf[T]
      case And(left, right) =>
        val sRight = simplifyClosestAnd(right)
        h(left, h(sRight, Symbol(True), sRight), And(left, sRight)).asInstanceOf[T]
      case e@_ => e