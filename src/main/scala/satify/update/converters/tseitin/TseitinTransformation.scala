package satify.update.converters.tseitin

import satify.model.cnf.CNF
import satify.model.expression.Expression
import satify.model.expression.SymbolGeneration.converterVarPrefix

import scala.annotation.tailrec

/** Object containing the Tseitin transformation algorithm. */
private[converters] object TseitinTransformation:

  import Utils.*
  import satify.model.cnf.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
  import satify.model.cnf.Literal
  import satify.model.expression.Expression.{replace as replaceExp, *}

  /** Applies the Tseitin transformation to the given expression in order to convert it into CNF.
    * @param exp the expression to transform.
    * @return the CNF expression.
    */
  def tseitin(exp: Expression): CNF = concat(substitutions(exp).flatMap(transform))

  /** Substitute Symbols of nested subexpressions in all others expressions
    * @param exp the expression where to substitute Symbols
    * @return the decomposed expression in subexpressions with Symbols correctly substituted.
    */
  private[tseitin] def substitutions(exp: Expression): List[(Symbol, Expression)] =
    @tailrec
    def replace(
        list: List[(Symbol, Expression)],
        subexp: Expression,
        l: Symbol,
        acc: List[(Symbol, Expression)]
    ): List[(Symbol, Expression)] =
      list match
        case Nil => acc.reverse
        case (lit, e) :: t if e.contains(subexp) =>
          replace(t, subexp, l, (lit, e.replaceExp(subexp, l)) :: acc)
        case (lit, e) :: t => replace(t, subexp, l, (lit, e) :: acc)

    @tailrec
    def selector(
        list: List[(Symbol, Expression)],
        acc: List[(Symbol, Expression)]
    ): List[(Symbol, Expression)] =
      list match
        case Nil => acc.reverse
        case (l, e) :: tail => selector(replace(tail, e, l, Nil), (l, e) :: acc)


    val zipped = zipWithSymbol(exp).distinctBy(_._2)
    if zipped.size == 1 then zipped
    else selector(zipped.sortBy((_, e) => clauses(e)), Nil)

  /** Transform the Symbol and the corresponding expression to CNF form
    * @param exp a Symbol and the corresponding expression
    * @return a list of Symbol and expressions in CNF form for the given Symbol and expression
    * @throws IllegalArgumentException if the expression is not a subexpression
    */
  private[tseitin] def transform(exp: (Symbol, Expression)): List[CNF] =
    def expr(exp: Expression): Literal = exp match
      case Symbol(v) => CNFSymbol(v)
      case Not(Symbol(v)) => CNFNot(CNFSymbol(v))
      case _ => throw new IllegalArgumentException("Expression is not a literal")
    def symbol(exp: Expression): CNFSymbol = exp match
      case Symbol(v) => CNFSymbol(v)
      case _ => throw new IllegalArgumentException("Expression is not a literal")
    def not(exp: Expression): Literal = exp match
      case Not(Symbol(v)) => CNFSymbol(v)
      case Symbol(v) => CNFNot(CNFSymbol(v))
      case _ => throw new IllegalArgumentException("Expression is not a literal")

    val (s, e) = exp
    val sym = symbol(s)

    e match
      case Not(lit) =>
        CNFOr(not(lit), CNFNot(sym)) ::
          CNFOr(symbol(lit), sym) :: Nil
      case And(e1, e2) =>
        CNFOr(CNFOr(not(e1), not(e2)), sym) ::
          CNFOr(expr(e1), CNFNot(sym)) ::
          CNFOr(expr(e2), CNFNot(sym)) :: Nil
      case Or(e1, e2) =>
        CNFOr(CNFOr(expr(e1), expr(e2)), CNFNot(sym)) ::
          CNFOr(not(e1), sym) ::
          CNFOr(not(e2), sym) :: Nil
      case ssym @ Symbol(_) => List(symbol(ssym))

  /** Concat all subexpression in And to obtain a valid CNF expression.
    * @param subexpressions the subexpressions to concat.
    * @return the CNF expression.
    */
  private[tseitin] def concat(subexpressions: List[CNF]): CNF =
    if subexpressions.size == 1 then subexpressions.head
    else
      var concatenated = subexpressions
      concatenated = concatenated.prepended(CNFSymbol(converterVarPrefix + "0"))
      concatenated.reduceRight((s1, s2) =>
        CNFAnd(s1.asInstanceOf[CNFOr | Literal], s2.asInstanceOf[CNFAnd | CNFOr | Literal])
      )
