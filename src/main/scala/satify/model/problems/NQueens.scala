package satify.model.problems

import satify.model.Assignment
import satify.model.expression.Encodings.{atLeastOne, atMostOne}
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.expression.SymbolGeneration.{SymbolGenerator, encodingVarPrefix}

import scala.annotation.tailrec

case class NQueens(n: Int) extends Problem:

  given SymbolGenerator with
    def prefix: String = encodingVarPrefix

  private val variables: Seq[Seq[Symbol]] =
    if n <= 0 then throw new IllegalArgumentException("n must be positive")
    else
      for i <- 0 until n
      yield for j <- 0 until n
      yield Symbol(s"x_${i}_$j")

  /** At least one queen on each row and column */
  private val atLeastOneQueenRowsColumns: Expression =
    val constraint =
      for i <- 0 until n
      yield And(atLeastOne(variables(i): _*), atLeastOne(variables.map(row => row(i)): _*))
    constraint.reduceLeft(And(_, _))

  /** At most one queen on each row and column */
  private val atMostOneQueenRowsColumns: Expression =
    val atMostConstraints =
      for i <- 0 until n
      yield And(atMostOne(variables(i): _*), atMostOne(variables.map(row => row(i)): _*))
    atMostConstraints.reduceLeft(And(_, _))

  /** At most one queen on each diagonal */
  private def atMostOneQueenDiagonals: Expression =
    val clauses =
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
    clauses.reduceLeft(And(_, _))

  override val constraints: Set[Expression] =
    if n > 1 then Set(atLeastOneQueenRowsColumns, atMostOneQueenRowsColumns, atMostOneQueenDiagonals)
    else Set(atLeastOneQueenRowsColumns, atMostOneQueenRowsColumns)

  override def toString(assignment: Assignment): String =
    @tailrec
    def getStringView(assignment: Assignment, acc: String): String = assignment match
      case Assignment(variables) =>
        val firstN = variables.take(n)
        if firstN.nonEmpty then
          getStringView(
            Assignment(variables.drop(n)),
            acc + "\n" + firstN.foldLeft("")((p, c) => p + (if c.value then s" ♕ " else " · "))
          )
        else acc
      case _ => acc
    getStringView(assignment, "")
