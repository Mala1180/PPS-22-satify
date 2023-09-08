package satify.model.problems
import satify.model.expression.Expression
import satify.model.expression.Expression.*

import scala.swing.{Component, FlowPanel}

case class GraphColoring(edges: List[(String, String)], nodes: List[String], colors: Int) extends Problem:

  private val variables: Seq[Seq[Symbol]] = nodes.indices.map(i => (0 until colors).map(j => Symbol(s"v${i}_$j")))

  private val vertexHasExactlyOneColor: (String, Expression) =
    val constraints = for i <- nodes.indices yield exactlyOne(variables(i): _*)
    ("Each vertex has exactly one color", constraints.reduceLeft(And(_, _)))

  private val linkedNodesHasDifferentColor: (String, Expression) =
    val constraints =
      for
        (i, j) <- edges
        k <- 0 until colors
      yield Or(Not(variables(nodes.indexOf(i))(k)), Not(variables(nodes.indexOf(j))(k)))
    ("Each edge must have different colors in its vertices", constraints.reduceLeft(And(_, _)))

  override val constraints: Set[(String, Expression)] = Set(vertexHasExactlyOneColor, linkedNodesHasDifferentColor)
  override val exp: Expression = constraints.map(_._2).reduceLeft(And(_, _))
  override def getVisualization: Component = new FlowPanel()
  override def toString: String =
    // print variables as a matrix
    val sb = new StringBuilder
    for i <- nodes.indices do
      for j <- 0 until colors do sb.append(s"${variables(i)(j)} ")
      sb.append("\n")
    sb.toString

@main def test(): Unit =
  val nodes = "node1" :: "node2" :: "node3" :: "node4" :: "node5" :: "node6" :: Nil
  val edges = (nodes.head, nodes.last) :: (nodes.head, nodes(1)) :: (nodes.head, nodes(2)) :: Nil
  val prob = GraphColoring(edges, nodes, 3)
  println(prob.exp.printAsDSL(false))
