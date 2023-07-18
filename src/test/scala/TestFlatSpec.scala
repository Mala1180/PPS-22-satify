import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.convertToStringShouldWrapperForVerb

class TestFlatSpec extends AnyFlatSpec {

  "An empty List" should "have size 0" in {
    assert(List.empty.isEmpty)
  }

  it should "throw an IndexOutOfBoundsException when trying to access any element" in {
    val emptyList = List();
    assertThrows[IndexOutOfBoundsException] {
      emptyList(1)
    }
  }
}