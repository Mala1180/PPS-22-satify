package satify.update

import satify.model.{Assignment, CNF, Solution, Variable}
import satify.model.Result.*
import satify.model.dpll.PartialModel
import satify.model.tree.Expression
import satify.update.converters.CNFConverter
import satify.update.dpll.DPLL.*

/** Entity providing the necessary methods to solve the SAT problem. */
trait Solver:

  /** Solves a the SAT problem using the DPLL algorithm.
    * @param cnf the input expression in Conjunctive Normal Form
    * @return the solution
    */
  def dpll(cnf: CNF): Solution

  /** Apply a CNF conversion to the input expression and then it is given in input to the DPLL algorithm.
    *
    * @param exp the input expression
    * @return the solution
    */
  def solve(exp: Expression)(using converter: CNFConverter): Solution

/** Companion object of the [[Solver]] trait providing a factory method. */
object Solver:
  def apply(): Solver = SolverImpl()

  /** Private implementation of [[Solver]]. */
  private case class SolverImpl() extends Solver:
    def dpll(cnf: CNF): Solution =
      val s = extractSolutions(cnf)
      s match
        case _ if s.isEmpty => Solution(UNSAT, None)
        case _ => Solution(SAT, Some(Assignment(s.head)))

    def solve(exp: Expression)(using converter: CNFConverter): Solution = dpll(converter.convert(exp))
