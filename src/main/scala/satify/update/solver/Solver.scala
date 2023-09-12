package satify.update.solver

import satify.model.Result.*
import satify.model.cnf.CNF
import satify.model.dpll.{DecisionTree, PartialModel}
import satify.model.expression.Expression
import satify.model.{Assignment, Solution}
import satify.update.converters.ConverterType.*
import satify.update.converters.{Converter, ConverterType}
import satify.update.solver.SolverType.*
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions
import satify.update.solver.dpll.DpllOneSol.dpll

import scala.collection.mutable

/** Entity providing the methods to solve the SAT problem.
  * @see [[satify.update.solver.DPLL]]
  */
trait Solver:

  /** Solves the SAT problem.
    * @param cnf the input in conjunctive normal form
    * @return the solution to the SAT problem
    */
  def solve(cnf: CNF): Solution

  /** Solves the SAT problem.
    * @param exp the input expression
    * @return the solution to the SAT problem
    */
  def solve(exp: Expression): Solution

  /** Finds the next assignment of the previous solution, if any.
    * @return the solution to the SAT problem
    * @throws IllegalStateException if the previous solution was not found
    */
  def next(): Assignment

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

    import DpllSolverMemoize.runDpll

    override def solve(cnf: CNF): Solution = runDpll(cnf)

    override def solve(exp: Expression): Solution = solve(converter.convert(exp))

    override def next(): Assignment = dpll()

object DpllSolverMemoize:

  val runDpll: CNF => Solution = memoize(cnf => dpll(cnf))

  protected def memoize(f: CNF => Solution): CNF => Solution =
    new collection.mutable.HashMap[CNF, Solution]():
      override def apply(key: CNF): Solution = getOrElseUpdate(key, f(key))
