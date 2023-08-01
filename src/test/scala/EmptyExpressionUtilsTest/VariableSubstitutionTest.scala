package EmptyExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression.*
import satify.model.{EmptyExpression, EmptyVariable}
import satify.update.converters.TseitinTransformation.symbolsReplace

class VariableSubstitutionTest extends AnyFlatSpec with Matchers:

  "In (¬b) the replacement of subexpressions with symbols" should "return only a new variable representing the input expression" in {
    val exp: EmptyExpression = Not(Symbol(EmptyVariable("b")))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = symbolsReplace(exp)
    result should contain only (
      (Symbol(EmptyVariable("X0")), Not(Symbol(EmptyVariable("b"))))
    )
  }

  "In (a ∨ ¬b) the replacement of subexpressions with symbols" should "generate 2 new symbols starting from the ¬b subexpression" in {
    val exp: EmptyExpression = Or(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b"))))
    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = symbolsReplace(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X1")), Not(Symbol(EmptyVariable("b")))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("X1"))))
    )
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: EmptyExpression = Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
    val list: List[(Symbol[EmptyVariable], EmptyExpression)] = symbolsReplace(exp)

    val expectedList: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X2")), Not(Symbol(EmptyVariable("b")))),
      (Symbol(EmptyVariable("X1")), And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("X2")))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("X1")), Symbol(EmptyVariable("c"))))
    )
    expectedList shouldBe list
  }

  "In ((a ∧ b) ∨ (c ∧ d) ∧ (e ∧ f)) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: EmptyExpression =
      And(
        Or(And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b"))), And(Symbol(EmptyVariable("c")), Symbol(EmptyVariable("d")))),
        And(Symbol(EmptyVariable("e")), Symbol(EmptyVariable("f")))
      )

    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = symbolsReplace(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X4")), And(Symbol(EmptyVariable("e")), Symbol(EmptyVariable("f")))),
      (Symbol(EmptyVariable("X3")), And(Symbol(EmptyVariable("c")), Symbol(EmptyVariable("d")))),
      (Symbol(EmptyVariable("X2")), And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b")))),
      (Symbol(EmptyVariable("X1")), Or(Symbol(EmptyVariable("X2")), Symbol(EmptyVariable("X3")))),
      (Symbol(EmptyVariable("X0")), And(Symbol(EmptyVariable("X1")), Symbol(EmptyVariable("X4"))))
    )
    result shouldBe expected
  }

  "In ((a ∧ (a ∨ b)) ∨ (¬c ∧ d)) the replacement of subexpressions with variables" should "follow the nesting level and considering the not subexpression" in {
    val exp: EmptyExpression =
      Or(
        And(Symbol(EmptyVariable("a")), Or(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b")))),
        And(Not(Symbol(EmptyVariable("c"))), Symbol(EmptyVariable("d")))
      )

    val result: List[(Symbol[EmptyVariable], EmptyExpression)] = symbolsReplace(exp)
    val expected: List[(Symbol[EmptyVariable], EmptyExpression)] = List(
      (Symbol(EmptyVariable("X4")), Not(Symbol(EmptyVariable("c")))),
      (Symbol(EmptyVariable("X3")), And(Symbol(EmptyVariable("X4")), Symbol(EmptyVariable("d")))),
      (Symbol(EmptyVariable("X2")), Or(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b")))),
      (Symbol(EmptyVariable("X1")), And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("X2")))),
      (Symbol(EmptyVariable("X0")), Or(Symbol(EmptyVariable("X1")), Symbol(EmptyVariable("X3"))))
    )
    result shouldBe expected
  }
