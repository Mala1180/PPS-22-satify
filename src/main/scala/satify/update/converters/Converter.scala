package satify.update.converters

import satify.model.CNF
import satify.model.expression.Expression
import satify.update.converters.TseitinTransformation.tseitin
import satify.update.converters.ConverterType.*


/** Functional interface for converting an expression into a CNF. */
trait Converter:
  /** Converts an expression into a CNF.
    * @param exp the expression to convert.
    * @return the CNF expression.
    */
  def convert(exp: Expression): CNF

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
    override def convert(exp: Expression): CNF = tseitin(exp)
