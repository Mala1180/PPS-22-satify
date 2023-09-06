package satify.update

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.State
import satify.update.Update.update
import satify.update.Message.Solve

class SolveUpdateTest extends AnyFlatSpec with Matchers:

  val currentState: State = State()
  val validExpression: String = "a and b"
  val invalidExpression: String = "a ad b"

  "When Solve message containing a valid expression is sent to the update component it" should "return an updated State" in {
    val newState = update(currentState, Solve(validExpression))
    import newState.*
    input.isDefined
    && expression.isDefined
    && solution.isDefined
    && cnf.isEmpty
    && problem.isEmpty
    && error.isEmpty shouldBe true
  }

  "When Solve message containing an invalid expression is sent to the update component it" should "return a defined error" in {
    val newState = update(currentState, Solve(invalidExpression))
    import newState.*
    input.isDefined
    && expression.isEmpty
    && solution.isEmpty
    && cnf.isEmpty
    && problem.isEmpty
    && error.isDefined shouldBe true
  }
