package satify.model.tree.expression

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree
import satify.model.*
import satify.model.tree.expression.{And, Expression, Not, Or, Symbol}
import satify.model.tree.expression.Expression.map

class MapTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the exp ¬c" should "not be replaced and input exp returned" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val result = map(
      exp,
      {
        case Not(Symbol("c")) => Symbol("X0")
        case e => e
      }
    )
    result shouldBe exp
  }

  "In ((a ∧ ¬b) ∨ c) the exp (a ∧ b)" should "not be replaced and input exp returned" in {
    val exp: Expression = Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val result = map(
      exp,
      {
        case And(Symbol("a"), Symbol("b")) => Symbol("X0")
        case e => e
      }
    )
    result shouldBe exp
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with X0" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val expected: Expression =
      Or(And(Symbol("a"), Symbol("X0")), Symbol("c"))
    val result = map(
      exp,
      {
        case Not(Symbol("b")) => Symbol("X0")
        case e => e
      }
    )
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp (a ∧ ¬b)" should "be replaced with X0" in {
    val exp: Expression = Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val expected: Expression = Or(Symbol("X0"), Symbol("c"))
    val result = map(
      exp,
      {
        case And(Symbol("a"), Not(Symbol("b"))) => Symbol("X0")
        case e => e
      }
    )
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ((a ∧ ¬b) ∨ c)" should "be replaced with X0" in {
    val exp: Expression = Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val expected: Expression = Symbol("X0")
    val result = map(
      exp,
      {
        case Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c")) => Symbol("X0")
        case e => e
      }
    )
    result shouldBe expected
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with X0" in {
    val exp: Expression = Or(And(Not(Symbol("b")), Not(Symbol("b"))), Not(Symbol("b")))
    val expected: Expression = Or(And(Symbol("X0"), Symbol("X0")), Symbol("X0"))
    val result = map(
      exp,
      {
        case Not(Symbol("b")) => Symbol("X0")
        case e => e
      }
    )
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with X0 and then in ((a ∧ X0) ∨ c) the subexp (a ∧ X0) with X1" in {
    val expression: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val exp1: Expression = Or(And(Symbol("a"), Symbol("X0")), Symbol("c"))
    val res1 = map(
      expression,
      {
        case Not(Symbol("b")) => Symbol("X0")
        case e => e
      }
    )
    res1 shouldBe exp1
    val exp2: Expression = Or(Symbol("X1"), Symbol("c"))
    val res2 = map(
      exp1,
      {
        case And(Symbol("a"), Symbol("X0")) => Symbol("X1")
        case e => e
      }
    )
    res2 shouldBe exp2
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp (¬b ∧ ¬b)" should "be replaced with X0" in {
    val exp: Expression = Or(And(Not(Symbol("b")), Not(Symbol("b"))), Not(Symbol("b")))
    val expected: Expression = Or(Symbol("X0"), Not(Symbol("b")))
    val result = map(
      exp,
      {
        case And(Not(Symbol("b")), Not(Symbol("b"))) => Symbol("X0")
        case e => e
      }
    )
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with X0" in {
    val exp: Expression = Or(And(Symbol("a"), Not(Symbol("b"))), Not(Symbol("b")))
    val expected: Expression = Or(And(Symbol("a"), Symbol("X0")), Symbol("X0"))
    val result = map(
      exp,
      {
        case Not(Symbol("b")) => Symbol("X0")
        case e => e
      }
    )
    result shouldBe expected
  }
