package satify.model.expression

enum Expression:
  case Symbol(value: String)
  case Not(branch: Expression)
  case And(left: Expression, right: Expression)
  case Or(left: Expression, right: Expression)

/** Object with methods to manipulate expressions */

object Expression:

  export satify.model.expression.Utils.*
  export satify.model.expression.Encodings.*

  extension (exp: Expression)

    /** Zip the subexpressions found in the given expression with a generic type A.
      * @param f the supplier of the generic type A.
      * @return a list of the subexpressions found in the given expression zipped with the generic type.
      */
    def zipWith[A](f: () => A): List[(A, Expression)] =
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

    /** Search for subexpressions in the given expression.
      * @return a list of the subexpressions found in the given expression.
      */
    def subexpressions: List[Expression] = zipWith(() => Int).map(_._2)

    /** Search if a subexpression is contained in the given expression.
      * @param exp    the expression.
      * @param subexp the subexpression to find.
      * @return true if the subexpression is contained in the expression, false otherwise.
      */
    def contains(subexp: Expression): Boolean = exp.subexpressions.contains(subexp)

    /** Count the number of clauses in the given expression.
      * @return the number of clauses in the given expression.
      */
    def clauses: Int = zipWith(() => Int).size

    /** Replace a subexpression of an expression with the given Symbol.
      * @param subexp the subexpression to replace.
      * @param s the Symbol inserted in place of the subexpression.
      * @return the expression with the subexpression replaced.
      */
    def replace(subexp: Expression, s: Symbol): Expression =
      def replaceExp(exp: Expression, subexp: Expression, s: Symbol): Expression =
        exp match
          case And(e1, e2) => And(e1.replace(subexp, s), e2.replace(subexp, s))
          case Or(e1, e2) => Or(e1.replace(subexp, s), e2.replace(subexp, s))
          case Not(e) => Not(e.replace(subexp, s))
          case Symbol(_) => exp
      exp match
        case _ if exp == subexp => s
        case and @ And(_, _) => replaceExp(and, subexp, s)
        case or @ Or(_, _) => replaceExp(or, subexp, s)
        case not @ Not(_) => replaceExp(not, subexp, s)
        case _ => exp
