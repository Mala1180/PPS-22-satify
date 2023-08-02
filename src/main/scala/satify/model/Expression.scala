package satify.model
trait Variable:
  val name: String

case class EmptyVariable(name: String) extends Variable
case class PartialVariable(name: String, value: Option[Boolean]) extends Variable
case class AssignedVariable(name: String, value: Boolean) extends Variable

enum Expression[T <: Variable]:
  case Symbol(value: T)
  case Not(branch: Expression[T])
  case And(right: Expression[T], left: Expression[T])
  case Or(right: Expression[T], left: Expression[T])

type EmptyExpression = Expression[EmptyVariable]
type PartialExpression = Expression[PartialVariable]
type AssignedExpression = Expression[AssignedVariable]

/** Object with methods to manipulate expressions */

object Expression:

  /** Zip the subexpressions found in the given expression with a generic type A.
   *
   * @param exp the expression.
   * @param f the supplier of the generic type A.
   * @return a list of the subexpressions found in the given expression zipped with the generic type.
   */
  def zipWith[T <: Variable, A](exp: Expression[T])(f: () => A): List[(A, Expression[T])] =
    def subexp(exp: Expression[T], list: List[(A, Expression[T])])
                             (f: () => A): List[(A, Expression[T])] = exp match
        case Symbol(_) => List()
        case e => (f(), exp) :: list ::: subop(e, list)(f)

    def subop(exp: Expression[T], list: List[(A, Expression[T])])
                            (f: () => A): List[(A, Expression[T])] = exp match
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
  def zipWithSymbol[T <: Variable](exp: Expression[T]): List[(Symbol[T], Expression[T])] =
    //TODO TO CHECK!
    var c = 0
    def freshLabel(): Symbol[T] =
      exp match
        case e: EmptyExpression if e.isInstanceOf[Expression[EmptyVariable]] =>
          val l: Symbol[EmptyVariable] = Symbol(EmptyVariable("X" + c))
          c = c + 1
          l
        case e: PartialExpression =>
          val l: Symbol[PartialVariable] = Symbol(PartialVariable("X" + c, Option.empty))
          c = c + 1
          l
        case e: AssignedExpression if e.isInstanceOf[Expression[AssignedVariable]] =>
          val l: Symbol[AssignedVariable] = Symbol(AssignedVariable("X" + c, false))
          c = c + 1
          l
        case And(_, _) | Or(_, _) | Not(_) | Symbol(_) => ???
    zipWith(exp)(freshLabel)

  /** Search for subexpressions in the given expression.
   *
   * @param exp the expression.
   * @return a list of the subexpressions found in the given expression.
   */
  def subexpressions[T <: Variable](exp: Expression[T]): List[Expression[T]] =
    zipWithSymbol(exp).map(_._2)

  /** Search if a subexpression is contained in the given expression.
   *
   * @param exp the expression.
   * @param subexp the subexpression to find.
   * @return true if the subexpression is contained in the expression, false otherwise.
   */
  def contains[T <: Variable](exp: Expression[T], subexp: Expression[T]): Boolean = subexpressions(exp).contains(subexp)

  /** Replace a subexpression of an expression with the given Symbol.
    *
    * @param exp the expression.
    * @param subexp the subexpression to replace.
    * @param s the Symbol inserted in place of the subexpression.
    * @return the expression with the subexpression replaced.
    */
  def replace[T <: Variable](exp: Expression[T], subexp: Expression[T], s: Symbol[T]): Expression[T] =
    def replaceExp(exp: Expression[T], subexp: Expression[T], s: Symbol[T]): Expression[T] =
      exp match
        case And(e1, e2) => And(replace(e1, subexp, s), replace(e2, subexp, s))
        case Or(e1, e2) => Or(replace(e1, subexp, s), replace(e2, subexp, s))
        case Not(e) => Not(replace(e, subexp, s))
        case Symbol(_) => exp
    exp match
    case _ if exp == subexp => s
    case and@And(_, _) => replaceExp(and, subexp, s)
    case or@Or(_, _) => replaceExp(or, subexp, s)
    case not@Not(_) => replaceExp(not, subexp, s)
    case _ => exp
