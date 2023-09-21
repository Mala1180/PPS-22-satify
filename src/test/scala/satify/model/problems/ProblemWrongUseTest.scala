package satify.model.problems

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Result.*
import satify.model.Solution
import satify.update.solver.SolverType.DPLL
import satify.update.solver.{Solver, SolverType}

class ProblemWrongUseTest extends AnyFlatSpec with Matchers:
  
  "NQueens(-2)" should "throw IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      NQueens(-2)
    }
  }
  
  "NQueens(0)" should "throw IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      NQueens(0)
    }
  }
