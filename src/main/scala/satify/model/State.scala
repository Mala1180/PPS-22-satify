package satify.model

/** The immutable entity representing the application state (Model). */
trait State:
  /** A general expression of boolean logic, it is the user input */
  type Expression
  val expression: Option[Expression] = None

  /** The input expression converted to Conjunctive Normal Form (CNF) */
  type CNF
  val cnf: Option[CNF] = None

  /** An entity containing the solution of the problem (SAT or UNSAT, optional Assignment), it is the output of DPLL */
  type Solution
  val solution: Option[Solution] = None

  /** An entity representing the problem to solve */
  type Problem
  val problem: Option[Problem] = None

/** Factory for [[satify.model.State]] instances. */
object State:
  /** Creates a new application state.
    *
    * @return a new State instance.
    */
  def apply(): State = StateImpl()

  /** Creates a new application state with the given CNF.
    * @param cnf the CNF to set
    * @return a new State instance.
    */
  def apply(exp: Expression, cnf: CNF): State = StateImpl(Some(exp), Some(cnf))
  private case class StateImpl(
      override val expression: Option[Expression] = None,
      override val cnf: Option[CNF] = None,
      override val solution: Option[Solution] = None,
      override val problem: Option[Unit] = None
  ) extends State:
    override type Expression = satify.model.Expression
    override type CNF = satify.model.CNF
    override type Solution = satify.model.Solution
    override type Problem = Unit
