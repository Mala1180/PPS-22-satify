package satify.model

enum Expression:
  case Symbol(value: String)
  case Not(branch: Expression)
  case And(right: Expression, left: Expression)
  case Or(right: Expression, left: Expression)


/** Object with methods to manipulate expressions */

object Expression:

  /** Zip the subexpressions found in the given expression with a generic type A.
    *
    * @param exp the expression.
    * @param f the supplier of the generic type A.
    * @return a list of the subexpressions found in the given expression zipped with the generic type.
    */
  def zipWith[A](exp: Expression)(f: () => A): List[(A, Expression)] =
    def subexp(exp: Expression, list: List[(A, Expression)])(f: () => A): List[(A, Expression)] = exp match
      case Symbol(_) => List()
      case e => (f(), exp) :: list ::: subop(e, list)(f)

    def subop(exp: Expression, list: List[(A, Expression)])(f: () => A): List[(A, Expression)] = exp match
      case And(exp1, exp2) => subexp(exp1, list)(f) ::: subexp(exp2, list)(f)
      case Or(exp1, exp2) => subexp(exp1, list)(f) ::: subexp(exp2, list)(f)
      case Not(exp) => subexp(exp, list)(f)
      case _ => subexp(exp, list)(f)
    exp match
      case Symbol(_) => List((f(), exp))
      case Not(Symbol(_)) => List((f(), exp))
      case _ => subexp(exp, List())(f)

  /** Zip the subexpressions found in the given expression with a Symbol.
    *
    * @param exp the expression.
    * @return a list of the subexpressions found in the given expression zipped with the Symbol.
    */
  def zipWithSymbol(exp: Expression): List[(Symbol, Expression)] =
    // TODO TO CHECK!!
    var c = 0
    def freshLabel(): Symbol =
      val s: Symbol = Symbol("X" + c)
      c += 1
      s
    zipWith(exp)(freshLabel)

  /** Search for subexpressions in the given expression.
    *
    * @param exp the expression.
    * @return a list of the subexpressions found in the given expression.
    */
  def subexpressions(exp: Expression): List[Expression] =
    zipWithSymbol(exp).map(_._2)

  /** Search if a subexpression is contained in the given expression.
    *
    * @param exp the expression.
    * @param subexp the subexpression to find.
    * @return true if the subexpression is contained in the expression, false otherwise.
    */
  def contains(exp: Expression, subexp: Expression): Boolean = subexpressions(exp).contains(subexp)

  /** Replace a subexpression of an expression with the given Symbol.
    *
    * @param exp the expression.
    * @param subexp the subexpression to replace.
    * @param s the Symbol inserted in place of the subexpression.
    * @return the expression with the subexpression replaced.
    */
  def replace(exp: Expression, subexp: Expression, s: Symbol): Expression =
    def replaceExp(exp: Expression, subexp: Expression, s: Symbol): Expression =
      exp match
        case And(e1, e2) => And(replace(e1, subexp, s), replace(e2, subexp, s))
        case Or(e1, e2) => Or(replace(e1, subexp, s), replace(e2, subexp, s))
        case Not(e) => Not(replace(e, subexp, s))
        case Symbol(_) => exp
    exp match
      case _ if exp == subexp => s
      case and @ And(_, _) => replaceExp(and, subexp, s)
      case or @ Or(_, _) => replaceExp(or, subexp, s)
      case not @ Not(_) => replaceExp(not, subexp, s)
      case _ => exp
