package ExpressionUtilsTest

import model.Expression
import model.Operator
import Expression.*
import model.Operator.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import tseitin.Transformation.*


class TransformationTest extends AnyFlatSpec with Matchers:

  "The transformation of ¬b" should "return a list of CNF clauses relative to the ¬ operator" in {
    val exp: (Literal, Expression) = (Literal("X0"), Clause(Not(Literal("b"))))
    val result: List[(Literal, Expression)] = transform(exp)
    val expected : List[(Literal, Expression)] = List(
      (Literal("X0"), Clause(Or(Clause(Not(Literal("b"))), Clause(Not(Literal("X0")))))),
      (Literal("X0"), Clause(Or(Literal("b"), Literal("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Literal, Expression) = (Literal("X0"), Clause(And(Literal("a"), Literal("b"))))
    val result: List[(Literal, Expression)] = transform(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X0"), Clause(Or(Clause(Or(Clause(Not(Literal("a"))), Clause(Not(Literal("b"))))), Literal("X0")))),
      (Literal("X0"), Clause(Or(Literal("a"), Clause(Not(Literal("X0")))))),
      (Literal("X0"), Clause(Or(Literal("b"), Clause(Not(Literal("X0"))))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Literal, Expression) = (Literal("X0"), Clause(And(Clause(Not(Literal("a"))), Literal("b"))))
    val result: List[(Literal, Expression)] = transform(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X0"), Clause(Or(Clause(Or(Literal("a"), Clause(Not(Literal("b"))))), Literal("X0")))),
      (Literal("X0"), Clause(Or(Clause(Not(Literal("a"))), Clause(Not(Literal("X0")))))),
      (Literal("X0"), Clause(Or(Literal("b"), Clause(Not(Literal("X0"))))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Literal, Expression) = (Literal("X0"), Clause(And(Literal("a"), Clause(Not(Literal("b"))))))
    val result: List[(Literal, Expression)] = transform(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X0"), Clause(Or(Clause(Or(Clause(Not(Literal("a"))), Literal("b"))), Literal("X0")))),
      (Literal("X0"), Clause(Or(Literal("a"), Clause(Not(Literal("X0")))))),
      (Literal("X0"), Clause(Or(Clause(Not(Literal("b"))), Clause(Not(Literal("X0"))))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Literal, Expression) = (Literal("X0"), Clause(And(Clause(Not(Literal("a"))), Clause(Not(Literal("b"))))))
    val result: List[(Literal, Expression)] = transform(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X0"), Clause(Or(Clause(Or(Literal("a"), Literal("b"))), Literal("X0")))),
      (Literal("X0"), Clause(Or(Clause(Not(Literal("a"))), Clause(Not(Literal("X0")))))),
      (Literal("X0"), Clause(Or(Clause(Not(Literal("b"))), Clause(Not(Literal("X0"))))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Literal, Expression) = (Literal("X0"), Clause(Or(Literal("a"), Literal("b"))))
    val result: List[(Literal, Expression)] = transform(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X0"), Clause(Or(Clause(Or(Literal("a"), Literal("b"))), Clause(Not(Literal("X0")))))),
      (Literal("X0"), Clause(Or(Clause(Not(Literal("a"))), Literal("X0")))),
      (Literal("X0"), Clause(Or(Clause(Not(Literal("b"))), Literal("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Literal, Expression) = (Literal("X0"), Clause(Or(Clause(Not(Literal("a"))), Literal("b"))))
    val result: List[(Literal, Expression)] = transform(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X0"), Clause(Or(Clause(Or(Clause(Not(Literal("a"))), Literal("b"))), Clause(Not(Literal("X0")))))),
      (Literal("X0"), Clause(Or(Literal("a"), Literal("X0")))),
      (Literal("X0"), Clause(Or(Clause(Not(Literal("b"))), Literal("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Literal, Expression) = (Literal("X0"), Clause(Or(Literal("a"), Clause(Not(Literal("b"))))))
    val result: List[(Literal, Expression)] = transform(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X0"), Clause(Or(Clause(Or(Literal("a"), Clause(Not(Literal("b"))))), Clause(Not(Literal("X0")))))),
      (Literal("X0"), Clause(Or(Clause(Not(Literal("a"))), Literal("X0")))),
      (Literal("X0"), Clause(Or(Literal("b"), Literal("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Literal, Expression) = (Literal("X0"), Clause(Or(Clause(Not(Literal("a"))), Clause(Not(Literal("b"))))))
    val result: List[(Literal, Expression)] = transform(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X0"), Clause(Or(Clause(Or(Clause(Not(Literal("a"))), Clause(Not(Literal("b"))))), Clause(Not(Literal("X0")))))),
      (Literal("X0"), Clause(Or(Literal("a"), Literal("X0")))),
      (Literal("X0"), Clause(Or(Literal("b"), Literal("X0"))))
    )
    result shouldBe expected
  }
