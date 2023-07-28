package update.converters

import model.{CNF, Expression, Variable}

/** Object containing the Tseitin transformation algorithm. */
object TseitinTransformation:

  /** Applies the Tseitin transformation to the given expression in order to convert it into CNF.
    * @param exp the expression to transform.
    * @return the CNF expression.
    */
  def tseitin[T <: Variable](exp: Expression[T]): CNF = ???
