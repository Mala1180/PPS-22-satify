package satify.model.tree

import satify.model.tree.*
import satify.model.tree.Utils.symbolGenerator
import satify.model.tree.expression.{And, Expression, Not, Or}

object Encodings:

  private def requireVariables(vars: Seq[expression.Symbol], minimum: Int, method: String): Unit =
    require(vars.length >= minimum, s"$method encoding requires at least two variables")

  private def removeDuplicates(vars: Seq[expression.Symbol]): Seq[expression.Symbol] = vars.distinct

  /** Encodes the constraint that at least one of the given variables is true.
    * It is implemented concatenating the expressions with the OR operator.
    *
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atLeastOne(variables: expression.Symbol*): Expression =
    val vars: Seq[expression.Symbol] = removeDuplicates(variables)
    requireVariables(vars, 2, "atLeastOne")
    vars.reduceLeft(Or(_, _))

  /** Encodes the constraint that at most one of the given variables is true.
    * It uses the sequential encoding that produces 3n − 4 clauses (O(n) complexity).
    *
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atMostOne(variables: expression.Symbol*): Expression =
    val vars = removeDuplicates(variables)
    requireVariables(vars, 2, "atMostOne")
//    val generator: () => Symbol = symbolGenerator("ENC")
//    // removing duplicates
//    // generate new n - 1 variables
//    val newVars = (1 until vars.length).map(_ => generator()).toList
//    val startingClauses = And(Or(Not(vars.head), newVars.head), Or(Not(vars.last), newVars.last))
//    val middleClauses = for (x, i) <- vars.zipWithIndex if i > 0 && i < vars.length - 1
//    yield And(And(Or(Not(x), newVars(i)), Or(Not(newVars(i - 1)), newVars(i))), Or(Not(x), Not(newVars(i - 1))))
//    middleClauses.foldLeft(startingClauses)(And(_, _))
    // for each combinations of 2 variables, generate a clause that says that at least one of them is false
    val clauses = for
      i <- vars.indices
      j <- vars.indices
      if i < j
    yield Not(And(vars(i), vars(j)))
    clauses.reduceLeft(expression.And(_, _))

  /** Encodes the constraint that exactly one of the given variables is true.
    *
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def exactlyOne(variables: expression.Symbol*): Expression =
    val vars: Seq[expression.Symbol] = removeDuplicates(variables)
    requireVariables(vars, 2, "exactlyOne")
    And(atLeastOne(vars: _*), atMostOne(vars: _*))

  /** Encodes the constraint that at most k of the given variables are true.
    * It is implemented using sequential encoding that produces 2nk + n − 3k − 1 clauses (O(n) complexity).
    *
    * @param k         the number of variables that must be true
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atMostK(k: Int)(variables: expression.Symbol*): Expression =
    val vars: Seq[expression.Symbol] = removeDuplicates(variables)
    requireVariables(vars, 2, "atMostK")
    require(k <= vars.length, "atMostK encoding requires k <= n")

    expression.Symbol("TODO")

  /** Encodes the constraint that at least k of the given variables are true.
    * It is implemented using the pairwise encoding that produces O(n&#94;2) clauses.
    *
    * @param k         the number of variables that must be true
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atLeastK(k: Int)(variables: expression.Symbol*): Expression =
    val vars: Seq[expression.Symbol] = removeDuplicates(variables)
    requireVariables(vars, 2, "atLeastK")
    require(k <= vars.length, "atLeastK encoding requires k <= n")

    def combinations(vars: Seq[expression.Symbol], k: Int): Seq[Seq[Expression]] =
      if (k == 0) Seq(Seq())
      else if (k == 1) vars.map(Seq(_))
      else
        for
          i <- vars.indices
          subCombination <- combinations(vars.drop(i + 1), k - 1)
        yield vars(i) +: subCombination

    combinations(vars, k).map(_.reduceLeft(And(_, _))).reduceLeft(Or(_, _))

  extension (expressions: Seq[expression.Symbol])

    /** Calls [[atMostOne]] if k is 1, [[atMostK]] otherwise.
      * @see [[atMostOne]] and [[atMostK]]
      */
    def atMost(k: Int): Expression = k match
      case 1 => atMostOne(expressions: _*)
      case _ => atMostK(k)(expressions: _*)

    /** Calls [[atLeastOne]] if k is 1, [[atLeastK]] otherwise.
      * @see [[atLeastOne]] and [[atLeastK]]
      */
    def atLeast(k: Int): Expression = k match
      case 1 => atLeastOne(expressions: _*)
      case _ => atLeastK(k)(expressions: _*)
