package satify.ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Variable
import satify.model.expression.Expression
import satify.model.expression.Expression.*

class ReplaceTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the exp ¬c" should "not be replaced and input exp returned" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val substitution: Expression = Not(Symbol("c"))
    val result = replace(exp, substitution, Symbol("TSTN0"))
    result shouldBe exp
  }

  "In ((a ∧ ¬b) ∨ c) the exp (a ∧ b)" should "not be replaced and input exp returned" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val substitution: Expression = And(Symbol("a"), Symbol("b"))
    val result = replace(exp, substitution, Symbol("TSTN0"))
    result shouldBe exp
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with TSTN0" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val substitution: Expression = Not(Symbol("b"))
    val expected: Expression =
      Or(And(Symbol("a"), Symbol("TSTN0")), Symbol("c"))
    val result = replace(exp, substitution, Symbol("TSTN0"))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp (a ∧ ¬b)" should "be replaced with TSTN0" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val substitution: Expression = And(Symbol("a"), Not(Symbol("b")))
    val expected: Expression = Or(Symbol("TSTN0"), Symbol("c"))
    val result = replace(exp, substitution, Symbol("TSTN0"))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ((a ∧ ¬b) ∨ c)" should "be replaced with TSTN0" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val substitution: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val expected: Expression = Symbol("TSTN0")
    val result = replace(exp, substitution, Symbol("TSTN0"))
    result shouldBe expected
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with TSTN0" in {
    val exp: Expression =
      Or(And(Not(Symbol("b")), Not(Symbol("b"))), Not(Symbol("b")))
    val substitution: Expression = Not(Symbol("b"))
    val expected: Expression =
      Or(And(Symbol("TSTN0"), Symbol("TSTN0")), Symbol("TSTN0"))
    val result = replace(exp, substitution, Symbol("TSTN0"))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with TSTN0 and then in ((a ∧ TSTN0) ∨ c) the subexp (a ∧ TSTN0) with TSTN1" in {
    val expression: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val sub1: Expression = Not(Symbol("b"))
    val exp1: Expression =
      Or(And(Symbol("a"), Symbol("TSTN0")), Symbol("c"))
    val res1 = replace(expression, sub1, Symbol("TSTN0"))
    res1 shouldBe exp1
    val sub2: Expression = And(Symbol("a"), Symbol("TSTN0"))
    val exp2: Expression = Or(Symbol("TSTN1"), Symbol("c"))
    val res2 = replace(exp1, sub2, Symbol("TSTN1"))
    res2 shouldBe exp2
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp (¬b ∧ ¬b)" should "be replaced with TSTN0" in {
    val exp: Expression =
      Or(And(Not(Symbol("b")), Not(Symbol("b"))), Not(Symbol("b")))
    val substitution: Expression = And(Not(Symbol("b")), Not(Symbol("b")))
    val expected: Expression = Or(Symbol("TSTN0"), Not(Symbol("b")))
    val result = replace(exp, substitution, Symbol("TSTN0"))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with TSTN0" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Not(Symbol("b")))
    val substitution: Expression = Not(Symbol("b"))
    val expected: Expression =
      Or(And(Symbol("a"), Symbol("TSTN0")), Symbol("TSTN0"))
    val result = replace(exp, substitution, Symbol("TSTN0"))
    result shouldBe expected
  }
