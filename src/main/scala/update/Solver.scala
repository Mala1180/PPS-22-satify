package update

import model.{CNF, Expression, Solution, Variable}
import update.converters.CNFConverter

/** Entity providing the necessary methods to solve the SAT problem. */
trait Solver:

  /** Solves a the SAT problem using the DPLL algorithm.
    * @param cnf the input expression in Conjunctive Normal Form
    * @return the solution
    */
  def dpll(cnf: CNF): Solution

  /** Apply the Tseitin transformation and the DPLL algorithm to solve the SAT problem.
    * @param exp the input expression
    * @return the solution
    */
  def solve[T <: Variable](exp: Expression[T])(using converter: CNFConverter): Solution

/** Companion object of the [[Solver]] trait providing a factory method. */
object Solver:
  def apply(): Solver = SolverImpl()

  /** Private implementation of the Solver trait. */
  private case class SolverImpl() extends Solver:
    def dpll(cnf: CNF): Solution = ???
    def solve[T <: Variable](exp: Expression[T])(using converter: CNFConverter): Solution = dpll(converter.convert(exp))
