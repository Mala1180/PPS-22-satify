package satify.update.dpll

import satify.model.CNF.*
import satify.model.Constraint
import satify.model.*
import satify.model.Constant.{False, True}

object CNFSimplification:

  /**
   * Simplify the CNF applying the constraint given as parameter.
   * @param cnf expression in CNF
   * @param constr constraint
   * @return simplified CNF
   */
  def simplifyCnf(cnf: CNF, constr: Constraint): CNF =
    simplifyClosestAnd(simplifyClosestOr(simplifyUppermostOr(cnf, constr), constr))

  /**
   * Simplify the clause (e.g. the uppermost Or near the root of CNF) substituting it with a True constant
   * if the constrained Literal in a clause is set to true s.t. v = true or Not(v) = true.
   *
   * @param cnf expression in CNF
   * @param constr constraint
   * @tparam T type
   * @return partial simplified CNF
   */
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


  /**
   * Simplify the clause by deleting the constrained Literal inside that clause (e.g. substituting the
   * closest Or with the other branch, if any) when it is set to false s.t. v = false or Not(v) = false,
   *
   * @param cnf CNF expression
   * @param constr constraint
   * @tparam T type
   * @return partial simplified CNF
   */
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


  /**
   * Simplify And(s) when a Literal is set to true s.t. v = true or Not(v) = true.
   * @param cnf CNF expression
   * @tparam T type
   * @return partial simplified CNF
   */
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