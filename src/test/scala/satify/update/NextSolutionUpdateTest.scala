package satify.update

/*import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.State
import satify.update.Message.*
import satify.update.Update.update

class NextSolutionUpdateTest extends AnyFlatSpec with Matchers:

    "When NextSolution message is sent to the update component it" should "return an updated State changing first solution assignment" in {
    val multipleAssignmentsExpression: String = "a or b"
    val currentState: State = update(State(), Solve(multipleAssignmentsExpression))
    println(currentState)
    val newState = update(currentState, NextSolution)
    println(newState)
    currentState.solution.get.assignment.head shouldNot be(newState.solution.get.assignment.head)
  }


  "When NextSolution message is sent but only one assigment is possible it" should "return the same solution assignment" in {
    val singleAssignmentsExpression: String = "a and b"
    val currentState: State = update(State(), Solve(singleAssignmentsExpression))
    val newState = update(currentState, NextSolution)
    println(newState)
    currentState.solution.get.assignment.head should be(newState.solution.get.assignment.head)
  }

  "When NextSolution message is sent but no solution are present it" should "return an Error" in {
    val newState = update(State(), NextSolution())
    import newState.*
    input.isEmpty
    && expression.isEmpty
    && solution.isEmpty
    && cnf.isEmpty
    && problem.isEmpty
    && error.isDefined shouldBe true
  }

  */
