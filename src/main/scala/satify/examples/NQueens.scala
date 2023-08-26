package satify.examples

import satify.model.{Expression, Variable}
import satify.model.Expression.*
import satify.dsl.SatEncodings.*

trait Example:
  val exp: Expression

case class NQueens(n: Int) extends Example:

  private val variables: Seq[Seq[Symbol]] =
    for i <- 0 until n
    yield for j <- 0 until n
    yield Symbol(s"x_${i}_$j")

  // At least one on each row and column
  private val rowColConstr =
    for i <- 0 until n
    yield And(
      And(
        atLeastOne(variables(i): _*),
        atLeastOne(variables.map(row => row(i)): _*)
      ),
      And(
        atMostOne(variables(i): _*),
        atMostOne(variables.map(row => row(i)): _*)
      )
    )

  // Diagonal constraints
  private val diagConstr =
    for i <- 0 until (n - 1)
    yield
      val s: Seq[(Symbol, Symbol, Symbol, Symbol)] =
        for j <- 0 until (n - i)
        yield (
          variables(i + j)(j),
          variables(n - 1 - (i + j))(j),
          variables(i + j)(n - 1 - j),
          variables(n - 1 - (i + j))(n - 1 - j)
        )
      And(
        atMostOne(s.map((t, _, _, _) => t): _*),
        And(
          atMostOne(s.map((t, _, _, _) => t): _*),
          And(atMostOne(s.map((_, _, t, _) => t): _*), atMostOne(s.map((_, _, _, t) => t): _*))
        )
      )

  val exp: Expression = buildAnd(rowColConstr ++ diagConstr: _*)

private def buildAnd(exp: Expression*): Expression = exp match
  case _ if exp.size == 1 => exp(0)
  case _ if exp.nonEmpty => And(exp.head, buildAnd(exp.tail: _*))
