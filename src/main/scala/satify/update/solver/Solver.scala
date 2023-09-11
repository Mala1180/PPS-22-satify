package satify.update.solver

import satify.model.Result.*
import satify.model.dpll.{DecisionTree, PartialModel}
import satify.model.expression.Expression
import satify.model.{Assignment, CNF, Solution}
import satify.update.converters.ConverterType.*
import satify.update.converters.{Converter, ConverterType}
import satify.update.solver.SolverType.*
import satify.update.solver.dpll.utils.DpllUtils.extractSolutions
import satify.update.solver.dpll.DpllOneSol.dpll

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
    private var prevSol: (Option[DecisionTree], Option[Set[PartialModel]]) = (None, None)

    override def solve(cnf: CNF): Solution = memoize(cnf =>
      dpll(cnf) match
        case (dt, Some(partialModel)) =>
          println("SAT")
          prevSol = (Some(dt), Some(Set(partialModel)))
          println(prevSol)
          Solution(SAT, List(Assignment(partialModel)))
        case (_, None) =>
          println("UNSAT")
          prevSol = (None, None)
          Solution(UNSAT, List.empty)
//      decisionTree = Some(sol._1)
//      Solution(sol._2, extractSolutions(decisionTree.get))
    )(cnf)

    override def solve(exp: Expression): Solution = solve(converter.convert(exp))

    override def next(): Assignment =
      println(prevSol)
      prevSol match
        case (Some(dt), Some(partialModelSet)) =>
          val sol: (DecisionTree, Option[PartialModel]) = dpll(dt, partialModelSet)
          sol match
            case (dt, Some(partialModel)) =>
              prevSol = (Some(dt), Some(prevSol._2.get + partialModel))
              Assignment(partialModel)
        case _ => throw IllegalStateException("No previous solution found")
