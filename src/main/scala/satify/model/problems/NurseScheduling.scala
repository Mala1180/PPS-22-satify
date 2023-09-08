package satify.model.problems
import satify.model.expression.Expression
import satify.model.expression.Expression.*

import scala.swing.{Component, FlowPanel}

case class NurseScheduling(nurses: Int, days: Int, shifts: Int) extends Problem:

  private val variables: Seq[Seq[Seq[Symbol]]] =
    for n <- 0 until nurses
    yield for d <- 0 until days
    yield for s <- 0 until shifts
    yield Symbol(s"n$n-d$d-s$s")

  private val oneNursePerShift: (String, Expression) =
    val constraints =
      for
        d <- 0 until days
        s <- 0 until shifts
      yield exactlyOne(variables(d)(s): _*)

    println(constraints)
    ("In each shift can work just one nurse", constraints.reduceLeft(And(_, _)))

  private val atMostOneShiftPerDay: (String, Expression) =
    val constraints = for
      n <- 0 until nurses
      d <- 0 until days
    yield atMostOne(variables(n)(d): _*)

    println(constraints)
    ("Each nurse can work no more than one shift per day", constraints.reduceLeft(And(_, _)))

  /** If possible, shifts should be distributed evenly and fairly, so that each nurse works the minimum amount of them. */
  private val minShiftsPerNurse: Int = (shifts * days) / nurses

  /** If this is not possible, because the total number of shifts is not divisible by the number of nurses,
    * some nurses will be assigned one more shift, without crossing the maximum number of shifts which can be worked by each nurse
    */
  private val maxShiftsPerNurse: Int = minShiftsPerNurse + 1

  private val minShiftsPerNurseConstraint: (String, Expression) =
    val constraints = atLeastK(minShiftsPerNurse)(variables.flatten.flatten: _*)
    ("Each nurse should work at least the minimum number of shifts", constraints)

  private val maxShiftsPerNurseConstraint: (String, Expression) =
    val constraints = atMostK(maxShiftsPerNurse)(variables.flatten.flatten: _*)
    ("Each nurse should work at most the maximum number of shifts", constraints)

  override def toString: String = s"NurseScheduling(nurses=$nurses, days=$days, shifts=$shifts)"
  override val constraints: Set[(String, Expression)] = Set(
    oneNursePerShift,
    atMostOneShiftPerDay,
    minShiftsPerNurseConstraint,
    maxShiftsPerNurseConstraint
  )
  override val exp: Expression = constraints.map(_._2).reduceLeft(And(_, _))
  override def getVisualization: Component = new FlowPanel()
  def s(): (String, Expression) = oneNursePerShift

@main def testNurse(): Unit =
  val prob = NurseScheduling(3, 2, 2)
  println(prob.s())
