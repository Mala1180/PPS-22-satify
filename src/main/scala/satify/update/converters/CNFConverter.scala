package satify.update.converters

import satify.model.tree.expression.Expression
import satify.model.tree.cnf.{CNF, Variable}

/** Functional interface for converting an expression into a CNF. */
trait CNFConverter:
  /** Converts an expression into a CNF.
    *
    * @param exp the expression to convert.
    * @return the CNF expression.
    */
  def convert(exp: Expression): CNF
