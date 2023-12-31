package satify.model.problems

import satify.model.Assignment
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.expression.SymbolGeneration.{SymbolGenerator, encodingVarPrefix}

case class NurseScheduling(nurses: Int, days: Int, shifts: Int) extends Problem:

  given SymbolGenerator with
    def prefix: String = encodingVarPrefix

  private val variables: Seq[Seq[Seq[Symbol]]] =
    require(nurses > 0 && days > 0 && shifts > 0, "Nurses, days and shifts must be positive")
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
      yield
        val vars = for n <- 0 until nurses yield variables(n)(d)(s)
        exactlyOne(vars: _*)
    constraint.reduceLeft(And(_, _))

  /** Each nurse can work no more than one shift per day */
  private val atMostOneShiftPerDay: Expression =
    val constraint = for
      n <- 0 until nurses
      d <- 0 until days
    yield atMostOne(variables(n)(d): _*)
    constraint.reduceLeft(And(_, _))

  /** If possible, shifts should be distributed evenly and fairly, so that each nurse works the minimum amount of them. */
  private val minShiftsPerNurse: Float = (shifts * days) / nurses.toFloat

  /** If this is not possible, because the total number of shifts is not divisible by the number of nurses,
    * some nurses will be assigned one more shift, without crossing the maximum number of shifts which can be worked by each nurse
    */
  private val maxShiftsPerNurse: Int =
    if minShiftsPerNurse.isWhole then minShiftsPerNurse.toInt
    else minShiftsPerNurse.toInt + 1

  /** Each nurse should work at least the minimum number of shifts */
  private val minShiftsPerNurseConstraint: Expression =
    val constraint =
      for n <- 0 until nurses
      yield atLeastK(minShiftsPerNurse.toInt)(variables(n).flatten: _*)
    constraint.reduceLeft(And(_, _))

  /** Each nurse should work at most the maximum number of shifts */
  private val maxShiftsPerNurseConstraint: Expression =
    val constraint =
      for n <- 0 until nurses
      yield atMostK(maxShiftsPerNurse)(variables(n).flatten: _*)
    constraint.reduceLeft(And(_, _))

  override def toString: String = s"NurseScheduling(nurses=$nurses, days=$days, shifts=$shifts)"
  override val constraints: Set[Expression] = Set(
    oneNursePerShift,
    atMostOneShiftPerDay,
    minShiftsPerNurseConstraint,
    maxShiftsPerNurseConstraint
  )

  override def toString(assignment: Assignment): String =
    var output = s"There are $shifts shifts per day\n" +
      "Min shifts per nurse " + minShiftsPerNurse + "\n" +
      "Max shifts per nurse " + maxShiftsPerNurse + "\n"

    assignment match
      case Assignment(variables) =>
        variables.filter(_.value).foreach { v =>
          val name = v.name
          val nurse = "Nurse-" + name.split("_d")(0).replace("n", "")
          val day = "day " + name.split("_s")(0).last
          val shift = name.split("_s")(1)
          output += s"$nurse works $day at the shift $shift\n"
        }
        output
      case _ => output
