package model

trait State:
  /** A general expression of boolean logic, it is the adapted user input */
  type Expression

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
