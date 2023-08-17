package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import satify.model.Expression.*

class BasicOperatorsTest extends AnyFlatSpec:

  import satify.dsl.DSL.{*, given}

  """ "A" and "B" """ should "return And(Symbol(A), Symbol(B))" in {
    "A" and "B" shouldBe And(Symbol("A"), Symbol("B"))
  }

  """ "A" or "B" """ should "return Or(Symbol(A), Symbol(B))" in {
    "A" or "B" shouldBe Or(Symbol("A"), Symbol("B"))
  }

  """ not("A") """ should "return Not(Symbol(A))" in {
    not("A") shouldBe Not(Symbol("A"))
  }

  """ "A" xor "B" """ should "return Or(And(Symbol(A), Not(Symbol(B))), And(Not(Symbol(A)), Symbol(B)))" in {
    "A" xor "B" shouldBe Or(And(Symbol("A"), Not(Symbol("B"))), And(Not(Symbol("A")), Symbol("B")))
  }

  "operators" should "be able to be combined with parenthesis" in {
    not("A") and "B" or "C" shouldBe Or(And(Not(Symbol("A")), Symbol("B")), Symbol("C"))
    "A" and (not("B") or "C") shouldBe And(Symbol("A"), Or(Not(Symbol("B")), Symbol("C")))
    ("A" and "B") or not("C") shouldBe Or(And(Symbol("A"), Symbol("B")), Not(Symbol("C")))
  }

  "operators" should "be left associative" in {
    val exp1 = "A" and "B" or "C" and not("D")
    val exp2 = (("A" and "B") or "C") and not("D")
    val expected = And(
      Or(And(Symbol("A"), Symbol("B")), Symbol("C")),
      Not(Symbol("D"))
    )
    exp1 shouldBe expected
    exp2 shouldBe expected
  }
