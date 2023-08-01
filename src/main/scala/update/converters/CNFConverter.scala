package update.converters

import model.{CNF, Expression, Variable}

/** Functional interface for converting an expression into a CNF. */
trait CNFConverter:
  /** Converts an expression into a CNF.
    *
    * @param exp the expression to convert.
    * @return the CNF expression.
    */
  def convert[T <: Variable](exp: Expression[T]): CNF
