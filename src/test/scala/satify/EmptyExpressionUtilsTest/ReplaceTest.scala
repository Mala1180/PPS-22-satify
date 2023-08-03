package satify.EmptyExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression.*
import satify.model.{EmptyExpression, EmptyVariable}

class ReplaceTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the exp ¬c" should "not be replaced and input exp returned" in {
    val exp: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
    val substitution: EmptyExpression = Not(Symbol(EmptyVariable("c")))
    val result = replace(exp, substitution, Symbol(EmptyVariable("X0")))
    result shouldBe exp
  }

  "In ((a ∧ ¬b) ∨ c) the exp (a ∧ b)" should "not be replaced and input exp returned" in {
    val exp: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
    val substitution: EmptyExpression = And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b")))
    val result = replace(exp, substitution, Symbol(EmptyVariable("X0")))
    result shouldBe exp
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with X0" in {
    val exp: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
    val substitution: EmptyExpression = Not(Symbol(EmptyVariable("b")))
    val expected: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("X0"))), Symbol(EmptyVariable("c")))
    val result = replace(exp, substitution, Symbol(EmptyVariable("X0")))
    println(result)
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp (a ∧ ¬b)" should "be replaced with X0" in {
    val exp: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
    val substitution: EmptyExpression = And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b"))))
    val expected: EmptyExpression = Or(Symbol(EmptyVariable("X0")), Symbol(EmptyVariable("c")))
    val result = replace(exp, substitution, Symbol(EmptyVariable("X0")))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ((a ∧ ¬b) ∨ c)" should "be replaced with X0" in {
    val exp: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
    val substitution: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
    val expected: EmptyExpression = Symbol(EmptyVariable("X0"))
    val result = replace(exp, substitution, Symbol(EmptyVariable("X0")))
    result shouldBe expected
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with X0" in {
    val exp: EmptyExpression =
      Or(And(Not(Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("b")))), Not(Symbol(EmptyVariable("b"))))
    val substitution: EmptyExpression = Not(Symbol(EmptyVariable("b")))
    val expected: EmptyExpression =
      Or(And(Symbol(EmptyVariable("X0")), Symbol(EmptyVariable("X0"))), Symbol(EmptyVariable("X0")))
    val result = replace(exp, substitution, Symbol(EmptyVariable("X0")))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with X0 and then in ((a ∧ X0) ∨ c) the subexp (a ∧ X0) with X1" in {
    val expression: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
    val sub1: EmptyExpression = Not(Symbol(EmptyVariable("b")))
    val exp1: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("X0"))), Symbol(EmptyVariable("c")))
    val res1 = replace(expression, sub1, Symbol(EmptyVariable("X0")))
    res1 shouldBe exp1
    val sub2: EmptyExpression = And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("X0")))
    val exp2: EmptyExpression = Or(Symbol(EmptyVariable("X1")), Symbol(EmptyVariable("c")))
    val res2 = replace(exp1, sub2, Symbol(EmptyVariable("X1")))
    res2 shouldBe exp2
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp (¬b ∧ ¬b)" should "be replaced with X0" in {
    val exp: EmptyExpression =
      Or(And(Not(Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("b")))), Not(Symbol(EmptyVariable("b"))))
    val substitution: EmptyExpression = And(Not(Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("b"))))
    val expected: EmptyExpression = Or(Symbol(EmptyVariable("X0")), Not(Symbol(EmptyVariable("b"))))
    val result = replace(exp, substitution, Symbol(EmptyVariable("X0")))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with X0" in {
    val exp: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Not(Symbol(EmptyVariable("b"))))
    val substitution: EmptyExpression = Not(Symbol(EmptyVariable("b")))
    val expected: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("X0"))), Symbol(EmptyVariable("X0")))
    val result = replace(exp, substitution, Symbol(EmptyVariable("X0")))
    result shouldBe expected
  }
