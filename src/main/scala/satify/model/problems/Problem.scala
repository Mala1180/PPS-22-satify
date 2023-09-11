package satify.model.problems

import satify.model.expression.Expression
import satify.model.expression.Expression.And

import scala.swing.Component

/** Entity representing a SAT problem. */
trait Problem:
  /** The set of problem's constraints, with a description. */
  val constraints: Set[Expression]

  /** The expression encoded of the problem that must be satisfied. */
  val exp: Expression = constraints.reduceLeft(And(_, _))

  override def toString: String
  def getVisualization: Component

//enum ProblemChoice:
//  case GraphColoring
//  case NQueens
//  case NurseScheduling

//enum Problem:
//  case NQueens(n: Int)
//  case NurseScheduling(edges: Int, nodes: Int, colors: Int)
//  case GraphColoring(graph: Map[Int, Set[Int]], colors: Int)
