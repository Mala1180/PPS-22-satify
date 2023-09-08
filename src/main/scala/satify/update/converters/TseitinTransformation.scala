package satify.update.converters

import satify.model.expression.Expression
import satify.model.{CNF, Variable}
import scala.annotation.tailrec

/** Object containing the Tseitin transformation algorithm. */
private[converters] object TseitinTransformation:

  import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
  import satify.model.Literal
  import satify.model.expression.Expression.{replace as replaceExp, *}

  /** Applies the Tseitin transformation to the gt iven expression in order to convert it into CNF.
    * @param exp the expression to transform.
    * @return the CNF expression.
    */
  def tseitin(exp: Expression): CNF =
    var transformations: List[CNF] = List()
    symbolsReplace(exp).foreach(s => transformations = transform(s) ::: transformations)
    concat(transformations)

  // TODO TO ADD TESTS
  /** Concat all subexpression in And to obtain a valid CNF expression.
    *
    * @param subexpressions the subexpressions to concat.
    * @return the CNF expression.
    */
  private def concat(subexpressions: List[CNF]): CNF =
    if subexpressions.size == 1 then subexpressions.head
    else
      var concatenated = subexpressions
      concatenated = concatenated.prepended(CNFSymbol(Variable("TSTN0")))
      concatenated.reduceRight((s1, s2) =>
        CNFAnd(s1.asInstanceOf[CNFOr | Literal], s2.asInstanceOf[CNFAnd | CNFOr | Literal])
      )

  /** Substitute Symbols of nested subexpressions in all others expressions
    *
    * @param exp the expression where to substitute Symbols
    * @return the decomposed expression in subexpressions with Symbols correctly substituted.
    */
  def symbolsReplace(exp: Expression): List[(Symbol, Expression)] = {
    @tailrec
    def replace(
        list: List[(Symbol, Expression)],
        subexp: Expression,
        l: Symbol,
        acc: List[(Symbol, Expression)]
    ): List[(Symbol, Expression)] =
      list match {
        case Nil => acc.reverse
        case (lit, e) :: t if contains(e, subexp) =>
          replace(t, subexp, l, (lit, replaceExp(e, subexp, l)) :: acc)
        case (lit, e) :: t => replace(t, subexp, l, (lit, e) :: acc)
      }

    @tailrec
    def symbolSelector(
        list: List[(Symbol, Expression)],
        acc: List[(Symbol, Expression)]
    ): List[(Symbol, Expression)] =
      list match {
        case Nil => acc.reverse
        case (l, e) :: tail => symbolSelector(replace(tail, e, l, Nil), (l, e) :: acc)
      }

    val zipped = zipWithSymbol(exp)
    if (zipped.size == 1) zipped
    else symbolSelector(zipped.reverse, Nil)
  }

  /** Transform the Symbol and the corresponding expression to CNF form
    *
    * @param exp a Symbol and the corresponding expression
    * @return a list of Symbol and expressions in CNF form for the given Symbol and expression
    * @throws IllegalArgumentException if the expression is not a subexpression
    */
  def transform(exp: (Expression.Symbol, Expression)): List[CNF] =
    def expr(exp: Expression): Literal = exp match
      case Symbol(v) => CNFSymbol(Variable(v, None))
      case Not(Symbol(v)) => CNFNot(CNFSymbol(Variable(v, None)))
      case _ => throw new IllegalArgumentException("Expression is not a literal")
    def symbol(exp: Expression): CNFSymbol = exp match
      case Symbol(v) => CNFSymbol(Variable(v, None))
      case _ => throw new IllegalArgumentException("Expression is not a literal")
    def not(exp: Expression): Literal = exp match
      case Not(Symbol(v)) => CNFSymbol(Variable(v, None))
      case Symbol(v) => CNFNot(CNFSymbol(Variable(v, None)))
      case _ =>
        throw new IllegalArgumentException("Expression is not a literal")

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

  /** Method to check if an expression is in CNF form and can be converted to CNF form.
    * @param expression The expression to check.
    * @return true if the expression could be converted to CNF form, false otherwise and in case of empty expression.
    */
  def isCNF(expression: Expression): Boolean =
    import Expression.*
    expression match
      case Symbol(_) => true
      case Not(Symbol(_)) => true
      case And(l, r) =>
        (l, r) match
          case (And(_, _), _) => false
          case _ => isCNF(l) && isCNF(r);
      case Or(l, r) =>
        (l, r) match
          case (And(_, _), _) | (_, And(_, _)) => false
          case _ => isCNF(l) && isCNF(r);
      case _ => false

  /** Method to check if an expression is in CNF form and can be converted to CNF form.
    * @param expression The expression to check.
    * @return true if the expression could be converted to CNF form, false otherwise and in case of empty expression.
    */
  def convertToCNF(expression: Expression): CNF =
    def convL(exp: Expression): CNFOr | Literal = exp match
      case Or(l, r) => CNFOr(convL(l), convL(r))
      case Symbol(v) => CNFSymbol(Variable(v))
      case Not(Symbol(v)) => CNFNot(CNFSymbol(Variable(v)))
      case _ => throw new Exception("Expression is not convertible to CNF form")
    def convR(exp: Expression): CNFAnd | CNFOr | Literal = exp match
      case And(l, r) => CNFAnd(convL(l), convR(r))
      case Or(l, r) => CNFOr(convL(l), convL(r))
      case Symbol(v) => CNFSymbol(Variable(v))
      case Not(Symbol(v)) => CNFNot(CNFSymbol(Variable(v)))
      case _ => throw new Exception("Expression is not convertible to CNF form")
    expression match
      case Symbol(v) => CNFSymbol(Variable(v))
      case Not(Symbol(v)) => CNFNot(CNFSymbol(Variable(v)))
      case And(l, r) => CNFAnd(convL(l), convR(r))
      case Or(l, r) => CNFOr(convL(l), convL(r))
      case _ => throw new Exception("Expression is not convertible to CNF form")
