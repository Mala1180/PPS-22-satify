package EmptyExpressionUtilsTest

import model.{EmptyExpression, EmptyVariable}
import model.Expression.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import update.converters.TseitinTransformation.*

class TseitinTransformationTest extends AnyFlatSpec with Matchers:

  "The transformation of ¬b" should "return a list of CNF clauses relative to the ¬ operator" in {
    val exp: (Symbol[EmptyVariable], EmptyExpression) = (Symbol(EmptyVariable("X0")), Not(Symbol(EmptyVariable("b"))))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = transform(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("X0"))))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol[EmptyVariable], EmptyExpression) = (Symbol(EmptyVariable("X0")), And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b"))))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = transform(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X0")), Or(Or(Not(Symbol(EmptyVariable("a"))), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("X0")))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("X0"))))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("b")), Not(Symbol(EmptyVariable("X0")))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol[EmptyVariable], EmptyExpression) = (Symbol(EmptyVariable("X0")), And(Not(Symbol(EmptyVariable("a"))), Symbol(EmptyVariable("b"))))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = transform(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X0")), Or(Or(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("X0")))),
      (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("a"))), Not(Symbol(EmptyVariable("X0"))))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("b")), Not(Symbol(EmptyVariable("X0")))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol[EmptyVariable], EmptyExpression) = (Symbol(EmptyVariable("X0")), And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = transform(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X0")), Or(Or(Not(Symbol(EmptyVariable("a"))), Symbol(EmptyVariable("b"))), Symbol(EmptyVariable("X0")))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("X0"))))),
      (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("X0")))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol[EmptyVariable], EmptyExpression) = (Symbol(EmptyVariable("X0")), And(Not(Symbol(EmptyVariable("a"))), Not(Symbol(EmptyVariable("b")))))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = transform(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X0")), Or(Or(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b"))), Symbol(EmptyVariable("X0")))),
      (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("a"))), Not(Symbol(EmptyVariable("X0"))))),
      (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("X0")))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol[EmptyVariable], EmptyExpression) = (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b"))))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = transform(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X0")), Or(Or(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("X0"))))),
      (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("a"))), Symbol(EmptyVariable("X0")))),
      (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("b"))), Symbol(EmptyVariable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol[EmptyVariable], EmptyExpression) = (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("a"))), Symbol(EmptyVariable("b"))))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = transform(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X0")), Or(Or(Not(Symbol(EmptyVariable("a"))), Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("X0"))))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("X0")))),
      (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("b"))), Symbol(EmptyVariable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol[EmptyVariable], EmptyExpression) = (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = transform(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X0")), Or(Or(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Not(Symbol(EmptyVariable("X0"))))),
      (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("a"))), Symbol(EmptyVariable("X0")))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol[EmptyVariable], EmptyExpression) = (Symbol(EmptyVariable("X0")), Or(Not(Symbol(EmptyVariable("a"))), Not(Symbol(EmptyVariable("b")))))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = transform(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X0")), Or(Or(Not(Symbol(EmptyVariable("a"))), Not(Symbol(EmptyVariable("b")))), Not(Symbol(EmptyVariable("X0"))))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("X0")))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("X0"))))
    )
    result shouldBe expected
  }
