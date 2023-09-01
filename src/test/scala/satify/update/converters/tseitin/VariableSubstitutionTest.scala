package satify.update.converters.tseitin

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.expression.{And, Expression, Not, Or, Symbol}
import satify.update.converters.TseitinTransformation.symbolsReplace

class VariableSubstitutionTest extends AnyFlatSpec with Matchers:

  "In (¬b) the replacement of subexpressions with symbols" should "return only a new variable representing the input expression" in {
    val exp: Expression = Not(Symbol("b"))
    val result: List[(Symbol, Expression)] = symbolsReplace(exp)
    result should contain only (
      (Symbol("X0"), Not(Symbol("b")))
    )
  }

  "In (a ∨ ¬b) the replacement of subexpressions with symbols" should "generate 2 new symbols starting from the ¬b subexpression" in {
    val exp: Expression = Or(Symbol("a"), Not(Symbol("b")))
    val result: List[(Symbol, Expression)] = symbolsReplace(exp)
    val expected: List[(Symbol, Expression)] = List(
      (Symbol("X1"), Not(Symbol("b"))),
      (Symbol("X0"), Or(Symbol("a"), Symbol("X1")))
    )
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val list: List[(Symbol, Expression)] = symbolsReplace(exp)

    val expectedList: List[(Symbol, Expression)] = List(
      (Symbol("X2"), Not(Symbol("b"))),
      (Symbol("X1"), And(Symbol("a"), Symbol("X2"))),
      (Symbol("X0"), Or(Symbol("X1"), Symbol("c")))
    )
    expectedList shouldBe list
  }

  "In ((a ∧ b) ∨ (c ∧ d) ∧ (e ∧ f)) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: Expression =
      And(
        Or(
          And(Symbol("a"), Symbol("b")),
          And(Symbol("c"), Symbol("d"))
        ),
        And(Symbol("e"), Symbol("f"))
      )

    val result: List[(Symbol, Expression)] = symbolsReplace(exp)
    val expected: List[(Symbol, Expression)] = List(
      (Symbol("X4"), And(Symbol("e"), Symbol("f"))),
      (Symbol("X3"), And(Symbol("c"), Symbol("d"))),
      (Symbol("X2"), And(Symbol("a"), Symbol("b"))),
      (Symbol("X1"), Or(Symbol("X2"), Symbol("X3"))),
      (Symbol("X0"), And(Symbol("X1"), Symbol("X4")))
    )
    result shouldBe expected
  }

  "In ((a ∧ (a ∨ b)) ∨ (¬c ∧ d)) the replacement of subexpressions with variables" should "follow the nesting level and considering the not subexpression" in {
    val exp: Expression =
      Or(
        And(Symbol("a"), Or(Symbol("a"), Symbol("b"))),
        And(Not(Symbol("c")), Symbol("d"))
      )

    val result: List[(Symbol, Expression)] = symbolsReplace(exp)
    val expected: List[(Symbol, Expression)] = List(
      (Symbol("X4"), Not(Symbol("c"))),
      (Symbol("X3"), And(Symbol("X4"), Symbol("d"))),
      (Symbol("X2"), Or(Symbol("a"), Symbol("b"))),
      (Symbol("X1"), And(Symbol("a"), Symbol("X2"))),
      (Symbol("X0"), Or(Symbol("X1"), Symbol("X3")))
    )
    result shouldBe expected
  }
