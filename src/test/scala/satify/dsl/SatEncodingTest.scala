package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import satify.model.expression.Expression.*

class SatEncodingTest extends AnyFlatSpec:

  import satify.dsl.DSL.{*, given}

//  """ ("A", "B") atMost 1 """ should """ be equal to atMostOne("A", "B") """ in {
//    ("A", "B") atMost 1 shouldBe ((Not("A") or "ENC0") and (Not("B") or "ENC0"))
//  }

  """ ("A", "B") atMost one """ should """ be equal to ("A", "B") atMost 1 """ in {
    ("A", "B") atMost one shouldBe (("A", "B") atMost 1)
  }

  """ ("A", "B", "C") atLeast 1 """ should """ be equal to atLeastOne("A", "B") """ in {
    ("A", "B", "C") atLeast 1 shouldBe ("A" or "B" or "C")
  }

  """ ("A", "B", "C") atLeast one """ should """ be equal to ("A", "B", "C") atLeast 1 """ in {
    ("A", "B", "C") atLeast one shouldBe (("A", "B", "C") atLeast 1)
  }

  """ atLeastK(2)("A", "B", "C")  """ should """ be equal to "A" or "B" or "C" """ in {
    atLeastK(2)("A", "B", "C") shouldBe (("A" and "B") or ("A" and "C") or ("B" and "C"))
  }
