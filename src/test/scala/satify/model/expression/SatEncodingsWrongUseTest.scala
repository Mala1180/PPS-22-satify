package satify.model.expression

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import satify.model.expression.Expression.*
import satify.model.expression.SymbolGeneration.{ErasableSymbolGenerator, encodingVarPrefix}

class SatEncodingsWrongUseTest extends AnyFlatSpec:

  given ErasableSymbolGenerator with
    override def prefix: String = encodingVarPrefix

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

  "exactly(1)()" should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      exactlyK(1)()
    }
  }

  """ atLeastK(0)("A", "B", "C") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastK(0)(Symbol("A"), Symbol("B"), Symbol("C"))
    }
  }

