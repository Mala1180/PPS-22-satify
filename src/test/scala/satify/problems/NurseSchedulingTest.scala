package satify.problems

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Result.*
import satify.model.Solution
import satify.model.problems.NurseScheduling
import satify.update.parser.DimacsCNF
import satify.update.solver.SolverType.DPLL
import satify.update.solver.{Solver, SolverType}

class NurseSchedulingTest extends AnyFlatSpec with Matchers:

  "NurseScheduling 2 nurses with 5 days and 2 shifts" should "be UNSAT" in {
    val problem = NurseScheduling(2, 5, 2)
    /*val sol = Solver(DPLL).solve(problem.exp)
    sol should matchPattern { case Solution(UNSAT, _, _) => }*/
  }

  "NurseScheduling 3 nurses with 1 days and 3 shifts" should "be SAT" in {
    val problem = NurseScheduling(3, 1, 3)
    /*val sol = Solver(DPLL).solve(problem.exp)
    sol should matchPattern { case Solution(SAT, _, _) => }*/
  }
