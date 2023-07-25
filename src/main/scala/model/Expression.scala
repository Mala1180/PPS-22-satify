package model

enum Operation:
  case And(exp1: Expression, exp2: Expression)
  case Or(exp1: Expression, exp2: Expression)
  case Not(exp: Expression)
  case Xor(exp1: Expression, exp2: Expression)

enum Expression:
  case Literal(name: String)
  case Clause(op: Operation)

/** Object with methods to manipulate expressions */
object Expression:
  def contains(exp1: Expression, exp2: Expression): Boolean = ???
