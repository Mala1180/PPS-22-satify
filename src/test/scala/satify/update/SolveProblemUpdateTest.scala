package satify.update

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Result.{SAT, UNSAT}
import satify.model.problems.{GraphColoring, NQueens, NurseScheduling}
import satify.model.{Solution, State}
import satify.update.Message.*
import satify.update.Update.update

class SolveProblemUpdateTest extends AnyFlatSpec with Matchers:

  val currentState: State = State()

  "The solving of NQueens" should "return an updated State" in {
    update(currentState, SolveProblem(NQueens(4))).solution should matchPattern { case Some(Solution(SAT, _, _)) =>
    }
  }

  "Scheduling of 2 nurses, 2 days and 3 shifts" should "be UNSAT" in {
    update(currentState, SolveProblem(NurseScheduling(2, 2, 3))).solution should matchPattern {
      case Some(Solution(UNSAT, _, _)) =>
    }
  }

  "Scheduling of 3 nurses, 1 days and 3 shifts" should "be SAT" in {
    update(currentState, SolveProblem(NurseScheduling(3, 1, 3))).solution should matchPattern {
      case Some(Solution(SAT, _, _)) =>
    }
  }

  "GraphColoring 3 linked nodes with 2 color and 1 auto-cycle" should "be UNSAT" in {
    val problem = GraphColoring(List(("n1", "n2"), ("n2", "n3"), ("n2", "n2")), List("n1", "n2", "n3"), 2)
    update(currentState, SolveProblem(problem)).solution should matchPattern { case Some(Solution(UNSAT, _, _)) =>
    }
  }

  "GraphColoring 3 linked nodes with 2 colors" should "be SAT" in {
    val problem = GraphColoring(List(("n1", "n2"), ("n2", "n3")), List("n1", "n2", "n3"), 2)
    update(currentState, SolveProblem(problem)).solution should matchPattern { case Some(Solution(SAT, _, _)) =>
    }
  }
