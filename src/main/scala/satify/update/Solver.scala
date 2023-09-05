package satify.update

import satify.model.{Assignment, CNF, Solution, Variable}
import satify.model.Result.*
import satify.model.dpll.PartialModel
import satify.model.expression.Expression
import satify.update.converters.Converter
import satify.update.dpll.DPLL.*

/** Entity providing the necessary methods to solve the SAT problem. */
trait Solver:

  /** Solves a the SAT problem using the DPLL algorithm.
    * @param cnf the input expression in Conjunctive Normal Form
    * @return the solution
    */
  def solve(cnf: CNF): Solution

  /** Apply a CNF conversion to the input expression and then it is given in input to the DPLL algorithm.
    * @param exp the input expression
    * @return the solution
    */
  def solve(exp: Expression)(using converter: Converter): Solution

/** Companion object of the [[Solver]] trait providing a factory method. */
object Solver:
  def apply(): Solver = SolverImpl()

  /** Private implementation of [[Solver]]. */
  private case class SolverImpl() extends Solver:
    def solve(cnf: CNF): Solution =
      val s = extractSolutions(cnf)
      s match
        case _ if s.isEmpty => Solution(UNSAT)
        case _ => Solution(SAT, s.map(Assignment.apply).toList)

    def solve(exp: Expression)(using converter: Converter): Solution = solve(converter.convert(exp))
