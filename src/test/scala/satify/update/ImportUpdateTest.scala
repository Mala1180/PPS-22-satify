package satify.update

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.State
import satify.model.problems.ProblemChoice
import satify.model.problems.ProblemChoice.NQueens
import satify.update.Message.*
import satify.update.Update.update
import satify.view.GUI.inputTextArea

import java.io.File

class ImportUpdateTest extends AnyFlatSpec with Matchers:

  val currentState: State = State()
  val validFile: File = File("src/main/resources/cnf/nqueens4.txt")
  val invalidFile: File = File("wrong/path/to/file.txt")

  "When Convert message containing a valid expression is sent to the update component it" should "return an updated State" in {
    val newState = update(currentState, Import(validFile))
    import newState.*
    input.isDefined
    && expression.isEmpty
    && solution.isEmpty
    && cnf.isDefined
    && problem.isEmpty
    && error.isEmpty shouldBe true
  }

  "When Import message containing an invalid file is sent to the update component it" should "return a defined Error" in {
    val newState = update(currentState, Import(invalidFile))
    import newState.*
    input.isEmpty
    && expression.isEmpty
    && solution.isEmpty
    && cnf.isEmpty
    && problem.isEmpty
    && error.isDefined shouldBe true
  }
