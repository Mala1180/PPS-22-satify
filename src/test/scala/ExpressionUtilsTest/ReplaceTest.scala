package ExpressionUtilsTest

import model.Expression
import model.Operator
import Expression.*
import model.Operator.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReplaceTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the exp ¬c" should "not be replaced and input exp returned" in {
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val substitution: Expression = Clause(Not(Literal("c")))
    val expected: Expression = Clause(Or(Clause(And(Literal("a"), Literal("X0"))), Literal("c")))
    val result = replace(exp, substitution, Literal("X0"))
    result shouldBe exp
  }

  "In ((a ∧ ¬b) ∨ c) the exp (a ∧ b)" should "not be replaced and input exp returned" in {
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val substitution: Expression = Clause(And(Literal("a"), Literal("b")))
    val expected: Expression = Clause(Or(Clause(And(Literal("a"), Literal("X0"))), Literal("c")))
    val result = replace(exp, substitution, Literal("X0"))
    result shouldBe exp
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with X0" in {
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val substitution: Expression = Clause(Not(Literal("b")))
    val expected: Expression = Clause(Or(Clause(And(Literal("a"), Literal("X0"))), Literal("c")))
    val result = replace(exp, substitution, Literal("X0"))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp (a ∧ ¬b)" should "be replaced with X0" in {
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val substitution: Expression = Clause(And(Literal("a"), Clause(Not(Literal("b")))))
    val expected: Expression = Clause(Or(Literal("X0"), Literal("c")))
    val result = replace(exp, substitution, Literal("X0"))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ((a ∧ ¬b) ∨ c)" should "be replaced with X0" in {
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val substitution: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val expected: Expression = Literal("X0")
    val result = replace(exp, substitution, Literal("X0"))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be replaced with X0 and then in ((a ∧ X0) ∨ c) the subexp (a ∧ X0) with X1" in {
    val expression: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val sub1: Expression = Clause(Not(Literal("b")))
    val exp1: Expression = Clause(Or(Clause(And(Literal("a"), Literal("X0"))), Literal("c")))
    val res1 = replace(expression, sub1, Literal("X0"))
    res1 shouldBe exp1
    val sub2: Expression = Clause(And(Literal("a"), Literal("X0")))
    val exp2: Expression = Clause(Or(Literal("X1"), Literal("c")))
    val res2 = replace(exp1, sub2, Literal("X1"))
    res2 shouldBe exp2
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp (¬b ∧ ¬b)" should "be replaced with X0" in {
    val exp: Expression =
      Clause(Or(Clause(And(Clause(Not(Literal("b"))), Clause(Not(Literal("b"))))), Clause(Not(Literal("b")))))
    val substitution: Expression = Clause(And(Clause(Not(Literal("b"))), Clause(Not(Literal("b")))))
    val expected: Expression = Clause(Or(Literal("X0"), Clause(Not(Literal("b")))))
    val result = replace(exp, substitution, Literal("X0"))
    result shouldBe expected
  }

  "In ((¬b ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with X0" in {
    val exp: Expression =
      Clause(Or(Clause(And(Clause(Not(Literal("b"))), Clause(Not(Literal("b"))))), Clause(Not(Literal("b")))))
    val substitution: Expression = Clause(Not(Literal("b")))
    val expected: Expression = Clause(Or(Clause(And(Literal("X0"), Literal("X0"))), Literal("X0")))
    val result = replace(exp, substitution, Literal("X0"))
    result shouldBe expected
  }

  "In ((a ∧ ¬b) ∨ ¬b) the subexp ¬b" should "be replaced with X0" in {
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Clause(Not(Literal("b")))))
    val substitution: Expression = Clause(Not(Literal("b")))
    val expected: Expression = Clause(Or(Clause(And(Literal("a"), Literal("X0"))), Literal("X0")))
    val result = replace(exp, substitution, Literal("X0"))
    result shouldBe expected
  }
