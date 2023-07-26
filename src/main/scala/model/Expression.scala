package model

import model.{EmptyVariable, PartialVariable}

trait Variable
case class EmptyVariable(name: String) extends Variable
case class PartialVariable(name: String, value: Option[Boolean]) extends Variable
case class AssignedVariable(name: String, value: Boolean) extends Variable

enum Operation[T <: Variable]:
  case And(exp1: Expression[T], exp2: Expression[T])
  case Or(exp1: Expression[T], exp2: Expression[T])
  case Not(exp: Expression[T])

enum Expression[T <: Variable]:
  case Literal(v: T)
  case Clause(op: Operation[T])

type EmptyModel = Expression[EmptyVariable]
type PartialModel = Expression[PartialVariable]

/** Object with methods to manipulate expressions */

object Expression:
  def contains[T <: Variable](exp1: T, exp2: T): Boolean = ???
