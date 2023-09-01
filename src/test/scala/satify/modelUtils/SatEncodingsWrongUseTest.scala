package satify.modelUtils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import satify.model.tree.expression.Expression.*
import satify.model.tree.expression.Encodings.*
import satify.model.tree.*

class SatEncodingsWrongUseTest extends AnyFlatSpec:

  import satify.dsl.DSL.{*, given}

  """ atLeastOne("A")  """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastOne(expression.Symbol("A"))
    }
  }

  """ atMostOne("A")  """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atMostOne(expression.Symbol("A"))
    }
  }

  """ atLeastK(1)("A") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastK(1)(expression.Symbol("A"))
    }
  }

  """ atLeastK(4)("A", "B", "C") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atLeastK(4)(expression.Symbol("A"), expression.Symbol("B"), expression.Symbol("C"))
    }
  }

  """ atMostK(1)("A") """ should "throw an IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      atMostK(1)(expression.Symbol("A"))
    }
  }

  //""" atMostK(4)("A", "B", "C") """ should "throw an IllegalArgumentException" in {
  //  assertThrows[IllegalArgumentException] {
  //    atMostK(4)("A", "B", "C")
  //  }
  //}
