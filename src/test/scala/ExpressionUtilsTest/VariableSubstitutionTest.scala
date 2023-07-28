package ExpressionUtilsTest

import model.Expression
import model.Operator
import Expression.*
import model.Operator.*
import update.converters.TseitinTransformation.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class VariableSubstitutionTest extends AnyFlatSpec with Matchers:

  "In (¬b) the replacement of subexpressions with symbols" should "return only a new variable representing the input expression" in {
    val exp: Expression = Clause(Not(Literal("b")))
    val result: List[(Literal, Expression)] = symbolsReplace(exp)
    result should contain only (
      (Literal("X0"), Clause(Not(Literal("b"))))
    )
  }

  "In (a ∨ ¬b) the replacement of subexpressions with symbols" should "generate 2 new symbols starting from the ¬b subexpression" in {
    val exp: Expression = Clause(Or(Literal("a"), Clause(Not(Literal("b")))))
    val result: List[(Literal, Expression)] = symbolsReplace(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X1"), Clause(Not(Literal("b")))),
      (Literal("X0"), Clause(Or(Literal("a"), Literal("X1"))))
    )
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val list: List[(Literal, Expression)] = symbolsReplace(exp)

    val expectedList: List[(Literal, Expression)] = List(
      (Literal("X2"), Clause(Not(Literal("b")))),
      (Literal("X1"), Clause(And(Literal("a"), Literal("X2")))),
      (Literal("X0"), Clause(Or(Literal("X1"), Literal("c"))))
    )
    expectedList shouldBe list
  }

  "In ((a ∧ b) ∨ (c ∧ d) ∧ (e ∧ f)) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: Expression = Clause(
      And(
        Clause(Or(Clause(And(Literal("a"), Literal("b"))), Clause(And(Literal("c"), Literal("d"))))),
        Clause(And(Literal("e"), Literal("f")))
      )
    )
    val result: List[(Literal, Expression)] = symbolsReplace(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X4"), Clause(And(Literal("e"), Literal("f")))),
      (Literal("X3"), Clause(And(Literal("c"), Literal("d")))),
      (Literal("X2"), Clause(And(Literal("a"), Literal("b")))),
      (Literal("X1"), Clause(Or(Literal("X2"), Literal("X3")))),
      (Literal("X0"), Clause(And(Literal("X1"), Literal("X4"))))
    )
    result shouldBe expected
  }

  "In ((a ∧ (a ∨ b)) ∨ (¬c ∧ d)) the replacement of subexpressions with variables" should "follow the nesting level and considering the not subexpression" in {
    val exp: Expression = Clause(
      Or(
        Clause(And(Literal("a"), Clause(Or(Literal("a"), Literal("b"))))),
        Clause(And(Clause(Not(Literal("c"))), Literal("d")))
      )
    )
    val result: List[(Literal, Expression)] = symbolsReplace(exp)
    val expected: List[(Literal, Expression)] = List(
      (Literal("X4"), Clause(Not(Literal("c")))),
      (Literal("X3"), Clause(And(Literal("X4"), Literal("d")))),
      (Literal("X2"), Clause(Or(Literal("a"), Literal("b")))),
      (Literal("X1"), Clause(And(Literal("a"), Literal("X2")))),
      (Literal("X0"), Clause(Or(Literal("X1"), Literal("X3"))))
    )
    result shouldBe expected
  }
