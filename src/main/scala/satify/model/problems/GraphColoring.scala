package satify.model.problems
import satify.model.expression.Expression
import satify.model.expression.Expression.*

import scala.swing.{Component, FlowPanel}

case class GraphColoring(edges: Int, nodes: Int, colors: Int) extends Problem:

  private val variables: Seq[Seq[Symbol]] = (0 until nodes).map(i => (0 until colors).map(j => Symbol(s"v$i$j")))

  private val vertexHasExactlyOneColor: (String, Expression) =
    // at least one color per node
    val constraints = for i <- 0 until nodes yield exactlyOne(variables(i): _*)
    ("Each vertex has exactly one color", constraints.reduceLeft(And(_, _)))


  "Each vertex can have at most one color"
//    val constraints = for i <- 0 until colors yield atLeastOne(variables(i): _*)
//    ("Each vertex has at least one color", constraints.reduceLeft(And(_, _)))

  override val constraints: Set[(String, Expression)] = Set()
  override val exp: Expression = Symbol("v")
  override def getVisualization: Component = new FlowPanel()
  override def toString: String =
    // print variables as a matrix
    val sb = new StringBuilder
    for i <- 0 until nodes do
      for j <- 0 until colors do
        sb.append(s"${variables(i)(j)} ")
      sb.append("\n")
    sb.toString

@main def test(): Unit =
  val prob = GraphColoring(3, 6, 3)
  println(prob.toString)
