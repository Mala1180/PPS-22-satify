package satify.model.expression

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import satify.model.expression.Expression.*

class SatEncodingsWrongUseTest extends AnyFlatSpec:

  given ResettableSymbolGenerator with
    def prefix: String = "ENC"
    
  "atLeastOne()" should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastOne()
    }
  }

  "atMostOne()" should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atMostOne()
    }
  }

  """ atLeastK(4)("A", "B", "C") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastK(4)(Symbol("A"), Symbol("B"), Symbol("C"))
    }
  }

  """ atMostK(4)("A", "B", "C") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atMostK(4)(Symbol("A"), Symbol("B"), Symbol("C"))
    }
  }

  "exactlyOne()" should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      exactlyOne()
    }
  }
