package satify.model.problems

import satify.model.Assignment
import satify.model.expression.Expression
import satify.model.expression.Expression.And

/** Entity representing a SAT problem. */
trait Problem:

  /** The set of problem's constraints, with a description. */
  val constraints: Set[Expression] = Set()

  /** The expression encoded of the problem that must be satisfied. */
  def exp: Expression = constraints.reduceLeft(And(_, _))

  def toString(assignment: Assignment): String
