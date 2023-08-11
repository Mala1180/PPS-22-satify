package satify.update.dpll

import satify.model.CNF.*
import satify.model.Constraint
import satify.model.*
import satify.model.Constant.{False, True}

object CNFSimplification:

  /** Simplify the CNF applying the constraint given as parameter.
    * @param cnf expression in CNF
    * @param constr constraint
    * @return simplified CNF
    */
  def simplifyCnf(cnf: CNF, constr: Constraint): CNF =
    simplifyClosestAnd(simplifyClosestOr(simplifyUppermostOr(updateCnf(cnf, constr), constr), constr))

  /** Simplify the clause (e.g. the uppermost Or near the root of CNF) substituting it with a True constant
    * if the constrained Literal in a clause is set to true s.t. v = true or Not(v) = true.
    * @param cnf expression in CNF
    * @param constr constraint
    * @tparam T type
    * @return partial simplified CNF
    */
  private def simplifyUppermostOr[T <: CNF](cnf: T, constr: Constraint): T =

    /** Propagate the simplification if the CNF in input is a Literal equal to the constrained Variable and
      * it is evaluated true, or if it is a Literal with a Constant which is evaluated true.
      * @param e expression in CNF
      * @param d default match case
      * @tparam V subtype of CNF
      * @return a Symbol(True) or d
      */
    def f[V <: CNF](e: V, d: T): T =
      (e match
        case Symbol(Variable(name, _)) if name == constr.name && constr.value => Symbol(True)
        case s @ Symbol(True) => s
        case Not(Symbol(Variable(name, _))) if name == constr.name && !constr.value => Symbol(True)
        case Not(Symbol(False)) => Symbol(True)
        case _ => d
      ).asInstanceOf[T]

    (f(cnf, cnf) match
      case And(left, right) =>
        And(simplifyUppermostOr(left, constr), simplifyUppermostOr(right, constr))
      case Or(left, right) =>
        /*
        1. Check if left branch is a True constant or the constrained Variable:
          1.1. if so, return True e.g. substitute the Or with a True;
          1.2. else, repeat this procedure (1.) for the right branch;
        2. If also the right branch isn't a True or the constrained Variable:
          2.1. recursively call the function on the left branch repeating 1.;
          2.2. if it doesn't reduce to a True, then recursively call the function on the right branch repeating 1.;
              If also 2.2. doesn't reduce to a True, the Or doesn't contain the constrained Variable,
              so return Or(left, right).
         */
        f(
          left,
          f(
            right,
            f(
              simplifyUppermostOr(left, constr),
              f(
                simplifyUppermostOr(right, constr),
                Or(left, right).asInstanceOf[T]
              )
            )
          )
        )
      case e @ _ => e
    ).asInstanceOf[T]

  /** Simplify the clause by deleting the constrained Literal inside that clause (e.g. substituting the
    * closest Or with the other branch, if any) when it is set to false s.t. v = false or Not(v) = false,
    * @param cnf CNF expression
    * @param constr constraint
    * @tparam T type
    * @return partial simplified CNF
    */
  private def simplifyClosestOr[T <: CNF](cnf: T, constr: Constraint): T =

    /** Return the specified value if the CNF is a negative Literal
      * @param e CNF expression to check
      * @param o return value if CNF is a negative Literal
      * @param d default match case
      * @tparam V subtype of CNF
      * @return o if cnf is a negative Literal, d otherwise
      */
    def g[V <: CNF](e: V, o: V, d: V): V = e match
      case Symbol(Variable(name, _)) if name == constr.name && !constr.value => o
      case Not(Symbol(Variable(name, _))) if name == constr.name && constr.value => o
      case Not(Symbol(True)) => o
      case Symbol(False) => o
      case _ => d

    (cnf match
      case And(left, right) => And(simplifyClosestOr(left, constr), simplifyClosestOr(right, constr))
      case Or(left, right) =>
        /*
        1. Check if the left branch is a negative Literal:
          1.1. If it is then substitute it with the right branch, by recursively call this function for further
              simplification;
          1.2. Else, repeat 1. on the right branch instead of the left (and using the left in 1.1.).
              If also the right branch isn't a negative Literal, then the current Or mustn't be simplified,
              so return an Or recursively call the function on the left branch and on the right.
         */
        g(
          left,
          simplifyClosestOr(right, constr),
          g(
            right,
            simplifyClosestOr(left, constr),
            Or(simplifyClosestOr(left, constr), simplifyClosestOr(right, constr))
          )
        )
      case e @ _ => e
    ).asInstanceOf[T]

  /** Simplify And(s) when a Literal is set to true s.t. v = true or Not(v) = true.
    * @param cnf CNF expression
    * @tparam T type
    * @return partial simplified CNF
    */
  private def simplifyClosestAnd[T <: CNF](cnf: T): T =

    /** Return the specified value if CNF is a Literal contains a Constant and it is evaluated true.
      * @param e expression in CNF
      * @param o return value if CNF is a positive Literal
      * @param d default match case
      * @tparam V subtype of CNF
      * @return o if cnf is a positive Literal containing a Constant, d otherwise
      */
    def h[V <: CNF](e: V, o: V, d: V): V = e match
      case Symbol(True) => o
      case Not(Symbol(False)) => o
      case _ => d

    (cnf match
      case Or(left, right) => Or(simplifyClosestAnd(left), simplifyClosestAnd(right))
      case And(left, right) =>
        /*
        1. Check if the left branch is a positive Literal:
          1.1. If so, substitute the And by recursively calling the function on the right branch
              for further simplification;
          1.2. Else, repeat 1. on the right branch instead of the left (and using the left in 1.1.).
              If also the right branch isn't a positive Literal, then the current And mustn't be simplified,
              so return an And recursively call the function on the left branch and on the right.
         */
        h(
          left,
          simplifyClosestAnd(right),
          h(
            right,
            simplifyClosestAnd(left),
            And(simplifyClosestAnd(left), simplifyClosestAnd(right))
          )
        )
      case e @ _ => e
    ).asInstanceOf[T]

  /** Update a CNF expression constraining a Variable.
    * @param cnf CNF subexpression to be updated
    * @param constr constraint to apply
    * @return Updated CNF
    */
  private def updateCnf[T <: CNF](cnf: T, constr: Constraint): T =
    (cnf match
      case Symbol(variable: Variable) if variable.name == constr.name =>
        if constr.value then Symbol(True)
        else Symbol(False)
      case And(left, right) => And(updateCnf(left, constr), updateCnf(right, constr))
      case Or(left, right) => Or(updateCnf(left, constr), updateCnf(right, constr))
      case Not(symbol) => Not(updateCnf(symbol, constr))
      case _ => cnf
    ).asInstanceOf[T]
