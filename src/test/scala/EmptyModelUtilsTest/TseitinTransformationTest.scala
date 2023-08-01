package EmptyModelUtilsTest

import model.{EmptyModel, NamedVariable}
import model.Expression.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import update.converters.TseitinTransformation.*

class TseitinTransformationTest extends AnyFlatSpec with Matchers:

  "The transformation of ¬b" should "return a list of CNF clauses relative to the ¬ operator" in {
    val exp: (Symbol[NamedVariable], EmptyModel) = (Symbol(NamedVariable("X0")), Not(Symbol(NamedVariable("b"))))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = transform(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("X0"))))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("b")), Symbol(NamedVariable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol[NamedVariable], EmptyModel) = (Symbol(NamedVariable("X0")), And(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = transform(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X0")), Or(Or(Not(Symbol(NamedVariable("a"))), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("X0")))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("X0"))))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("b")), Not(Symbol(NamedVariable("X0")))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol[NamedVariable], EmptyModel) = (Symbol(NamedVariable("X0")), And(Not(Symbol(NamedVariable("a"))), Symbol(NamedVariable("b"))))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = transform(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X0")), Or(Or(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("X0")))),
      (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("a"))), Not(Symbol(NamedVariable("X0"))))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("b")), Not(Symbol(NamedVariable("X0")))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol[NamedVariable], EmptyModel) = (Symbol(NamedVariable("X0")), And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = transform(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X0")), Or(Or(Not(Symbol(NamedVariable("a"))), Symbol(NamedVariable("b"))), Symbol(NamedVariable("X0")))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("X0"))))),
      (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("X0")))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol[NamedVariable], EmptyModel) = (Symbol(NamedVariable("X0")), And(Not(Symbol(NamedVariable("a"))), Not(Symbol(NamedVariable("b")))))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = transform(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X0")), Or(Or(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))), Symbol(NamedVariable("X0")))),
      (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("a"))), Not(Symbol(NamedVariable("X0"))))),
      (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("X0")))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol[NamedVariable], EmptyModel) = (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = transform(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X0")), Or(Or(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("X0"))))),
      (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("a"))), Symbol(NamedVariable("X0")))),
      (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("b"))), Symbol(NamedVariable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol[NamedVariable], EmptyModel) = (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("a"))), Symbol(NamedVariable("b"))))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = transform(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X0")), Or(Or(Not(Symbol(NamedVariable("a"))), Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("X0"))))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("a")), Symbol(NamedVariable("X0")))),
      (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("b"))), Symbol(NamedVariable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol[NamedVariable], EmptyModel) = (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = transform(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X0")), Or(Or(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Not(Symbol(NamedVariable("X0"))))),
      (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("a"))), Symbol(NamedVariable("X0")))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("b")), Symbol(NamedVariable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol[NamedVariable], EmptyModel) = (Symbol(NamedVariable("X0")), Or(Not(Symbol(NamedVariable("a"))), Not(Symbol(NamedVariable("b")))))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = transform(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X0")), Or(Or(Not(Symbol(NamedVariable("a"))), Not(Symbol(NamedVariable("b")))), Not(Symbol(NamedVariable("X0"))))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("a")), Symbol(NamedVariable("X0")))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("b")), Symbol(NamedVariable("X0"))))
    )
    result shouldBe expected
  }
