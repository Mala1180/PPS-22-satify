package EmptyModelUtilsTest

import model.{EmptyModel, NamedVariable}
import model.Expression.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReplaceTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the exp ¬c" should "not be replaced and input exp returned" in {
    val exp: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val substitution: EmptyModel = Not(Symbol(NamedVariable("c")))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    result shouldBe exp
  }

  "In ((a ∧ ¬b) ∨ c) the exp (a ∧ b)" should "not be replaced and input exp returned" in {
    val exp: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val substitution: EmptyModel = And(Symbol(NamedVariable("a")), Symbol(NamedVariable("b")))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    result shouldBe exp
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with X0" in {
    val exp: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val substitution: EmptyModel = Not(Symbol(NamedVariable("b")))
    val expected: EmptyModel = Or(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("X0"))), Symbol(NamedVariable("c")))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp (a ∧ ¬b)" should "be replaced with X0" in {
    val exp: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val substitution: EmptyModel = And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b"))))
    val expected: EmptyModel = Or(Symbol(NamedVariable("X0")), Symbol(NamedVariable("c")))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ((a ∧ ¬b) ∨ c)" should "be replaced with X0" in {
    val exp: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val substitution: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val expected: EmptyModel = Symbol(NamedVariable("X0"))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with X0 and then in ((a ∧ X0) ∨ c) the subexp (a ∧ X0) with X1" in {
    val expression: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val sub1: EmptyModel = Not(Symbol(NamedVariable("b")))
    val exp1: EmptyModel = Or(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("X0"))), Symbol(NamedVariable("c")))
    val res1 = replace(expression, sub1, Symbol(NamedVariable("X0")))
    res1 shouldBe exp1
    val sub2: EmptyModel = And(Symbol(NamedVariable("a")), Symbol(NamedVariable("X0")))
    val exp2: EmptyModel = Or(Symbol(NamedVariable("X1")), Symbol(NamedVariable("c")))
    val res2 = replace(exp1, sub2, Symbol(NamedVariable("X1")))
    res2 shouldBe exp2
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp (¬b ∧ ¬b)" should "be replaced with X0" in {
    val exp: EmptyModel = Or(And(Not(Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("b")))), Not(Symbol(NamedVariable("b"))))
    val substitution: EmptyModel = And(Not(Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("b"))))
    val expected: EmptyModel = Or(Symbol(NamedVariable("X0")), Not(Symbol(NamedVariable("b"))))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    result shouldBe expected
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with X0" in {
    val exp: EmptyModel = Or(And(Not(Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("b")))), Not(Symbol(NamedVariable("b"))))
    val substitution: EmptyModel = Not(Symbol(NamedVariable("b")))
    val expected: EmptyModel = Or(And(Symbol(NamedVariable("X0")), Symbol(NamedVariable("X0"))), Symbol(NamedVariable("X0")))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with X0" in {
    val exp: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Not(Symbol(NamedVariable("b"))))
    val substitution: EmptyModel = Not(Symbol(NamedVariable("b")))
    val expected: EmptyModel = Or(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("X0"))), Symbol(NamedVariable("X0")))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    result shouldBe expected
  }
