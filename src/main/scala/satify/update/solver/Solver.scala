package satify.update.solver

import satify.model.Result.*
import satify.model.cnf.CNF
import satify.model.expression.Expression
import satify.model.{Assignment, Solution}
import satify.update.converters.ConverterType.*
import satify.update.converters.{Converter, ConverterType}
import satify.update.solver.SolverType.*
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions

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

  protected def memoize(f: CNF => Solution): CNF => Solution =
    new collection.mutable.HashMap[CNF, Solution]():
      override def apply(key: CNF): Solution = getOrElseUpdate(key, f(key))

/** Factory for [[Solver]] instances. */
object Solver:

  /** Creates a solver using the given type of algorithm
    * @param algorithmType the type of the algorithm
    * @return the Solver
    * @see [[SolverType]]
    */
  def apply(algorithmType: SolverType, conversionType: ConverterType = Tseitin): Solver =
    algorithmType match
      case DPLL => DpllAlgorithm(Converter(conversionType))

  /** Private implementation of [[Solver]] */
  private case class DpllAlgorithm(converter: Converter) extends Solver:
    override def solve(cnf: CNF): Solution = memoize(cnf =>
      val s = extractSolutions(cnf)
      s match
        case _ if s.isEmpty => Solution(UNSAT)
        case _ => Solution(SAT, s.map(Assignment.apply).toList)
    )(cnf)

    override def solve(exp: Expression): Solution = solve(converter.convert(exp))
