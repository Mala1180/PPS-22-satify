package satify.model.expression

import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.Result.*
import satify.model.expression.Expression.*
import satify.model.expression.Utils.symbolProducer
import satify.model.{Assignment, CNF, Literal, Variable}
import satify.update.Solver
import satify.update.converters.CNFConverter
import satify.update.converters.TseitinTransformation.{convertToCNF, tseitin}

object Encodings:

  private def symbolGenerator(prefix: String): () => Symbol = symbolProducer(prefix)
  private val encVarProducer: () => Symbol = symbolGenerator("ENC")

  private def requireVariables(vars: Seq[Symbol], minimum: Int, method: String): Unit =
    require(vars.length >= minimum, s"$method encoding requires at least two variables")

  private def removeDuplicates(vars: Seq[Symbol]): Seq[Symbol] = vars.distinct

  /** Encodes the constraint that exactly one of the given variables is true.
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def exactlyOne(variables: Symbol*): Expression =
    val vars: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(vars, 2, "exactlyOne")
    And(atLeastOne(vars: _*), atMostOne(vars: _*))

  /** Encodes the constraint that at least one of the given variables is true.
    * It is implemented concatenating the expressions with the OR operator.
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atLeastOne(variables: Symbol*): Expression =
    val vars: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(vars, 2, "atLeastOne")
    vars.reduceLeft(Or(_, _))

  /** Encodes the constraint that at least k of the given variables are true.
    * It is implemented using the pairwise encoding that produces O(n&#94;2) clauses.
    * @param k         the number of variables that must be true
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

    combinations(vars, k).map(_.reduceLeft(And(_, _))).reduceLeft(Or(_, _))

  /** Encodes the constraint that at most one of the given variables is true. <br>
    * It uses the sequential encoding that produces 3n − 4 clauses (O(n) complexity).
    * @param variables the input variables
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atMostOne(variables: Symbol*): Expression =
    // removing duplicates
    val vars = removeDuplicates(variables)
    requireVariables(vars, 2, "atMostOne")
    // for each combinations of 2 variables, generate a clause that says that at least one of them is false
    val clauses = for
      i <- vars.indices
      j <- vars.indices
      if i < j
    yield Not(And(vars(i), vars(j)))
    val c = clauses.reduceLeft(And(_, _))
    val v: Symbol = encVarProducer()
    println(v)
    And(c, v)
//    atMostK(1)(variables: _*)

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
    * @return the [[satify.model.Expression]] that encodes the constraint
    */
  def atMostK(k: Int)(variables: Symbol*): Expression =
    val X: Seq[Symbol] = removeDuplicates(variables)
    requireVariables(X, 2, "atMostK")
    val n = X.length
    require(k <= n, "atMostK encoding requires k <= n")
//    val R: Seq[Seq[Symbol]] = (1 to n).map(_ => (1 to k).map(_ => encVarProducer())).toList
//    val clauses1 = for i <- 0 to n - 2 yield Or(Not(X(i)), R(i).head)
//    val clauses2 = for j <- 1 until k yield Not(R.head(j))
//    val clauses3 = for
//      i <- 1 to n - 2
//      j <- 0 until k
//    yield Or(Not(R(i - 1)(j)), R(i)(j))
//    val clauses4 = for
//      i <- 1 to n - 2
//      j <- 1 until k
//    yield Or(Or(Not(X(i)), Not(R(i - 1)(j - 1))), R(i)(j))
//    val clauses5 = for i <- 1 until n yield Or(Not(X(i)), R(i - 1).last)
//    val allClauses: Seq[Expression] = clauses1 ++ clauses2 ++ clauses3 ++ clauses4 ++ clauses5
//    allClauses.reduceLeft(And(_, _))

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
    val all: Seq[Expression] = clauses1 ++ clauses2 ++ clauses3 ++ clauses4 ++ clauses5 ++ clauses6
    all.zipWithIndex.foreach((c, i) => println(i.toString + "   " + c.toString))
    val allClauses: Expression = all.reduceRight(And(_, _))
    allClauses

@main def test1(): Unit =
  import Encodings.atMostK
  val exp: Expression = atMostK(3)(Symbol("x1"), Symbol("x2"), Symbol("x3"), Symbol("x4"))
  println(exp)
  println(exp.printAsDSL(false))
  val cnf = convertToCNF(exp)
  println(cnf)
  val sol = Solver().dpll(cnf)
//  given CNFConverter = exp => tseitin(exp)
//  val sol = Solver().solve(exp)
  println(sol.assignment.length)
  // filter from assignment of solve the variable that starts with ENC os TSTN
  println(sol.assignment)
