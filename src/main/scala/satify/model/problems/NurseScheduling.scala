package satify.model.problems
import satify.model.expression.Expression
import satify.model.expression.Expression.*

import scala.swing.{Component, FlowPanel}

case class NurseScheduling(nurses: Int, days: Int, shifts: Int) extends Problem:

  given IncrementalSymbolGenerator with
    override def prefix: String = "ENC"
    
  private val variables: Seq[Seq[Seq[Symbol]]] =
    for n <- 0 until nurses
    yield for d <- 0 until days
    yield for s <- 0 until shifts
    yield Symbol(s"n${n}_d${d}_s$s")

  /** In each shift can work just one nurse */
  private val oneNursePerShift: Expression =
    val constraint =
      for
        d <- 0 until days
        s <- 0 until shifts
      yield exactlyOne(variables(d)(s): _*)

    println(constraint)
    constraint.reduceLeft(And(_, _))

  /** Each nurse can work no more than one shift per day */
  private val atMostOneShiftPerDay: Expression =
    val constraint = for
      n <- 0 until nurses
      d <- 0 until days
    yield atMostOne(variables(n)(d): _*)

    println(constraint)
    constraint.reduceLeft(And(_, _))

  /** If possible, shifts should be distributed evenly and fairly, so that each nurse works the minimum amount of them. */
  private val minShiftsPerNurse: Int = (shifts * days) / nurses

  /** If this is not possible, because the total number of shifts is not divisible by the number of nurses,
    * some nurses will be assigned one more shift, without crossing the maximum number of shifts which can be worked by each nurse
    */
  private val maxShiftsPerNurse: Int = minShiftsPerNurse + 1

  /** Each nurse should work at least the minimum number of shifts */
  private val minShiftsPerNurseConstraint: Expression = atLeastK(minShiftsPerNurse)(variables.flatten.flatten: _*)

  /** Each nurse should work at most the maximum number of shifts */
  private val maxShiftsPerNurseConstraint: Expression = atMostK(maxShiftsPerNurse)(variables.flatten.flatten: _*)

  override def toString: String = s"NurseScheduling(nurses=$nurses, days=$days, shifts=$shifts)"
  override val constraints: Set[Expression] = Set(
    oneNursePerShift,
    atMostOneShiftPerDay,
    minShiftsPerNurseConstraint,
    maxShiftsPerNurseConstraint
  )
  override def getVisualization: Component = new FlowPanel()
