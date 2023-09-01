package satify.modelUtils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.expression.Expression.*
import satify.model.tree.expression.{And, Expression, Not, Or}
import satify.model.tree.*
import satify.update.converters.TseitinTransformation.symbolsReplace

class VariableSubstitutionTest extends AnyFlatSpec with Matchers:

  "In (¬b) the replacement of subexpressions with symbols" should "return only a new variable representing the input expression" in {
    val exp: Expression = Not(expression.Symbol("b"))
    val result: List[(expression.Symbol, Expression)] = symbolsReplace(exp)
    result should contain only (
      (expression.Symbol("X0"), expression.Not(expression.Symbol("b")))
    )
  }

  "In (a ∨ ¬b) the replacement of subexpressions with symbols" should "generate 2 new symbols starting from the ¬b subexpression" in {
    val exp: Expression = Or(expression.Symbol("a"), expression.Not(expression.Symbol("b")))
    val result: List[(expression.Symbol, Expression)] = symbolsReplace(exp)
    val expected: List[(expression.Symbol, Expression)] = List(
      (expression.Symbol("X1"), expression.Not(expression.Symbol("b"))),
      (expression.Symbol("X0"), expression.Or(expression.Symbol("a"), expression.Symbol("X1")))
    )
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: Expression =
      expression.Or(And(expression.Symbol("a"), expression.Not(expression.Symbol("b"))), expression.Symbol("c"))
    val list: List[(expression.Symbol, Expression)] = symbolsReplace(exp)

    val expectedList: List[(expression.Symbol, Expression)] = List(
      (expression.Symbol("X2"), expression.Not(expression.Symbol("b"))),
      (expression.Symbol("X1"), expression.And(expression.Symbol("a"), expression.Symbol("X2"))),
      (expression.Symbol("X0"), expression.Or(expression.Symbol("X1"), expression.Symbol("c")))
    )
    expectedList shouldBe list
  }

  "In ((a ∧ b) ∨ (c ∧ d) ∧ (e ∧ f)) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: Expression =
      And(
        Or(
          expression.And(expression.Symbol("a"), expression.Symbol("b")),
          expression.And(expression.Symbol("c"), expression.Symbol("d"))
        ),
        expression.And(expression.Symbol("e"), expression.Symbol("f"))
      )

    val result: List[(expression.Symbol, Expression)] = symbolsReplace(exp)
    val expected: List[(expression.Symbol, Expression)] = List(
      (expression.Symbol("X4"), expression.And(expression.Symbol("e"), expression.Symbol("f"))),
      (expression.Symbol("X3"), expression.And(expression.Symbol("c"), expression.Symbol("d"))),
      (expression.Symbol("X2"), expression.And(expression.Symbol("a"), expression.Symbol("b"))),
      (expression.Symbol("X1"), expression.Or(expression.Symbol("X2"), expression.Symbol("X3"))),
      (expression.Symbol("X0"), expression.And(expression.Symbol("X1"), expression.Symbol("X4")))
    )
    result shouldBe expected
  }

  "In ((a ∧ (a ∨ b)) ∨ (¬c ∧ d)) the replacement of subexpressions with variables" should "follow the nesting level and considering the not subexpression" in {
    val exp: Expression =
      Or(
        expression.And(expression.Symbol("a"), expression.Or(expression.Symbol("a"), expression.Symbol("b"))),
        expression.And(expression.Not(expression.Symbol("c")), expression.Symbol("d"))
      )

    val result: List[(expression.Symbol, Expression)] = symbolsReplace(exp)
    val expected: List[(expression.Symbol, Expression)] = List(
      (expression.Symbol("X4"), expression.Not(expression.Symbol("c"))),
      (expression.Symbol("X3"), expression.And(expression.Symbol("X4"), expression.Symbol("d"))),
      (expression.Symbol("X2"), expression.Or(expression.Symbol("a"), expression.Symbol("b"))),
      (expression.Symbol("X1"), expression.And(expression.Symbol("a"), expression.Symbol("X2"))),
      (expression.Symbol("X0"), expression.Or(expression.Symbol("X1"), expression.Symbol("X3")))
    )
    result shouldBe expected
  }
