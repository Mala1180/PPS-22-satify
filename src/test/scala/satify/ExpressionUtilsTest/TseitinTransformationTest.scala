package ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression
import satify.model.Expression.*
import satify.update.converters.TseitinTransformation.transform

class TseitinTransformationTest extends AnyFlatSpec with Matchers:

  "The transformation of ¬b" should "return a list of CNF clauses relative to the ¬ operator" in {
    val exp: (Symbol, Expression) = (Symbol("X0"), Not(Symbol("b")))
    val result: List[(Symbol, Expression)] = transform(exp)
    val expected: List[(Symbol, Expression)] = List(
      (Symbol("X0"), Or(Not(Symbol("b")), Not(Symbol("X0")))),
      (Symbol("X0"), Or(Symbol("b"), Symbol("X0")))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), And(Symbol("a"), Symbol("b")))
    val result: List[(Symbol, Expression)] = transform(exp)
    val expected: List[(Symbol, Expression)] = List(
      (
        Symbol("X0"),
        Or(Or(Not(Symbol("a")), Not(Symbol("b"))), Symbol("X0"))
      ),
      (Symbol("X0"), Or(Symbol("a"), Not(Symbol("X0")))),
      (Symbol("X0"), Or(Symbol("b"), Not(Symbol("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), And(Not(Symbol("a")), Symbol("b")))
    val result: List[(Symbol, Expression)] = transform(exp)
    val expected: List[(Symbol, Expression)] = List(
      (
        Symbol("X0"),
        Or(Or(Symbol("a"), Not(Symbol("b"))), Symbol("X0"))
      ),
      (Symbol("X0"), Or(Not(Symbol("a")), Not(Symbol("X0")))),
      (Symbol("X0"), Or(Symbol("b"), Not(Symbol("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), And(Symbol("a"), Not(Symbol("b"))))
    val result: List[(Symbol, Expression)] = transform(exp)
    val expected: List[(Symbol, Expression)] = List(
      (
        Symbol("X0"),
        Or(Or(Not(Symbol("a")), Symbol("b")), Symbol("X0"))
      ),
      (Symbol("X0"), Or(Symbol("a"), Not(Symbol("X0")))),
      (Symbol("X0"), Or(Not(Symbol("b")), Not(Symbol("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), And(Not(Symbol("a")), Not(Symbol("b"))))
    val result: List[(Symbol, Expression)] = transform(exp)
    val expected: List[(Symbol, Expression)] = List(
      (
        Symbol("X0"),
        Or(Or(Symbol("a"), Symbol("b")), Symbol("X0"))
      ),
      (Symbol("X0"), Or(Not(Symbol("a")), Not(Symbol("X0")))),
      (Symbol("X0"), Or(Not(Symbol("b")), Not(Symbol("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), Or(Symbol("a"), Symbol("b")))
    val result: List[(Symbol, Expression)] = transform(exp)
    val expected: List[(Symbol, Expression)] = List(
      (
        Symbol("X0"),
        Or(Or(Symbol("a"), Symbol("b")), Not(Symbol("X0")))
      ),
      (Symbol("X0"), Or(Not(Symbol("a")), Symbol("X0"))),
      (Symbol("X0"), Or(Not(Symbol("b")), Symbol("X0")))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), Or(Not(Symbol("a")), Symbol("b")))
    val result: List[(Symbol, Expression)] = transform(exp)
    val expected: List[(Symbol, Expression)] = List(
      (
        Symbol("X0"),
        Or(Or(Not(Symbol("a")), Symbol("b")), Not(Symbol("X0")))
      ),
      (Symbol("X0"), Or(Symbol("a"), Symbol("X0"))),
      (Symbol("X0"), Or(Not(Symbol("b")), Symbol("X0")))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), Or(Symbol("a"), Not(Symbol("b"))))
    val result: List[(Symbol, Expression)] = transform(exp)
    val expected: List[(Symbol, Expression)] = List(
      (
        Symbol("X0"),
        Or(Or(Symbol("a"), Not(Symbol("b"))), Not(Symbol("X0")))
      ),
      (Symbol("X0"), Or(Not(Symbol("a")), Symbol("X0"))),
      (Symbol("X0"), Or(Symbol("b"), Symbol("X0")))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), Or(Not(Symbol("a")), Not(Symbol("b"))))
    val result: List[(Symbol, Expression)] = transform(exp)
    val expected: List[(Symbol, Expression)] = List(
      (
        Symbol("X0"),
        Or(Or(Not(Symbol("a")), Not(Symbol("b"))), Not(Symbol("X0")))
      ),
      (Symbol("X0"), Or(Symbol("a"), Symbol("X0"))),
      (Symbol("X0"), Or(Symbol("b"), Symbol("X0")))
    )
    result shouldBe expected
  }
