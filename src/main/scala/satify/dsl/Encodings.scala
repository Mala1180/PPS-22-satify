package satify.dsl

import satify.dsl.DSL.*
import satify.model.Expression
import satify.model.Expression.*

object Encodings:

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
    val generator: () => Symbol = symbolGenerator("ENC")
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

  def atMostK(k: Int, variables: Symbol*): Expression = Expression.Symbol("TODO")

  def atLeastK(k: Int, variables: Symbol*): Expression =
    val vars = variables.distinct
    // make all combinations
    val clauses = for
      i <- vars.indices
      j <- i + 1 until vars.length
    yield vars(i) and vars(j)
    clauses.reduceLeft(_ or _)

  val one: Int = 1
  private def tupleToSymbols(tuple: Product): List[Symbol] =
    tuple.productIterator.toList.distinct.map(_.toString).map(sym => Symbol(sym))

  extension (expressions: Tuple)
    def atMost(k: Int): Expression =
      if k == 1 then atMostOne(tupleToSymbols(expressions): _*) else atMostK(k, tupleToSymbols(expressions): _*)

    def atLeast(k: Int): Expression =
      if k == 1 then atLeastOne(tupleToSymbols(expressions): _*) else atLeastK(k, tupleToSymbols(expressions): _*)
