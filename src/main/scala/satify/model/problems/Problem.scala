package satify.model.problems

import satify.model.expression.Expression

trait Problem:
  val exp: Expression
  val constraints: Set[(String, Expression)]
  def toString: String





//enum ProblemChoice:
//  case GraphColoring
//  case NQueens
//  case NurseScheduling


//enum Problem:
//  case NQueens(n: Int)
//  case NurseScheduling(edges: Int, nodes: Int, colors: Int)
//  case GraphColoring(graph: Map[Int, Set[Int]], colors: Int)
