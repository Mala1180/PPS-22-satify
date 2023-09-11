package satify.model.problems
import satify.model.expression.Expression
import satify.model.expression.Expression.*

import scala.swing.{Component, FlowPanel}

case class GraphColoring(edges: List[(String, String)], nodes: List[String], colors: Int) extends Problem:

  val variables: Seq[Seq[Symbol]] =
    for i <- nodes.indices yield for j <- 0 until colors yield Symbol(s"${nodes(i)}_c$j")

  /** Each node has exactly one color */
  private val nodeHasExactlyOneColor: Expression =
    val constraints = for i <- nodes.indices yield exactlyOne(variables(i): _*)
    constraints.reduceLeft(And(_, _))

  /** Each edge must have different colors in its vertices */
  private val linkedNodesHasDifferentColor: Expression =
    val constraints =
      for
        (i, j) <- edges
        k <- 0 until colors
      yield Or(Not(variables(nodes.indexOf(i))(k)), Not(variables(nodes.indexOf(j))(k)))
    constraints.reduceLeft(And(_, _))

  override val constraints: Set[Expression] = Set(nodeHasExactlyOneColor, linkedNodesHasDifferentColor)
  override def getVisualization: Component = new FlowPanel()
  override def toString: String =
    // print variables as a matrix
    val sb = new StringBuilder
    for i <- nodes.indices do
      for j <- 0 until colors do sb.append(s"${variables(i)(j)} ")
      sb.append("\n")
    sb.toString

@main def test(): Unit =
  val nodes = "node1" :: "node2" :: Nil
  val edges = (nodes.head, nodes.last) :: Nil
  val prob = GraphColoring(edges, nodes, 2)
  println(prob.exp.printAsDSL(false))
  println(prob.variables.head)
