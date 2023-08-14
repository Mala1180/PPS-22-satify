package satify.update.converters

import satify.model.{CNF, Expression, Literal, Variable}

/** Object containing the Tseitin transformation algorithm. */
object TseitinTransformation:

  import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
  import satify.model.Expression.{replace as replaceExp, *}

  /** Applies the Tseitin transformation to the given expression in order to convert it into CNF.
    * @param exp the expression to transform.
    * @return the CNF expression.
    */
  def tseitin(exp: Expression): CNF =
    def reduction(exp1: CNF, exp2: CNF): CNF = exp1 match
      case l @ CNFOr(e1, e2) =>
        exp2 match
          case r @ CNFAnd(e3, e4) => CNFAnd(l, r)
          case r @ CNFOr(e3, e4) => CNFAnd(l, r)
          case _ => exp2
      case _ => exp1
    var transformations: List[(CNFSymbol, CNF)] = List()
    symbolsReplace(exp).foreach(s => transformations = transform(s) ::: transformations)
    val transformedExp = transformations.map(_._2)

    if transformedExp.size == 1 then transformedExp.head
    else transformedExp.reduceRight((s1, s2) => reduction(s1, s2))

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

    val zipped = zipWithSymbol(exp)
    if zipped.size == 1 then zipped
    else symbolSelector(zipWithSymbol(exp).reverse)

  /** Transform the Symbol and the corresponding expression to CNF form
    * @param exp a Symbol and the corresponding expression
    * @return a list of Symbol and expressions in CNF form for the given Symbol and expression
    */
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
      case (s @ Symbol(v), Not(lit)) =>
        List(
          (symbol(Symbol(v)), CNFOr(not(lit), not(Symbol(v)))),
          (symbol(Symbol(v)), CNFOr(symbol(lit), symbol(Symbol(v))))
        )
      case (s @ Symbol(v), And(e1, e2)) =>
        List(
          (symbol(s), CNFOr(CNFOr(not(e1), not(e2)), symbol(s))),
          (symbol(s), CNFOr(expr(e1), not(s))),
          (symbol(s), CNFOr(expr(e2), not(s)))
        )
      case (s @ Symbol(v), Or(e1, e2)) =>
        List(
          (symbol(s), CNFOr(CNFOr(expr(e1), expr(e2)), not(s))),
          (symbol(s), CNFOr(not(e1), symbol(s))),
          (symbol(s), CNFOr(not(e2), symbol(s)))
        )
      case (s @ Symbol(v), ss @ Symbol(vv)) => List((symbol(s), symbol(ss)))
