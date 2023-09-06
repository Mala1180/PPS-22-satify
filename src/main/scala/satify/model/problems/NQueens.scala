package satify.model.problems

import satify.model.Variable
import satify.model.expression.Expression
import satify.model.expression.Expression.{And, Symbol}
import satify.model.dpll.OrderedSeq.{given_Ordering_Variable, seq}
import satify.model.dpll.PartialModel
import satify.model.expression.Encodings.{atLeastOne, atMostOne}

import scala.annotation.tailrec

case class NQueens(n: Int) extends Problem:

  private val variables: Seq[Seq[Symbol]] =
    if n < 0 then throw new IllegalArgumentException("n must be positive")
    else
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
    if n < 0 then throw new IllegalArgumentException("n must be positive")
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
          atMostOne(s.map((_, t, _, _) => t): _*),
          And(atMostOne(s.map((_, _, t, _) => t): _*), atMostOne(s.map((_, _, _, t) => t): _*))
        )
      )

  val exp: Expression = buildAnd(rowColConstr ++ diagConstr: _*)

object NQueens:
  def printNQueensFromDimacs(n: Int, pm: PartialModel): Unit =
    val mapPm: PartialModel = seq(pm.map {
      case Variable(s"x_$i", value) =>
        val xx = i.toInt - 1
        val row: Int = xx / n
        val col: Int = xx % n
        Variable(s"x_${row}_$col", value)
      case v => v
    }: _*)
    printNqueens(n, mapPm)

  @tailrec
  final def printNqueens(n: Int, pm: PartialModel): Unit =
    val firstN = pm.take(n)
    if firstN.nonEmpty then
      println(
        firstN.foldLeft("")((p, c) =>
          p + (if c.value.isDefined then if c.value.get then s" ♕ " else " · "
               else " ")
        )
      )
      printNqueens(n, pm.drop(n))

def buildAnd(exp: Expression*): Expression = exp.reduceLeft(And(_, _))
