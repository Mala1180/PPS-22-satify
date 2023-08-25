package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import satify.model.Expression

class SatEncodingsWrongUseTest extends AnyFlatSpec:

  import satify.dsl.DSLExports.{given, *}

  """ atLeastOne("A")  """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastOne("A")
    }
  }

  """ atMostOne("A")  """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atMostOne("A")
    }
  }

  """ atLeastK(1)("A") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastK(1)("A")
    }
  }

  """ atLeastK(4)("A", "B", "C") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastK(4)("A", "B", "C")
    }
  }


  """ atMostK(1)("A") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atMostK(1)("A")
    }
  }

  """ atMostK(4)("A", "B", "C") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atMostK(4)("A", "B", "C")
    }
  }
