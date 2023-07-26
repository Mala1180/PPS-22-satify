package tseitin

object Transformation:
  import model.Expression
  import model.Expression.*

  def replaceVariables(exp: Expression) : List[(Literal, Expression)] =
    def tailReplace(list: List[(Literal, Expression)], subExp: Expression, l: Literal) : List[(Literal, Expression)] = list match
      case Nil => Nil
      case (lit, e) :: tail if contains(e, subExp) => (lit, replace(e, subExp, l)) :: tailReplace(tail, subExp, l)
      case (lit, e) :: tail => (lit, e) :: tailReplace(tail, subExp, l)

    def replacer(list: List[(Literal, Expression)]) : List[(Literal, Expression)] = list match
      case Nil => Nil
      case (l, e) :: tail =>
        val newTail = tailReplace(tail, e, l)
        (l, e) :: replacer(newTail)

    replacer(zipWithLiteral(exp).reverse)
