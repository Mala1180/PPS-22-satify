package tseitin

object Transformation:
  import model.Expression
  import model.Expression.*
  import model.Operator
  import model.Operator.*

  /**
   * Substitute Symbols of nested subexpressions in all others expression
   * @param exp the expression where to substitute variables
   * @return the decomposed expression in subexpressions with Symbols correctly substituted.
   */
  def substitutionVariables(exp: Expression) : List[(Literal, Expression)] =
    def tailReplace(list: List[(Literal, Expression)], subexp: Expression, l: Literal) : List[(Literal, Expression)] = list match
      case Nil => Nil
      case (lit, e) :: t if contains(e, subexp) => (lit, replace(e, subexp, l)) :: tailReplace(t, subexp, l)
      case (lit, e) :: t => (lit, e) :: tailReplace(t, subexp, l)

    def selector(list: List[(Literal, Expression)]) : List[(Literal, Expression)] = list match
      case Nil => Nil
      case (l, e) :: tail => (l, e) :: selector(tailReplace(tail, e, l))

    selector(zipWithLiteral(exp).reverse)


  /**
   * Transform the literal and the corresponding subexpression to the CNF form
   *
   * @param exp a literal and the corresponding subexpression
   * @return a list of literals and expressions in CNF form
   */
  def transform(exp: (Literal, Expression)): List[(Literal, Expression)] =
    def not(exp: Expression) : Expression = exp match
      case Clause(Not(lit)) => lit
      case e => Clause(Not(e))
    def tNot(exp2: (Literal, Expression)) : List[(Literal, Expression)] = exp2 match
      case (l, Clause(Not(lit))) => List((l, Clause(Or(Clause(Not(lit)), Clause(Not(l))))), (l, Clause(Or(lit, l))))
      case _ => Nil
    def tAnd(exp2: (Literal, Expression)): List[(Literal, Expression)] = exp2 match
      case (l, Clause(And(e1, e2))) => List((l, Clause(Or(Clause(Or(not(e1), not(e2))), l))),
                                            (l, Clause(Or(e1, not(l)))),
                                            (l, Clause(Or(e2, not(l)))))
      case _ => Nil
    def tOr(exp2: (Literal, Expression)): List[(Literal, Expression)] = exp2 match
      case (l, Clause(Or(e1, e2))) => List((l, Clause(Or(Clause(Or(e1, e2)), not(l)))),
                                           (l, Clause(Or(not(e1), l))),
                                           (l, Clause(Or(not(e2), l))))
      case _ => Nil

    exp match
      case (l, Clause(Not(lit))) => tNot(exp)
      case (l, Clause(And(_, _))) => tAnd(exp)
      case (l, Clause(Or(_, _))) => tOr(exp)
      case _ => List()


  /**
    * Transform the expression to the CNF form
    *
    * @param exp the expression to transform
    * @return the expression in CNF form
    */
  def toCNF(exp: Expression): List[(Literal, Expression)] = ???
  // expression = (a ∧ ¬ b) ∨ ¬ (c ∧ d)
    val sub = substitutionVariables(exp)
    println(sub)
    var list : List[(Literal, Expression)] = List()
    /*sub.foreach(subs => {
      list = transform(subs) :: list
    })*/
    sub.foreach(s => list = transform(s) ::: list)
    list