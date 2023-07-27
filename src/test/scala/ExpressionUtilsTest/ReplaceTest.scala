package EmptyModelUtilsTest

import model.{EmptyExpression, NamedVariable}
import model.Expression.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class ReplaceTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with X0" in {
    val exp: EmptyExpression = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val substitution: EmptyExpression = Not(Symbol(NamedVariable("b")))
    val expected: EmptyExpression = Or(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("X0"))), Symbol(NamedVariable("c")))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    println(result)
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp (a ∧ ¬b)" should "be replaced with X0" in {
    val exp: EmptyExpression = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val substitution: EmptyExpression = And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b"))))
    val expected: EmptyExpression = Or(Symbol(NamedVariable("X0")), Symbol(NamedVariable("c")))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    expected shouldBe result
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ((a ∧ ¬b) ∨ c)" should "be replaced with X0" in {
    val exp: EmptyExpression = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val substitution: EmptyExpression = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val expected: EmptyExpression = Symbol(NamedVariable("X0"))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    expected shouldBe result
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with X0 and then in ((a ∧ X0) ∨ c) the subexp (a ∧ X0) with X1" in {
    val expression: EmptyExpression = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val sub1: EmptyExpression = Not(Symbol(NamedVariable("b")))
    val exp1: EmptyExpression = Or(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("X0"))), Symbol(NamedVariable("c")))
    val res1 = replace(expression, sub1, Symbol(NamedVariable("X0")))
    exp1 shouldBe res1
    val sub2: EmptyExpression = And(Symbol(NamedVariable("a")), Symbol(NamedVariable("X0")))
    val exp2: EmptyExpression = Or(Symbol(NamedVariable("X1")), Symbol(NamedVariable("c")))
    val res2 = replace(exp1, sub2, Symbol(NamedVariable("X1")))
    exp2 shouldBe res2
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp (¬b ∧ ¬b)" should "be replaced with X0" in {
    val exp: EmptyExpression = Or(And(Not(Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("b")))), Not(Symbol(NamedVariable("b"))))
    val substitution: EmptyExpression = And(Not(Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("b"))))
    val expected: EmptyExpression = Or(Symbol(NamedVariable("X0")), Not(Symbol(NamedVariable("b"))))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    expected shouldBe result
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with X0" in {
    val exp: EmptyExpression = Or(And(Not(Symbol(NamedVariable("b"))), Not(Symbol(NamedVariable("b")))), Not(Symbol(NamedVariable("b"))))
    val substitution: EmptyExpression = Not(Symbol(NamedVariable("b")))
    val expected: EmptyExpression = Or(And(Symbol(NamedVariable("X0")), Symbol(NamedVariable("X0"))), Symbol(NamedVariable("X0")))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    expected shouldBe result
  }

  "In ((a ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with X0" in {
    val exp: EmptyExpression = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Not(Symbol(NamedVariable("b"))))
    val substitution: EmptyExpression = Not(Symbol(NamedVariable("b")))
    val expected: EmptyExpression = Or(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("X0"))), Symbol(NamedVariable("X0")))
    val result = replace(exp, substitution, Symbol(NamedVariable("X0")))
    expected shouldBe result
  }
