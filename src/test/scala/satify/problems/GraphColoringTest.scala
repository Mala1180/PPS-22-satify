package satify.problems

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Result.*
import satify.model.Solution
import satify.model.problems.GraphColoring
import satify.update.parser.DimacsCNF
import satify.update.solver.SolverType.DPLL
import satify.update.solver.{Solver, SolverType}

class GraphColoringTest extends AnyFlatSpec with Matchers:

  "GraphColoring 3 linked nodes with 1 color" should "be UNSAT" in {
    // note auto-cycle
    val problem = GraphColoring(List(("n1", "n2"), ("n2", "n3"), ("n2", "n2")), List("n1", "n2", "n3"), 2)
    val sol = Solver(DPLL).solve(problem.exp)
    sol should matchPattern { case Solution(UNSAT, _, _) => }
  }

  "GraphColoring 3 linked nodes with 2 colors" should "be SAT" in {
    val problem = GraphColoring(List(("n1", "n2"), ("n2", "n3")), List("n1", "n2", "n3"), 2)
    val sol = Solver(DPLL).solve(problem.exp)
    sol should matchPattern { case Solution(SAT, _, _) => }
  }
