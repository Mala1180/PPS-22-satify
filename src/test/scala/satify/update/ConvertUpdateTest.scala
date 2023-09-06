package satify.update

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.State
import satify.model.problems.ProblemChoice
import satify.model.problems.ProblemChoice.NQueens
import satify.update.Message.*
import satify.update.Update.update
import satify.view.GUI.inputTextArea

class ConvertUpdateTest extends AnyFlatSpec with Matchers:

  val currentState: State = State()
  val validExpression: String = "a and b"
  val invalidExpression: String = "a ad b"

  "When Convert message containing a valid expression is sent to the update component it" should "return an updated State" in {
    val newState = update(currentState, Convert(validExpression))
    import newState.*
    input.isDefined
    && expression.isDefined
    && solution.isEmpty
    && cnf.isDefined
    && problem.isEmpty
    && error.isEmpty shouldBe true
  }

  "When Convert message containing an invalid expression is sent to the update component it" should "return a defined Error" in {
    val newState = update(currentState, Convert(invalidExpression))
    import newState.*
    input.isDefined
    && expression.isEmpty
    && solution.isEmpty
    && cnf.isEmpty
    && problem.isEmpty
    && error.isDefined shouldBe true
  }
