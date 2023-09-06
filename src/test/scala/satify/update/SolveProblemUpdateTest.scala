package satify.update

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.State
import satify.model.problems.ProblemChoice
import satify.model.problems.ProblemChoice.NQueens
import satify.update.Message.*
import satify.update.Update.update
import satify.view.GUI.inputTextArea

class SolveProblemUpdateTest extends AnyFlatSpec with Matchers:

  val currentState: State = State()
  val choice: ProblemChoice = NQueens
  val parameter: Int = 4

  "When SolveProblem message containing a valid choice is sent to the update component it" should "return an updated State" in {
    val newState = update(currentState, SolveProblem(choice, parameter))
    import newState.*
    input.isEmpty
    && expression.isEmpty
    && solution.isDefined
    && cnf.isDefined
    && problem.isDefined
    && error.isEmpty shouldBe true
  }

  "When SolveProblem message containing an invalid choice is sent to the update component it" should "return a defined Error" in {
    val newState = update(currentState, SolveProblem(choice, -2))
    import newState.*
    input.isEmpty
    && expression.isEmpty
    && solution.isEmpty
    && cnf.isEmpty
    && problem.isEmpty
    && error.isDefined shouldBe true
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
