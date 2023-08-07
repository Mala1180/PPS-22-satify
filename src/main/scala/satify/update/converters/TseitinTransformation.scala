package satify.update.converters

import satify.model.{CNF, Expression, Variable}

/** Object containing the Tseitin transformation algorithm. */
object TseitinTransformation:

  import satify.model.Expression.{replace as replaceExp, *}

  /** Applies the Tseitin transformation to the given expression in order to convert it into CNF.
    * @param exp the expression to transform.
    * @return the CNF expression.
    */
  def tseitin(exp: Expression): CNF =
    val cnf = toCNF(exp)
    // TODO to transform in CNF Model format here or in toCNF method
    ???

  /** Substitute Symbols of nested subexpressions in all others expressions
    * @param exp the expression where to substitute Symbols
    * @return the decomposed expression in subexpressions with Symbols correctly substituted.
    */
  def symbolsReplace(exp: Expression): List[(Symbol, Expression)] =
    def replace(
        list: List[(Symbol, Expression)],
        subexp: Expression,
        l: Symbol
    ): List[(Symbol, Expression)] =
      list match
        case Nil => Nil
        case (lit, e) :: t if contains(e, subexp) => (lit, replaceExp(e, subexp, l)) :: replace(t, subexp, l)
        case (lit, e) :: t => (lit, e) :: replace(t, subexp, l)

    def symbolSelector(list: List[(Symbol, Expression)]): List[(Symbol, Expression)] = list match
      case Nil => Nil
      case (l, e) :: tail => (l, e) :: symbolSelector(replace(tail, e, l))

    symbolSelector(zipWithSymbol(exp).reverse)

  /** Transform the Symbol and the corresponding expression to CNF form
    * @param exp a Symbol and the corresponding expression
    * @return a list of Symbol and expressions in CNF form for the given Symbol and expression
    */
  def transform(exp: (Symbol, Expression)): List[(Symbol, Expression)] =
    def not(exp: Expression): Expression = exp match
      case Not(sym) => sym
      case sym => Not(sym)
    exp match
      case (l, Not(lit)) => List((l, Or(Not(lit), Not(l))), (l, Or(lit, l)))
      case (l, And(e1, e2)) =>
        List((l, Or(Or(not(e1), not(e2)), l)), (l, Or(e1, not(l))), (l, Or(e2, not(l))))
      case (l, Or(e1, e2)) =>
        List((l, Or(Or(e1, e2), not(l))), (l, Or(not(e1), l)), (l, Or(not(e2), l)))
      case _ => List()

  /** Transforms the given expression into CNF following Tseitin Method.
    *
    * @param exp the expression to transform
    * @return the expression in CNF form with new symbols introduced.
    */
  def toCNF(exp: Expression): Expression =
    var transformations: List[(Symbol, Expression)] = List()
    symbolsReplace(exp).foreach(s => transformations = transform(s) ::: transformations)
    transformations.reduceRight((s1, s2) => (s1._1, And(s1._2, s2._2)))._2
