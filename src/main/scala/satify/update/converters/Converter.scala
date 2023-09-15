package satify.update.converters

import satify.model.cnf.CNF
import satify.model.expression.Expression
import satify.update.converters.ConverterType.*
import satify.update.converters.tseitin.TseitinTransformation.tseitin

import scala.collection.mutable

/** Interface for converting an expression into a CNF. */
trait Converter:

  /** Converts an expression into a CNF.
    * @param exp the expression to convert.
    * @return the CNF expression.
    */
  def convert(exp: Expression): CNF

  /** Memoize a function f: Expression => CNF to avoid recomputing the same CNF for the same expression.
    * @param f the function to memoize.
    * @return
    */
  protected def memoize(f: Expression => CNF): Expression => CNF =
    new mutable.HashMap[Expression, CNF]() {
      override def apply(key: Expression): CNF = getOrElseUpdate(key, f(key))
    }

/** Factory for [[Converter]] instances. */
object Converter:

  /** Creates a new CNF converter.
    * @param converterType the type of converter to create.
    * @return the converter.
    */
  def apply(converterType: ConverterType, cache: Boolean = true): Converter = converterType match
    case Tseitin => TseitinConverter(cache)

  /** Private implementation of [[Converter]] */
  private case class TseitinConverter(cache: Boolean = true) extends Converter:

    override def convert(exp: Expression): CNF = if cache then memoize(tseitin)(exp) else tseitin(exp)
