package satify.update.converters

import satify.model.{CNF, Expression, Variable}

/** Object containing the Tseitin transformation algorithm. */
object TseitinTransformation:

  import satify.model.Expression.{replace as replaceExp, *}
  import satify.model.CNF.{Symbol as CNFSymbol, And as CNFAnd, Or as CNFOr, Not as CNFNot}

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
  import satify.model.Literal
  def transform(exp: (Expression.Symbol, Expression)): List[(CNFSymbol, CNF)] =
    def expr(exp: Expression): Literal = exp match
      case Symbol(v) => CNFSymbol(Variable(v, None))
      case Not(Symbol(v)) => CNFNot(CNFSymbol(Variable(v, None)))
      case _ => throw new Exception("Expression is not a Symbol or a Not Symbol")

    def symbol(exp: Expression): CNFSymbol = exp match
      case Symbol(v) => CNFSymbol(Variable(v, None))
      case _ => throw new Exception("Expression is not a Symbol")

    def not(exp: Expression): Literal = exp match
      case Not(Symbol(v)) => CNFSymbol(Variable(v, None))
      case Symbol(v) => CNFNot(CNFSymbol(Variable(v, None)))
      case _ => throw new Exception("Expression is not a Symbol or a Not Symbol")

    exp match
      case (l@Symbol(v), Not(lit)) =>
        List((symbol(Symbol(v)), CNFOr(not(lit), not(Symbol(v)))), (symbol(Symbol(v)), CNFOr(symbol(lit), symbol(Symbol(v)))))

      case (l@Symbol(v), And(e1, e2)) =>
        List((symbol(l), CNFOr(CNFOr(not(e1), not(e2)), symbol(l))), (symbol(l), CNFOr(expr(e1), not(l))), (symbol(l), CNFOr(expr(e2), not(l))))

      case (l@Symbol(v), Or(e1, e2)) =>
        List((symbol(l), CNFOr(CNFOr(expr(e1), expr(e2)), not(l))), (symbol(l), CNFOr(not(e1), symbol(l))), (symbol(l), CNFOr(not(e2), symbol(l))))
      case _ => List()

    /* Transform the Symbol and the corresponding expression to CNF form
     *
     * @param exp a Symbol and the corresponding expression
     * @return a list of Symbol and expressions in CNF form for the given Symbol and expression*/
    /*def transform(exp: (Symbol, Expression)): List[(Symbol, Expression)] =
      def not(exp: Expression): Expression = exp match
        case Not(sym) => sym
        case sym => Not(sym)

      exp match
        case (l, Not(lit)) => List((l, Or(Not(lit), Not(l))), (l, Or(lit, l)))
        case (l, And(e1, e2)) =>
          List((l, Or(Or(not(e1), not(e2)), l)), (l, Or(e1, not(l))), (l, Or(e2, not(l))))
        case (l, Or(e1, e2)) =>
          List((l, Or(Or(e1, e2), not(l))), (l, Or(not(e1), l)), (l, Or(not(e2), l)))
        case _ => List()*/

  /** Transforms the given expression into CNF following Tseitin Method.
    *
    * @param exp the expression to transform
    * @return the expression in CNF form with new symbols introduced.
    */
  def toCNF(exp: Expression): Expression =
    var transformations: List[(CNFSymbol, CNF)] = List()
    symbolsReplace(exp).foreach(s => transformations = transform(s) ::: transformations)
    //transformations.reduce((s1, s2) => (s1._1, CNFAnd(s1._2, s2._2)))._2
    transformations.foreach(println)
    ???
    //transformations.reduceRight((s1, s2) => (s1._1, CNFAnd(s1._2, s2._2)))._2
