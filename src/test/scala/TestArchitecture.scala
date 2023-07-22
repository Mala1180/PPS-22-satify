
import Architecture.MVU
import model.Model
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import update.Message
import view.GUI

class TestArchitecture extends AnyFlatSpec with Matchers:

  val mvu: MVU = new MVU {}

  "Model" should "be a trait" in {
    mvu.model shouldBe a [Model]
  }

  "Update" should "be a function taking a Model and a Message, returning a new Model" in {
    mvu.update shouldBe a [(Model, Message) => Model]
  }

  "View" should "be a function which takes a Model and returns a GUI" in {
    mvu.view shouldBe a [Model => GUI]
  }
