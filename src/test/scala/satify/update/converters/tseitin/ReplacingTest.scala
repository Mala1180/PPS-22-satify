package satify.update.converters.tseitin

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.update.converters.tseitin.TseitinTransformation.symbolsReplace

class ReplacingTest extends AnyFlatSpec with Matchers:

  "In (¬b) the replacement of subexpressions with symbols" should "return only a new variable representing the input expression" in {
    val exp: Expression = Not(Symbol("b"))
    val result: List[(Symbol, Expression)] = symbolsReplace(exp)
    result should contain only (
      (Symbol("GEN0"), Not(Symbol("b")))
    )
  }

  "In (a ∨ ¬b) the replacement of subexpressions with symbols" should "generate 2 new symbols starting from the ¬b subexpression" in {
    val exp: Expression = Or(Symbol("a"), Not(Symbol("b")))
    val result: List[(Symbol, Expression)] = symbolsReplace(exp)
    val expected: List[(Symbol, Expression)] = List(
      (Symbol("GEN1"), Not(Symbol("b"))),
      (Symbol("GEN0"), Or(Symbol("a"), Symbol("GEN1")))
    )
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val list: List[(Symbol, Expression)] = symbolsReplace(exp)

    val expectedList: List[(Symbol, Expression)] = List(
      (Symbol("GEN2"), Not(Symbol("b"))),
      (Symbol("GEN1"), And(Symbol("a"), Symbol("GEN2"))),
      (Symbol("GEN0"), Or(Symbol("GEN1"), Symbol("c")))
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
      (Symbol("GEN2"), And(Symbol("a"), Symbol("b"))),
      (Symbol("GEN3"), And(Symbol("c"), Symbol("d"))),
      (Symbol("GEN4"), And(Symbol("e"), Symbol("f"))),
      (Symbol("GEN1"), Or(Symbol("GEN2"), Symbol("GEN3"))),
      (Symbol("GEN0"), And(Symbol("GEN1"), Symbol("GEN4")))
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
      (Symbol("GEN2"), Or(Symbol("a"), Symbol("b"))),
      (Symbol("GEN4"), Not(Symbol("c"))),
      (Symbol("GEN1"), And(Symbol("a"), Symbol("GEN2"))),
      (Symbol("GEN3"), And(Symbol("GEN4"), Symbol("d"))),
      (Symbol("GEN0"), Or(Symbol("GEN1"), Symbol("GEN3")))
    )
    result shouldBe expected
  }
