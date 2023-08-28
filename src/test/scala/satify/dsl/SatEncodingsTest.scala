package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import satify.model.Expression

class SatEncodingsTest extends AnyFlatSpec:

  import satify.model.dsl.DSL.{*, given}

  """ atLeastOne("A", "B", "C")  """ should """ be equal to "A" or "B" or "C" """ in {
    atLeastOne("A", "B", "C") shouldBe ("A" or "B" or "C")
  }

  """ atMostOne("A", "B") """ should """ be equal to (not("A") or "ENC0") and (not("B") or "ENC0") """ in {
    atMostOne("A", "B") shouldBe ((not("A") or "ENC0") and (not("B") or "ENC0"))
  }

  """ exactlyOne("A", "B") """ should """ be equal to atMostOne("A", "B") and atLeastOne("A", "B") """ in {
    exactlyOne("A", "B") shouldBe (atLeastOne("A", "B") and atMostOne("A", "B"))
  }

  """ atMostOne("A", "B", "C") """ should """ be equal to  """ in {
    atMostOne("A", "B", "C") shouldBe ((not("A") or "ENC0" and (not("C") or "ENC1")) and
      ((not("B") or "ENC1") and
        (not("ENC0") or "ENC1") and
        (not("B") or not("ENC0"))))
  }

  """ ("A", "B") atMost 1 """ should """ be equal to atMostOne("A", "B") """ in {
    ("A", "B") atMost 1 shouldBe ((not("A") or "ENC0") and (not("B") or "ENC0"))
  }

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
