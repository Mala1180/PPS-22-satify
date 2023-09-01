package satify.update.converters

import satify.model.tree.Expression
import satify.model.tree.Expression.zipWithSymbol
import satify.model.{CNF, Literal, Variable}
import satify.model.tree.{And, Not, Or, Symbol}
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree.*
import satify.model.tree.TreeTraversableGiven.given_Traversable_Tree
import satify.model.tree.TraversableOps.*
import satify.model.tree.Expression.map

/** Object containing the Tseitin transformation algorithm. */
object TseitinTransformation:

  import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
  import satify.model.Literal

  /** Applies the Tseitin transformation to the gt iven expression in order to convert it into CNF.
    * @param exp the expression to transform.
    * @return the CNF expression.
    */
  def tseitin(exp: Expression): CNF =
    var transformations: List[(CNFSymbol, CNF)] = List()
    symbolsReplace(exp).foreach(s => transformations = transform(s) ::: transformations)
    var transformedExp = transformations.map(_._2)
    if transformedExp.size == 1 then transformedExp.head
    else
      transformedExp = transformedExp.prepended(CNFSymbol(Variable("X0")))
      transformedExp.reduceRight((s1, s2) =>
        CNFAnd(s1.asInstanceOf[CNFOr | Literal], s2.asInstanceOf[CNFAnd | CNFOr | Literal])
      )

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
        case (lit, e) :: t if e.tree.contains(subexp.tree) =>
          (lit, map(e, {
            case tt if tt == subexp => l
            case tt => tt
          })) :: replace(t, subexp, l)
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
  def transform(exp: (Symbol, Expression)): List[(CNFSymbol, CNF)] =
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
