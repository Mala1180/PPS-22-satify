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

  "Scheduling of 2 nurses, 2 days and 3 shifts" should "be UNSAT" in {
    val problem = NurseScheduling(2, 2, 3)
    val sol = Solver(DPLL).solve(problem.exp)
    sol should matchPattern { case Solution(UNSAT, _, _) => }
  }

  "Scheduling of 3 nurses, 1 days and 3 shifts" should "be SAT" in {
    val problem = NurseScheduling(3, 1, 3)
    val sol = Solver(DPLL).solve(problem.exp)
    sol should matchPattern { case Solution(SAT, _, _) => }
  }
