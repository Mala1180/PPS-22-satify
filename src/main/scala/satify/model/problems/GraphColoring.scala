package satify.model.problems
import satify.model.Assignment
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.expression.SymbolGeneration.{SymbolGenerator, encodingVarPrefix}


case class GraphColoring(edges: List[(String, String)], nodes: List[String], colors: Int) extends Problem:

  given SymbolGenerator with
    def prefix: String = encodingVarPrefix

  val variables: Seq[Seq[Symbol]] =
    edges.foreach(e => require(nodes.contains(e._1) && nodes.contains(e._2)))
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

  override def toString(assignment: Assignment): String =
    var output = ""
    assignment match
      case Assignment(variables) =>
        variables.filter(_.value).foreach { v =>
          val name = v.name
          val color = "color-" + name.split("_c")(1)
          val node = name.split("_c")(0)
          output += s"$node -> $color\n"
        }
        output
      case _ => output
