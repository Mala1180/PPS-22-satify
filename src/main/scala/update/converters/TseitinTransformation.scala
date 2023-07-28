package update.converters

object TseitinTransformation:
  import model.Expression.*
  import model.{Expression, Operator}
  import model.Operator.*

  /** Substitute Symbols of nested subexpressions in all others expressions
    * @param exp the expression where to substitute Symbols
    * @return the decomposed expression in subexpressions with Symbols correctly substituted.
    */
  def symbolsReplace(exp: Expression): List[(Literal, Expression)] =
    def replacer(list: List[(Literal, Expression)], subexp: Expression, l: Literal): List[(Literal, Expression)] =
      list match
        case Nil => Nil
        case (lit, e) :: t if contains(e, subexp) => (lit, replace(e, subexp, l)) :: replacer(t, subexp, l)
        case (lit, e) :: t => (lit, e) :: replacer(t, subexp, l)

    def symbolSelector(list: List[(Literal, Expression)]): List[(Literal, Expression)] = list match
      case Nil => Nil
      case (l, e) :: tail => (l, e) :: symbolSelector(replacer(tail, e, l))

    symbolSelector(zipWithLiteral(exp).reverse)

  /** Transform the Symbol and the corresponding expression to CNF form
    * @param exp a Symbol and the corresponding expression
    * @return a list of Symbol and expressions in CNF form for the given Symbol and expression
    */
  def transform(exp: (Literal, Expression)): List[(Literal, Expression)] =
    def not(exp: Expression): Expression = exp match
      case Clause(Not(lit)) => lit
      case e => Clause(Not(e))
    exp match
      case (l, Clause(Not(lit))) => List((l, Clause(Or(Clause(Not(lit)), Clause(Not(l))))), (l, Clause(Or(lit, l))))
      case (l, Clause(And(e1, e2))) =>
        List((l, Clause(Or(Clause(Or(not(e1), not(e2))), l))), (l, Clause(Or(e1, not(l)))), (l, Clause(Or(e2, not(l)))))
      case (l, Clause(Or(e1, e2))) =>
        List((l, Clause(Or(Clause(Or(e1, e2)), not(l)))), (l, Clause(Or(not(e1), l))), (l, Clause(Or(not(e2), l))))
      case _ => List()

  /** TODO Transform the expression to the CNF form
    * @param exp the expression to transform
    * @return the expression in CNF form
    */
  def toCNF(exp: Expression): List[(Literal, Expression)] =
    // TODO: to implement
    // expression = (a ∧ ¬ b) ∨ ¬ (c ∧ d)
    val sub = symbolsReplace(exp)
    println(sub)
    var list: List[(Literal, Expression)] = List()
    sub.foreach(s => list = transform(s) ::: list)
    list
