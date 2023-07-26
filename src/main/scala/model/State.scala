package model

/** The immutable entity representing the application state (Model). */
trait State:
  /** A general expression of boolean logic, it is the user input */
  type Expression

  /** The input expression converted to Conjunctive Normal Form (CNF) */
  type CNF
  
  /** An entity containing the solution of the problem (SAT or UNSAT, optional Assignment), it is the output of DPLL */
  type Solution

  /** An entity representing the problem to solve */
  type Problem

/** Factory for [[model.State]] instances. */
object State:
  /** Creates a new application state.
    *
    * @return a new State instance.
    */
  def apply(): State = StateImpl()
  private case class StateImpl() extends State
