package satify.modelUtils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import satify.model.expression.Expression.*

class SatEncodingsWrongUseTest extends AnyFlatSpec:

  import satify.dsl.DSL.{*, given}

  """ atLeastOne("A")  """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastOne(Symbol("A"))
    }
  }

  """ atMostOne("A")  """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atMostOne(Symbol("A"))
    }
  }

  """ atLeastK(1)("A") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastK(1)(Symbol("A"))
    }
  }

  """ atLeastK(4)("A", "B", "C") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastK(4)(Symbol("A"), Symbol("B"), Symbol("C"))
    }
  }

  """ atMostK(1)("A") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atMostK(1)(Symbol("A"))
    }
  }

  """ atMostK(4)("A", "B", "C") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atMostK(4)("A", "B", "C")
    }
  }
