package satify.model.dsl

import DSL.*
import satify.model.Expression
import satify.model.Expression.*

object SatEncodings:

  private def requireVariables(vars: Seq[Symbol], minimum: Int, method: String): Unit =
    require(vars.length >= minimum, s"$method encoding requires at least two variables")

  private def removeDuplicates(vars: Seq[Symbol]): Seq[Symbol] = vars.distinct

  /** Encodes the constraint that at least one of the given variables is true.
    * It is implemented concatenating the expressions with the OR operator.
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atLeastOne(variables: Symbol*): Expression =
    val vars: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(vars, 2, "atLeastOne")
    vars.reduceLeft(_ or _)

  /** Encodes the constraint that at most one of the given variables is true.
    * It uses the sequential encoding that produces 3n − 4 clauses (O(n) complexity).
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atMostOne(variables: Symbol*): Expression =
    val vars = removeDuplicates(variables)
    requireVariables(vars, 2, "atMostOne")
    val generator: () => Symbol = symbolGenerator("ENC")
    // removing duplicates
    // generate new n - 1 variables
    val newVars = (1 until vars.length).map(_ => generator()).toList
    val startingClauses = (not(vars.head) or newVars.head) and (not(vars.last) or newVars.last)
    val middleClauses = for (x, i) <- vars.zipWithIndex if i > 0 && i < vars.length - 1
    yield (not(x) or newVars(i)) and
      (not(newVars(i - 1)) or newVars(i)) and
      (not(x) or not(newVars(i - 1)))
    middleClauses.foldLeft(startingClauses)(_ and _)

  /** Encodes the constraint that exactly one of the given variables is true.
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def exactlyOne(variables: Symbol*): Expression =
    val vars: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(vars, 2, "exactlyOne")
    atLeastOne(vars: _*) and atMostOne(vars: _*)

  /** Encodes the constraint that at most k of the given variables are true.
    * It is implemented using sequential encoding that produces 2nk + n − 3k − 1 clauses (O(n) complexity).
    * @param k the number of variables that must be true
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atMostK(k: Int)(variables: Symbol*): Expression =
    val vars: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(vars, 2, "atMostK")
    require(k <= vars.length, "atMostK encoding requires k <= n")

    Symbol("TODO")

  /** Encodes the constraint that at least k of the given variables are true.
    * It is implemented using the pairwise encoding that produces O(n&#94;2) clauses.
    * @param k the number of variables that must be true
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atLeastK(k: Int)(variables: Symbol*): Expression =
    val vars: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(vars, 2, "atLeastK")
    require(k <= vars.length, "atLeastK encoding requires k <= n")
    def combinations(vars: Seq[Symbol], k: Int): Seq[Seq[Expression]] =
      if (k == 0) Seq(Seq())
      else if (k == 1) vars.map(Seq(_))
      else
        for
          i <- vars.indices
          subCombination <- combinations(vars.drop(i + 1), k - 1)
        yield vars(i) +: subCombination

    combinations(vars, k).map(_.reduceLeft(_ and _)).reduceLeft(_ or _)

  /** Converts a tuple to a list of [[Symbol]]
    * @param tuple the input [[Tuple]]
    * @return the list of [[Symbol]]
    */
  private def tupleToSymbols(tuple: Tuple): List[Symbol] =
    tuple.productIterator.toList.map(_.toString).map(Symbol.apply)

  extension (expressions: Tuple)

    /** Calls [[atMostOne]] if k is 1, [[atMostK]] otherwise.
      * @see [[atMostOne]] and [[atMostK]]
      */
    def atMost(k: Int): Expression = k match
      case 1 => atMostOne(tupleToSymbols(expressions): _*)
      case _ => atMostK(k)(tupleToSymbols(expressions): _*)

    /** Calls [[atLeastOne]] if k is 1, [[atLeastK]] otherwise.
      * @see [[atLeastOne]] and [[atLeastK]]
      */
    def atLeast(k: Int): Expression = k match
      case 1 => atLeastOne(tupleToSymbols(expressions): _*)
      case _ => atLeastK(k)(tupleToSymbols(expressions): _*)
