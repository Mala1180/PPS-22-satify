package satify.model.problems

import satify.model.Variable
import satify.model.dpll.OrderedSeq.{given_Ordering_Variable, seq}
import satify.model.dpll.PartialModel
import satify.model.expression.Encodings.{atLeastOne, atMostOne}
import satify.model.expression.Expression
import satify.model.expression.Expression.{And, Symbol}

import scala.annotation.tailrec
import scala.swing.{Component, FlowPanel}

case class NQueens(n: Int) extends Problem:

  private val variables: Seq[Seq[Symbol]] =
    if n < 0 then throw new IllegalArgumentException("n must be positive")
    else
      for i <- 0 until n
      yield for j <- 0 until n
      yield Symbol(s"x_${i}_$j")

  /** At least one queen on each row and column */
  private val atLeastOneQueen: Expression =
    val constraint =
      for i <- 0 until n
      yield And(atLeastOne(variables(i): _*), atLeastOne(variables.map(row => row(i)): _*))
    constraint.reduceLeft(And(_, _))

  /** At most one queen on each row and column */
  private val atMostOneQueen: Expression =
    val atMostConstraints =
      for i <- 0 until n
      yield And(atMostOne(variables(i): _*), atMostOne(variables.map(row => row(i)): _*))
    atMostConstraints.reduceLeft(And(_, _))

  /** At most one queen on each diagonal */
  private val diagConstr: Expression =
    if n < 0 then throw new IllegalArgumentException("n must be positive")
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

  override val constraints: Set[Expression] = Set(atLeastOneQueen, atMostOneQueen, diagConstr)
  override def toString: String =
    ??? // variables.map(_.map(_ => if c.value.isDefined then if c.value.get then s" ♕ " else " · "
  override def getVisualization: Component = new FlowPanel()

object NQueens:

  extension (problem: NQueens)
    def printNQueensFromDimacs(pm: PartialModel): Unit =
      val mapPm: PartialModel = seq(pm.map {
        case Variable(s"x_$i", value) =>
          val xx = i.toInt - 1
          val row: Int = xx / problem.n
          val col: Int = xx % problem.n
          Variable(s"x_${row}_$col", value)
        case v => v
      }: _*)
      problem.printNqueens(mapPm)

    @tailrec
    def printNqueens(pm: PartialModel): Unit =
      val firstN = pm.take(problem.n)
      if firstN.nonEmpty then
        println(
          firstN.foldLeft("")((p, c) => p + (if c.value.isDefined then if c.value.get then s" ♕ " else " · " else " "))
        )
        problem.printNqueens(pm.drop(problem.n))
