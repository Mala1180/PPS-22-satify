package dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import satify.model.EmptyVariable
import satify.model.Expression.*

class SimpleOperatorsTest extends AnyFlatSpec:

  import satify.dsl.DSL.{*, given}

  """ "A" and "B" """ should "return And(Symbol(A), Symbol(B))" in {
    val exp = "A" and "B"
    exp shouldBe And(Symbol(EmptyVariable("A")), Symbol(EmptyVariable("B")))
  }

  """ "A" or "B" """ should "return Or(Symbol(A), Symbol(B))" in {
    val exp = "A" or "B"
    exp shouldBe Or(Symbol(EmptyVariable("A")), Symbol(EmptyVariable("B")))
  }

  """ not("A") """ should "return Not(Symbol(A))" in {
    val exp = not("A")
    exp shouldBe Not(Symbol(EmptyVariable("A")))
  }

  """ operators """ should "be able to be combined with parenthesis" in {
    val exp1 = not("A") and "B" or "C"
    exp1 shouldBe Or(And(Not(Symbol(EmptyVariable("A"))), Symbol(EmptyVariable("B"))), Symbol(EmptyVariable("C")))
    val exp2 = "A" and (not("B") or "C")
    exp2 shouldBe And(Symbol(EmptyVariable("A")), Or(Not(Symbol(EmptyVariable("B"))), Symbol(EmptyVariable("C"))))
    val exp3 = ("A" and "B") or not("C")
    exp3 shouldBe Or(And(Symbol(EmptyVariable("A")), Symbol(EmptyVariable("B"))), Not(Symbol(EmptyVariable("C"))))
  }

  """ operators """ should "be left associative" in {
    val exp1 = "A" and "B" or "C" and not("D")
    val exp2 = (("A" and "B") or "C") and not("D")
    exp1 shouldBe exp2
  }
