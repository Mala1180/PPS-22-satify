package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import satify.model.Expression.*

class SimpleOperatorsTest extends AnyFlatSpec:

  import satify.dsl.DSL.{*, given}

  """ "A" and "B" """ should "return And(Symbol(A), Symbol(B))" in {
    val exp = "A" and "B"
    exp shouldBe And(Symbol("A"), Symbol("B"))
  }

  """ "A" or "B" """ should "return Or(Symbol(A), Symbol(B))" in {
    val exp = "A" or "B"
    exp shouldBe Or(Symbol("A"), Symbol("B"))
  }

  """ not("A") """ should "return Not(Symbol(A))" in {
    val exp = not("A")
    exp shouldBe Not(Symbol("A"))
  }

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
