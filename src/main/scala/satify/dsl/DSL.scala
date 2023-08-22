package satify.dsl

import satify.model.Expression
import satify.model.Expression.*

import scala.annotation.targetName

object DSL:

  given Conversion[String, Symbol] with
    def apply(s: String): Symbol = Symbol(s)

  /** Encodes the constraint that at least one of the given variables is true.
    * It is implemented concatenating the expressions with the OR operator.
    * @param variables the input variables
    * @return the expression that encodes the constraint
    */
  def atLeastOne(variables: Expression*): Expression = variables.reduceLeft(_ or _)

  /** Encodes the constraint that at most one of the given variables is true.
    * It uses the sequential encoding that produces 3n − 4 clauses (O(n) complexity).
    * @param variables the input variables
    * @return the expression that encodes the constraint
    */
  def atMostOne(variables: Symbol*): Expression =
    val generator = symbolGenerator("ENC")
    // removing duplicates
    val vars = variables.distinct
    // generate new n - 1 variables
    val newVars = (1 until vars.length).map(_ => generator()).toList
    val startingClauses = (not(vars.head) or newVars.head) and (not(vars.last) or newVars.last)
    val middleClauses = for (x, i) <- vars.zipWithIndex if i > 0 && i < vars.length - 1
    yield (not(x) or newVars(i)) and
      (not(newVars(i - 1)) or newVars(i)) and
      (not(x) or not(newVars(i - 1)))
    middleClauses.foldLeft(startingClauses)(_ and _)

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
