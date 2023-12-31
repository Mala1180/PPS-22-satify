package satify.update.converters

import satify.model.cnf.CNF
import satify.model.expression.Expression
import satify.update.converters.ConverterType.*
import satify.update.converters.TseitinMemoization.cachedConversion
import satify.update.converters.tseitin.TseitinTransformation.tseitin

import scala.collection.mutable

/** Interface for converting an expression into a CNF. */
trait Converter:

  /** Converts an expression into a CNF.
    * @param exp the expression to convert.
    * @return the CNF expression.
    */
  def convert(exp: Expression, cache: Boolean = true): CNF

/** Factory for [[Converter]] instances. */
object Converter:

  /** Creates a new CNF converter.
    * @param converterType the type of converter to create.
    * @return the converter.
    */
  def apply(converterType: ConverterType): Converter = converterType match
    case Tseitin => TseitinConverter()

  /** Private implementation of [[Converter]] */
  private case class TseitinConverter() extends Converter:
    override def convert(exp: Expression, cache: Boolean = true): CNF =
      if cache then cachedConversion(exp) else tseitin(exp)

object TseitinMemoization:

  val cachedConversion: Expression => CNF = memoize(tseitin)

  /** Memoize a function f: Expression => CNF to avoid recomputing the same CNF for the same expression.
    * @param f the function to memoize.
    * @return
    */
  private def memoize(f: Expression => CNF): Expression => CNF =
    new mutable.HashMap[Expression, CNF]() {
      override def apply(key: Expression): CNF = getOrElseUpdate(key, f(key))
    }
