package satify.update.solver

import satify.model.cnf.CNF
import satify.model.expression.Expression
import satify.model.{Assignment, Solution}
import satify.update.converters.ConverterType.*
import satify.update.converters.{Converter, ConverterType}
import satify.update.solver.DpllSolverMemoize.dpllEnumerate
import satify.update.solver.SolverType.*
import satify.update.solver.dpll.impl.DpllEnumerator.enumerate
import satify.update.solver.dpll.impl.DpllFinder.{find, findNext}

import scala.collection.mutable

/** Entity providing the methods to solve the SAT problem.
  * @see [[satify.update.solver.DPLL]]
  */
trait Solver:

  /** Solves the SAT problem, returning a solution with all satisfiable assignments.
    * @param cnf the input in conjunctive normal form
    * @return the solution of the SAT problem
    */
  def solveAll(cnf: CNF): Solution

  /** Solves the SAT problem, returning a solution with all satisfiable assignments.
    * @param exp the input expression
    * @return the solution of the SAT problem
    */
  def solveAll(exp: Expression): Solution

  /** Solves the SAT problem returning a solution with the first satisfiable assignment found.
    * @param cnf the input in conjunctive normal form
    * @return the solution of the SAT problem
    */
  def solve(cnf: CNF): Solution

  /** Solves the SAT problem returning a solution with the first satisfiable assignment found.
    * The input expression is converted in CNF and then is given in input to the resolution algorithm.
    * @param exp the input expression
    * @return the solution of the SAT problem
    */
  def solve(exp: Expression): Solution

  /** Finds the next assignment of the previous solution, if any.
    * @return a filled assignment if there is another satisfiable, an empty one otherwise.
    * @throws IllegalStateException if a previous run was not found
    */
  def next(): Option[Assignment]

/** Factory for [[Solver]] instances. */
object Solver:

  /** Creates a solver using the given type of algorithm
    * @param algorithmType the type of the algorithm
    * @return the Solver
    * @see [[SolverType]]
    */
  def apply(algorithmType: SolverType, conversionType: ConverterType = Tseitin): Solver =
    algorithmType match
      case DPLL => DpllSolver(Converter(conversionType))

  /** Private implementation of [[Solver]] */
  private case class DpllSolver(converter: Converter) extends Solver:

    override def solveAll(cnf: CNF): Solution = dpllEnumerate(cnf)

    override def solveAll(exp: Expression): Solution = solveAll(converter.convert(exp))

    override def solve(cnf: CNF): Solution = find(cnf)

    override def solve(exp: Expression): Solution = solve(converter.convert(exp))

    override def next(): Option[Assignment] = findNext()

object DpllSolverMemoize:

  val dpllEnumerate: CNF => Solution = memoize(cnf => enumerate(cnf))

  protected def memoize(f: CNF => Solution): CNF => Solution =
    new collection.mutable.HashMap[CNF, Solution]():
      override def apply(key: CNF): Solution = getOrElseUpdate(key, f(key))
