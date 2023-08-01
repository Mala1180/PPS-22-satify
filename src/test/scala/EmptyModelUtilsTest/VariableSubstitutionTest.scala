package EmptyModelUtilsTest

import model.{EmptyModel, NamedVariable}
import model.Expression.*
import update.converters.TseitinTransformation.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class VariableSubstitutionTest extends AnyFlatSpec with Matchers:

  "In (¬b) the replacement of subexpressions with symbols" should "return only a new variable representing the input expression" in {
    val exp: EmptyModel = Not(Symbol(NamedVariable("b")))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = symbolsReplace(exp)
    result should contain only (
      (Symbol(NamedVariable("X0")), Not(Symbol(NamedVariable("b"))))
    )
  }

  "In (a ∨ ¬b) the replacement of subexpressions with symbols" should "generate 2 new symbols starting from the ¬b subexpression" in {
    val exp: EmptyModel = Or(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b"))))
    val result: List[(Symbol[NamedVariable], EmptyModel)] = symbolsReplace(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X1")), Not(Symbol(NamedVariable("b")))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("a")), Symbol(NamedVariable("X1"))))
    )
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val list: List[(Symbol[NamedVariable], EmptyModel)] = symbolsReplace(exp)

    val expectedList: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X2")), Not(Symbol(NamedVariable("b")))),
      (Symbol(NamedVariable("X1")), And(Symbol(NamedVariable("a")), Symbol(NamedVariable("X2")))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("X1")), Symbol(NamedVariable("c"))))
    )
    expectedList shouldBe list
  }

  "In ((a ∧ b) ∨ (c ∧ d) ∧ (e ∧ f)) the replacement of subexpressions with variables" should "follow the nesting level" in {
    val exp: EmptyModel =
      And(
        Or(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))), And(Symbol(NamedVariable("c")), Symbol(NamedVariable("d")))),
        And(Symbol(NamedVariable("e")), Symbol(NamedVariable("f")))
      )

    val result: List[(Symbol[NamedVariable], EmptyModel)] = symbolsReplace(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X4")), And(Symbol(NamedVariable("e")), Symbol(NamedVariable("f")))),
      (Symbol(NamedVariable("X3")), And(Symbol(NamedVariable("c")), Symbol(NamedVariable("d")))),
      (Symbol(NamedVariable("X2")), And(Symbol(NamedVariable("a")), Symbol(NamedVariable("b")))),
      (Symbol(NamedVariable("X1")), Or(Symbol(NamedVariable("X2")), Symbol(NamedVariable("X3")))),
      (Symbol(NamedVariable("X0")), And(Symbol(NamedVariable("X1")), Symbol(NamedVariable("X4"))))
    )
    result shouldBe expected
  }

  "In ((a ∧ (a ∨ b)) ∨ (¬c ∧ d)) the replacement of subexpressions with variables" should "follow the nesting level and considering the not subexpression" in {
    val exp: EmptyModel =
      Or(
        And(Symbol(NamedVariable("a")), Or(Symbol(NamedVariable("a")), Symbol(NamedVariable("b")))),
        And(Not(Symbol(NamedVariable("c"))), Symbol(NamedVariable("d")))
      )

    val result: List[(Symbol[NamedVariable], EmptyModel)] = symbolsReplace(exp)
    val expected: List[(Symbol[NamedVariable], EmptyModel)] = List(
      (Symbol(NamedVariable("X4")), Not(Symbol(NamedVariable("c")))),
      (Symbol(NamedVariable("X3")), And(Symbol(NamedVariable("X4")), Symbol(NamedVariable("d")))),
      (Symbol(NamedVariable("X2")), Or(Symbol(NamedVariable("a")), Symbol(NamedVariable("b")))),
      (Symbol(NamedVariable("X1")), And(Symbol(NamedVariable("a")), Symbol(NamedVariable("X2")))),
      (Symbol(NamedVariable("X0")), Or(Symbol(NamedVariable("X1")), Symbol(NamedVariable("X3"))))
    )
    result shouldBe expected
  }
