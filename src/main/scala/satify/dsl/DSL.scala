package satify.dsl

import satify.model.Expression
import satify.model.Expression.*

import scala.annotation.targetName

object DSL:

  given Conversion[String, Symbol] with
    def apply(s: String): Symbol = Symbol(s)

  extension (exp: Expression)

    def and(exp2: Expression): Expression = And(exp, exp2)
    def or(exp2: Expression): Expression = Or(exp, exp2)
    def not: Expression = Not(exp)
    def xor(exp2: Expression): Expression = Or(And(exp, Not(exp2)), And(Not(exp), exp2))

    @targetName("andSymbol")
    def /\(exp2: Expression): Expression = and(exp2)
    @targetName("orSymbol")
    def \/(exp2: Expression): Expression = or(exp2)
    @targetName("notSymbol")
    def unary_! : Expression = not
    @targetName("xorSymbol")
    def ^(exp2: Expression): Expression = xor(exp2)


  import satify.dsl.SatEncodings.*

  private def tupleToSymbols(tuple: Tuple): List[Symbol] =
    tuple.productIterator.toList.map(_.toString).map(Symbol.apply)
  extension (expressions: Tuple)
    def atMost(k: Int): Expression = k match
      case 1 => atMostOne(tupleToSymbols(expressions): _*)
      case _ => atMostK(k)(tupleToSymbols(expressions): _*)

    def atLeast(k: Int): Expression = k match
      case 1 => atLeastOne(tupleToSymbols(expressions): _*)
      case _ => atLeastK(k)(tupleToSymbols(expressions): _*)
