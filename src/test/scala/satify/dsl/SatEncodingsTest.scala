package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import satify.model.Expression

class SatEncodingsTest extends AnyFlatSpec:

  import satify.dsl.DSL.{*, given}

  """ atLeastOne("A", "B", "C")  """ should """ be equal to "A" or "B" or "C" """ in {
    atLeastOne("A", "B", "C") shouldBe ("A" or "B" or "C")
  }

  """ atMostOne("A", "B") """ should """ be equal to (not("A") or "ENC0") and (not("B") or "ENC0") """ in {
    atMostOne("A", "B") shouldBe ((not("A") or "ENC0") and (not("B") or "ENC0"))
  }

  """ atMostOne("A", "B", "C") """ should """ be equal to  """ in {
    atMostOne("A", "B", "C") shouldBe ((not("A") or "ENC0" and (not("C") or "ENC1")) and
      ((not("B") or "ENC1") and
        (not("ENC0") or "ENC1") and
        (not("B") or not("ENC0"))))
  }
