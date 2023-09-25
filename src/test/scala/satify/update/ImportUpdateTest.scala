package satify.update

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.State
import satify.model.errors.Error.InvalidImport
import satify.update.Message.*
import satify.update.Update.update

import java.io.File

class ImportUpdateTest extends AnyFlatSpec with Matchers:

  val currentState: State = State()
  val validFile: File = File("src/main/resources/cnf/nqueens4.txt")
  val invalidFile: File = File("wrong/path/to/file.txt")

  "The import of valid file" should "return an updated State without Error" in {
    val newState = update(currentState, Import(validFile))
    newState.error.isEmpty shouldBe true
  }

  "The import of invalid file" should "return a State with InvalidImport Error" in {
    update(currentState, Import(invalidFile)) shouldBe State(InvalidImport())
  }
