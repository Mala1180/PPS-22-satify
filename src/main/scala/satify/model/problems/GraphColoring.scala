package satify.model.problems
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.expression.SymbolGeneration.{SymbolGenerator, encodingVarPrefix}

import scala.swing.{Component, FlowPanel}

case class GraphColoring(edges: List[(String, String)], nodes: List[String], colors: Int) extends Problem:

  given SymbolGenerator with
    def prefix: String = encodingVarPrefix

  val variables: Seq[Seq[Symbol]] =
    for i <- nodes.indices yield for j <- 0 until colors yield Symbol(s"${nodes(i)}_c$j")

  /** Each node has exactly one color */
  private val nodeHasExactlyOneColor: Expression =
    val constraint = for i <- nodes.indices yield exactlyK(1)(variables(i): _*)
    constraint.reduceLeft(And(_, _))

  /** Each edge must have different colors in its vertices */
  private val linkedNodesHasDifferentColor: Expression =
    val constraint =
      for
        (i, j) <- edges
        k <- 0 until colors
      yield Or(Not(variables(nodes.indexOf(i))(k)), Not(variables(nodes.indexOf(j))(k)))
    constraint.reduceLeft(And(_, _))

  override val constraints: Set[Expression] = Set(nodeHasExactlyOneColor, linkedNodesHasDifferentColor)
  override def getVisualization: Component = new FlowPanel()
  override def toString: String =
    // print variables as a matrix
    val sb = new StringBuilder
    for i <- nodes.indices do
      for j <- 0 until colors do sb.append(s"${variables(i)(j)} ")
      sb.append("\n")
    sb.toString
