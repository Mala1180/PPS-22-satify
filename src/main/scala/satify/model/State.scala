package satify.model

/** The immutable entity representing the application state (Model). */
trait State:
  /** A general expression of boolean logic, it is the user input */
  type Expression
  val expression: Option[Expression]

  /** The input expression converted to Conjunctive Normal Form (CNF) */
  type CNF
  val cnf: Option[CNF]

  /** An entity containing the solution of the problem (SAT or UNSAT, optional Assignment), it is the output of DPLL */
  type Solution
  val solution: Option[Solution]

  /** An entity representing the problem to solve */
  type Problem
  val problem: Option[Problem]

/** Factory for [[satify.model.State]] instances. */
object State:
  /** Creates a new empty application state.
    * @return a new [[State]] instance.
    */
  def apply(): State = StateImpl()

  /** Creates a new application state with input expression and its CNF.
    * @param exp the input [[Expression]]
    * @param cnf the [[CNF]]
    * @return a new [[State]] instance.
    */
  def apply(exp: Expression, cnf: CNF): State = StateImpl(Some(exp), Some(cnf))

  /** Creates a new application state with the input expression, its CNF and the solution.
    * @param exp the input [[Expression]]
    * @param cnf the [[CNF]]
    * @param sol the [[Solution]]
    * @return a new [[State]] instance.
    */
  def apply(exp: Expression, cnf: CNF, sol: Solution): State = StateImpl(Some(exp), Some(cnf), Some(sol))

  /** Creates a new application state with an input problem, its CNF, and its solution.
    * @param cnf the [[CNF]]
    * @param sol the [[Solution]]
    * @param problem the [[Problem]] selected
    * @return a new [[State]] instance.
    */
  def apply(cnf: CNF, sol: Solution, problem: Problem): State =
    StateImpl(None, Some(cnf), Some(sol), Some(problem))

  private case class StateImpl(
      override val expression: Option[Expression] = None,
      override val cnf: Option[CNF] = None,
      override val solution: Option[Solution] = None,
      override val problem: Option[Problem] = None
  ) extends State:
    override type Expression = satify.model.Expression
    override type CNF = satify.model.CNF
    override type Solution = satify.model.Solution
    override type Problem = satify.model.Problem
