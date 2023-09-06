package satify.update.converters

import satify.model.CNF
import satify.model.expression.Expression

/** Functional interface providing a method to convert an expression into a CNF. */
trait Converter:

  /** Converts an expression into a CNF.
    * @param exp the expression to convert.
    * @return the CNF expression.
    */
  def convert(exp: Expression): CNF
