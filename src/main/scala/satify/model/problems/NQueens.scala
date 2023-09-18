package satify.model.problems

import satify.model.expression.Encodings.{atLeastOne, atMostOne}
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.expression.SymbolGeneration.{SymbolGenerator, encodingVarPrefix}
import satify.model.{Assignment, Variable}
import satify.view.ComponentUtils.createOutputTextArea
import satify.view.Constants.problemOutputDialogName

import scala.annotation.tailrec
import scala.swing.{BoxPanel, Component, FlowPanel, Orientation}

case class NQueens(n: Int) extends Problem:

  given SymbolGenerator with
    def prefix: String = encodingVarPrefix

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
  def toString(assignment: Assignment): String =
    @tailrec
    def getStringView(assignment: Assignment, acc: String): String = assignment match
      case Assignment(variables) =>
        val firstN = variables.take(n)
        if firstN.nonEmpty then
          getStringView(Assignment(variables.drop(n)), acc + "\n" + firstN.foldLeft("")((p, c) => p + (if c.value then s" ♕ " else " · ")))
        else acc
    getStringView(assignment, "")

object NQueens:

  extension (problem: NQueens)

    def printNQueens(assignment: Assignment): Unit = assignment match
      case Assignment(variables) =>
        val firstN = variables.take(problem.n)
        if firstN.nonEmpty then
          println(
            firstN.foldLeft("")((p, c) => p + (if c.value then s" ♕ " else " · "))
          )

  import satify.model.dpll.OrderedList.given

    @tailrec
    def printNQueensFromDimacs(n: Int, assignment: Assignment): Unit = assignment match
      case Assignment(variables) =>
        val firstN = variables.take(n)
        if firstN.nonEmpty then
          println(
            firstN
              .map {
                case Variable(s"x_$i", value) =>
                  val xx = i.toInt - 1
                  val row: Int = xx / n
                  val col: Int = xx % n
                  Variable(s"x_${row}_$col", value)
                case v => v
              }
              .foldLeft("")((p, c) => p + (if c.value then s" ♕ " else " · "))
          )
          printNQueensFromDimacs(n, Assignment(variables.drop(n)))
