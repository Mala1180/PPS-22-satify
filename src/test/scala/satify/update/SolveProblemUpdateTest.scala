package satify.update

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.State
import satify.model.errors.Error.InvalidInput
import satify.model.problems.{NQueens, Problem}
import satify.update.Message.*
import satify.update.Update.update

class SolveProblemUpdateTest extends AnyFlatSpec with Matchers:

  val currentState: State = State()
  val parameter: Int = 4

  "When SolveProblem message containing a valid choice is sent to the update component it" should "return an updated State" in {
    val problem: Problem = NQueens(4)
    val newState = update(currentState, SolveProblem(problem))
    import newState.*
    input.isEmpty
    && expression.isEmpty
    && solution.isDefined
    && cnf.isDefined
    && error.isEmpty shouldBe true
  }

  "When SolveProblem message containing an invalid choice is sent to the update component it" should "return a defined Error" in {
    val problem: Problem = NQueens(-2)
    update(currentState, SolveProblem(problem)) shouldBe State(InvalidInput())
  }

/*  "When Solve message containing a invalid expression is sent to the update component it" should "return a defined error" in {
    val newState = update(currentState, Solve(invalidExpression))
    import newState.*
    input.isDefined
      && expression.isEmpty
      && solution.isEmpty
      && cnf.isEmpty
      && problem.isEmpty
      && error.isDefined shouldBe true
  }
 */
