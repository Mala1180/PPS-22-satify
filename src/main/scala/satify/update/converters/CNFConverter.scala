package satify.update.converters

import satify.model.expression.Expression
import satify.model.{CNF, Variable}

/** Functional interface for converting an expression into a CNF. */
trait CNFConverter:
  /** Converts an expression into a CNF.
    *
    * @param exp the expression to convert.
    * @return the CNF expression.
    */
  def convert(exp: Expression): CNF
