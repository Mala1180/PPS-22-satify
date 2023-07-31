package update.converters

import model.{Expression, NamedVariable, Variable}

object TseitinTransformation:
  import model.EmptyModel
  import model.Expression.*

  /** Substitute Symbols of nested subexpressions in all others expressions
    * @param exp the expression where to substitute Symbols
    * @return the decomposed expression in subexpressions with Symbols correctly substituted.
    */
  def symbolsReplace[T <: Variable](exp: Expression[T]): List[(Symbol[T], Expression[T])] =
    def replacer(list: List[(Symbol[T], Expression[T])], subexp: Expression[T], l: Symbol[T]): List[(Symbol[T], Expression[T])] =
      list match
        case Nil => Nil
        case (lit, e) :: t if contains(e, subexp) => (lit, replace(e, subexp, l)) :: replacer(t, subexp, l)
        case (lit, e) :: t => (lit, e) :: replacer(t, subexp, l)

    def symbolSelector(list: List[(Symbol[T], Expression[T])]): List[(Symbol[T], Expression[T])] = list match
      case Nil => Nil
      case (l, e) :: tail => (l, e) :: symbolSelector(replacer(tail, e, l))

    symbolSelector(zipWithSymbol(exp).reverse)

  /** Transform the Symbol and the corresponding expression to CNF form
    * @param exp a Symbol and the corresponding expression
    * @return a list of Symbol and expressions in CNF form for the given Symbol and expression
    */
  def transform[T <: Variable](exp: (Symbol[T], Expression[T])): List[(Symbol[T], Expression[T])] =
    def not(exp: Expression[T]): Expression[T] = exp match
      case Not(sym) => sym
      case sym => Not(sym)
    exp match
      case (l, Not(lit)) => List((l, Or(Not(lit), Not(l))), (l, Or(lit, l)))
      case (l, And(e1, e2)) =>
        List((l, Or(Or(not(e1), not(e2)), l)), (l, Or(e1, not(l))), (l, Or(e2, not(l))))
      case (l, Or(e1, e2)) =>
        List((l, Or(Or(e1, e2), not(l))), (l, Or(not(e1), l)), (l, Or(not(e2), l)))
      case _ => List()

  /** TODO Transform the expression to the CNF form
    * @param exp the expression to transform
    * @return the expression in CNF form
    */
  def toCNF[T <: Variable](exp: Expression[T]): List[(Symbol[T], Expression[T])] =
    // TODO: to implement
    // expression = (a ∧ ¬ b) ∨ ¬ (c ∧ d)
    val sub = symbolsReplace(exp)
    println(sub)
    var list: List[(Symbol[T], Expression[T])] = List()
    sub.foreach(s => list = transform(s) ::: list)
    list
