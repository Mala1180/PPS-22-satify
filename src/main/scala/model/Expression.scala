package model

enum Operator:
  case And(exp1: Expression, exp2: Expression)
  case Or(exp1: Expression, exp2: Expression)
  case Not(exp: Expression)

enum Expression:
  case Literal(name: String)
  case Clause(op: Operator)

object Expression:
  import Expression.*
  import Operator.*

  /** Zip the subexpressions found in the given expression with a generic type A.
   *
   * @param exp the expression.
   * @param f the supplier of the generic type A.
   * @return a list of the subexpressions found in the given expression zipped with the generic type.
   */
  def zipWith[A](exp: Expression)(f: () => A): List[(A, Expression)] =
    def subexp(exp: Expression, list: List[(A, Expression)])(f: () => A): List[(A, Expression)] = exp match
      case Clause(op) => (f(), exp) :: list ::: subop(op, list)(f)
      case _ => List()

    def subop(op: Operator, list: List[(A, Expression)])(f: () => A): List[(A, Expression)] = op match
      case And(exp1, exp2) => subexp(exp1, list)(f) ::: subexp(exp2, list)(f)
      case Or(exp1, exp2) => subexp(exp1, list)(f) ::: subexp(exp2, list)(f)
      case Not(exp) => subexp(exp, list)(f)

    exp match
      case Literal(x) => List((f(), exp))
      case Clause(Not(Literal(x))) => List((f(), exp))
      case _ => subexp(exp, List())(f)

  /** Zip the subexpressions found in the given expression with a Literal.
   *
   * @param exp the expression.
   * @return a list of the subexpressions found in the given expression zipped with the Literal.
   */
  def zipWithLiteral(exp: Expression): List[(Literal, Expression)] =
    var c = 0
    def freshLabel(): Literal =
      val l: Literal = Literal("X" + c)
      c = c + 1
      l
    zipWith(exp)(freshLabel)

  /** Search for subexpressions in the given expression.
   *
   * @param exp the expression.
   * @return a list of the subexpressions found in the given expression.
   */
  def subexpressions(exp: Expression): List[Expression] =
    zipWithLiteral(exp).map(_._2)

  /** Search if a subexpression is contained in the given expression.
   *
   * @param exp the expression.
   * @param subexp the subexpression to find.
   * @return true if the subexpression is contained in the expression, false otherwise.
   */
  def contains(exp: Expression, subexp: Expression): Boolean = subexpressions(exp).contains(subexp)

  /** Replace the subexp with the given literal in the given expression.
    *
    * @param exp the expression.
    * @param subexp the subexpression to replace.
    * @param l the literal inserted in place of the subexpression.
    * @return the expression with the subexpression replaced.
    */
  def replace(exp: Expression, subexp: Expression, l: Literal): Expression = exp match
    case Clause(op) if Clause(op) == subexp => l
    case Clause(And(e1, e2)) => replaceOp(And(e1, e2), subexp, l)
    case Clause(Or(e1, e2)) => replaceOp(Or(e1, e2), subexp, l)
    case Clause(Not(e)) => replaceOp(Not(e), subexp, l)
    case _ => exp
    private def replaceOp(op: Operator, subexp: Expression, l: Literal): Expression = op match
      case And(e1, e2) => Clause(And(replace(e1, subexp, l), replace(e2, subexp, l)))
      case Or(e1, e2) => Clause(Or(replace(e1, subexp, l), replace(e2, subexp, l)))
      case Not(e) => Clause(Not(replace(e, subexp, l)))

  /** Print the given list of expressions.
   *
   * @param list the list of expressions to print.
   */
  def printExpressions(list: List[Expression]): Unit =
    list.foreach(clause => println(clause))
