package satify.update.converters

import satify.model.{CNF, Expression, Literal, Variable}

import scala.annotation.tailrec

/** Object containing the Tseitin transformation algorithm. */
object TseitinTransformation:

  import satify.model.Expression.{replace as replaceExp, *}
  import satify.model.CNF.{Symbol as CNFSymbol, And as CNFAnd, Or as CNFOr, Not as CNFNot}

  /** Applies the Tseitin transformation to the given expression in order to convert it into CNF.
    * @param exp the expression to transform.
    * @return the CNF expression.
    */
  def tseitin(exp: Expression): CNF =
    var transformations: List[(CNFSymbol, CNF)] = List()
    symbolsReplace(exp).foreach(s => transformations = transform(s) ::: transformations)
    //transformations.reduce((s1, s2) => (s1._1, CNFAnd(s1._2, s2._2)))._2
    transformations.reduceRight((s1, s2) => (s1._1, {
      s1._2 match
        case CNFOr(e1, e2) => s2._2 match
          case CNFAnd(e3, e4) => CNFAnd(CNFOr(e1, e2), CNFAnd(e3, e4))
          case CNFOr(e3, e4) => CNFAnd(CNFOr(e1, e2), CNFOr(e3, e4))
          case _ => s2._2
        case _ => s1._2
    }))._2

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

