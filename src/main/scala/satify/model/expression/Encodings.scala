package satify.model.expression

import satify.model.expression.Expression.*
import satify.model.expression.Utils.symbolProducer

object Encodings:

  private def symbolGenerator(prefix: String): () => Symbol = symbolProducer(prefix)
  private val encVarProducer: () => Symbol = symbolGenerator("ENC")

  private def requireVariables(vars: Seq[Symbol], minimum: Int, method: String): Unit =
    require(vars.length >= minimum, s"$method encoding requires at least two variables")

  private def removeDuplicates(vars: Seq[Symbol]): Seq[Symbol] = vars.distinct

  /** Encodes the constraint that exactly one of the given variables is true.
   * @param variables the input variables
   * @return the [[Expression]] that encodes the constraint
   */
  def exactlyOne(variables: Symbol*): Expression =
    val vars: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(vars, 1, "exactlyOne")
    And(atLeastOne(vars: _*), atMostOne(vars: _*))

  /** Encodes the constraint that at least one of the given variables is true.
   * It is implemented concatenating the expressions with the OR operator.
   * @param variables the input variables
   * @return the [[Expression]] that encodes the constraint
   */
  def atLeastOne(variables: Symbol*): Expression =
    val vars: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(vars, 1, "atLeastOne")
    vars.reduceLeft(Or(_, _))

  /** Encodes the constraint that at least k of the given variables are true.
   * It is implemented using the pairwise encoding that produces O(n&#94;2) clauses.
   * @param k         the number of variables that must be true
   * @param variables the input variables
   * @return the [[Expression]] that encodes the constraint
   */
  def atLeastK(k: Int)(variables: Symbol*): Expression =
    val vars: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(vars, 1, "atLeastK")
    require(k <= vars.length, "atLeastK encoding requires k <= n")

    def combinations(vars: Seq[Symbol], k: Int): Seq[Seq[Expression]] =
      if (k == 0) Seq(Seq())
      else if (k == 1) vars.map(Seq(_))
      else
        for
          i <- vars.indices
          subCombination <- combinations(vars.drop(i + 1), k - 1)
        yield vars(i) +: subCombination

    combinations(vars, k).map(_.reduceLeft(And(_, _))).reduceLeft(Or(_, _))

  /** Encodes the constraint that at most one of the given variables is true.
   * @param variables the input variables
   * @return the [[Expression]] that encodes the constraint
   * @see [[atMostK]]
   */
  def atMostOne(variables: Symbol*): Expression = atMostK(1)(variables: _*)

  /** Encodes the constraint that at most k of the given variables are true. <br>
   * It is implemented using sequential encoding that produces 2nk + 2n − 3k + 1 clauses (O(n) complexity). <br>
   * Set of mathematical clauses:<br>
   * 1) (¬s1,j) for 1 < j <= k <br>
   * 2) (¬xi ∨ si,1) for 1 < i < n<br>
   * 3) (¬si−1,1 ∨ si,1) for 1 < i < n<br>
   * 4) (¬xi ∨ ¬si−1,j−1 ∨ si,j) for 1 < i < n and for 1 < j <= k<br>
   * 5) (¬si−1,j ∨ si,j) for 1 < i < n and for 1 < j <= k<br>
   * 6) (¬xi ∨ ¬si−1,k) for 1 < i < n
   * @param k         the number of variables that must be true
   * @param variables the input variables
   * @return the [[Expression]] that encodes the constraint
   */
  def atMostK(k: Int)(variables: Symbol*): Expression =
    val X: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(X, 1, "atMostK")
    val n = X.length
    require(k <= n, "atMostK encoding requires k <= n")

    val S: Seq[Seq[Symbol]] = (1 until n).map(_ => (1 to k).map(_ => encVarProducer())).toList
    // (¬s1,j) for 1 < j <= k
    val clauses1: Seq[Expression] = (1 until k).map(j => Not(S.head(j)))
    // (¬xi ∨ si,1) for 1 < i < n
    val clauses2: Seq[Expression] = for i <- 0 to n - 2 yield Or(Not(X(i)), S(i).head)
    // (¬si−1,1 ∨ si,1) for 1 < i < n
    val clauses3: Seq[Expression] = for i <- 1 to n - 2 yield Or(Not(S(i - 1).head), S(i).head)
    // (¬xi ∨ ¬si−1,j−1 ∨ si,j) for 1 < i < n and for 1 < j <= k
    val clauses4: Seq[Expression] = for
      i <- 1 to n - 2
      j <- 1 until k
    yield Or(Or(Not(X(i)), Not(S(i - 1)(j - 1))), S(i)(j))
    // (¬si−1,j ∨ si,j) for 1 < i < n and for 1 < j <= k
    val clauses5: Seq[Expression] = for
      i <- 1 to n - 2
      j <- 1 until k
    yield Or(Not(S(i - 1)(j)), S(i)(j))
    // (¬xi ∨ ¬si−1,k) for 1 < i < n
    val clauses6: Seq[Expression] = for i <- 1 until n yield Or(Not(X(i)), Not(S(i - 1).last))
    (clauses1 ++ clauses2 ++ clauses3 ++ clauses4 ++ clauses5 ++ clauses6) reduceRight (And(_, _))
