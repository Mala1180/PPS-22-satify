package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe

class MathOperatorsTest extends AnyFlatSpec:

  import satify.dsl.DSL.{*, given}

  """ "A" /\ "B" """ should """ be equal to "A" and "B" """ in {
    "A" /\ "B" shouldBe ("A" and "B")
  }

  """ "A" \/ "B" """ should """ be equal to "A" or "B" """ in {
    "A" \/ "B" shouldBe ("A" or "B")
  }

  """ !"A" """ should """ be equal to not("A") """ in {
    !"A" shouldBe not("A")
  }
/*
  """ operators """ should "be able to be combined with parenthesis" in {
    val exp1 = not("A") and "B" or "C"
    exp1 shouldBe Or(And(Not(Symbol("A")), Symbol("B")), Symbol("C"))
    val exp2 = "A" and (not("B") or "C")
    exp2 shouldBe And(Symbol("A"), Or(Not(Symbol("B")), Symbol("C")))
    val exp3 = ("A" and "B") or not("C")
    exp3 shouldBe Or(And(Symbol("A"), Symbol("B")), Not(Symbol("C")))
  }

  """ operators """ should "be left associative" in {
    val exp1 = "A" and "B" or "C" and not("D")
    val exp2 = (("A" and "B") or "C") and not("D")
    val expected = And(
      Or(And(Symbol("A"), Symbol("B")), Symbol("C")),
      Not(Symbol("D"))
    )
    exp1 shouldBe expected
    exp2 shouldBe expected
  }
 */
