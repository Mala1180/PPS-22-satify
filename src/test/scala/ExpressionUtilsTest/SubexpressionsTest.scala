package ExpressionUtilsTest

import model.Expression
import model.Operator
import Expression.*
import model.Operator.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SubexpressionsTest extends AnyFlatSpec with Matchers:

  "The exp: ((a ∧ ¬b) ∨ c)" should "be decomposed correctly in 3 subexpressions following the nesting level" in {
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val result = zipWithLiteral(exp)
    val expected = List(
      (Literal("X0"), Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))),
      (Literal("X1"), Clause(And(Literal("a"), Clause(Not(Literal("b")))))),
      (Literal("X2"), Clause(Not(Literal("b"))))
    )
    result shouldBe expected
  }

  "The exp: a" should "contain only the subexpression a" in {
    val exp: Expression = Literal("a")
    val result = zipWithLiteral(exp)
    result should contain only (
      (Literal("X0"), Literal("a"))
    )
  }

  "The exp: ¬a" should "contain only the subexpression ¬a" in {
    val exp: Expression = Clause(Not(Literal("a")))
    val result = zipWithLiteral(exp)
    result should contain only (
      (Literal("X0"), Clause(Not(Literal("a"))))
    )
  }

  "The exp: (c ∧ (a ∧ ¬b))" should "be decomposed correctly" in {
    val exp: Expression = Clause(And(Literal("c"), Clause(And(Literal("a"), Clause(Not(Literal("b")))))))
    val result = zipWithLiteral(exp)
    val expected = List(
      (Literal("X0"), Clause(And(Literal("c"), Clause(And(Literal("a"), Clause(Not(Literal("b")))))))),
      (Literal("X1"), Clause(And(Literal("a"), Clause(Not(Literal("b")))))),
      (Literal("X2"), Clause(Not(Literal("b"))))
    )
    result shouldBe expected
  }

  "The exp: (c ∧ a) contains only the subexp witch is equal to the exp itself and" should "be decomposed anyway" in {
    val exp: Expression = Clause(And(Literal("c"), Literal("a")))
    val result = zipWithLiteral(exp)
    result should contain only (
      (Literal("X0"), Clause(And(Literal("c"), Literal("a"))))
    )
  }

  "The exp: (¬(p ∧ q) ∨ (r ∨ s))" should "be decomposed in 4 subexpressions following the nesting level" in {
    val exp: Expression = Clause(Or(Clause(Not(Clause(And(Literal("p"), Literal("q"))))), Clause(Or(Literal("r"), Literal("s")))))
    val result = zipWithLiteral(exp)
    val expected = List(
      (Literal("X0"), Clause(Or(Clause(Not(Clause(And(Literal("p"), Literal("q"))))), Clause(Or(Literal("r"), Literal("s")))))),
      (Literal("X1"), Clause(Not(Clause(And(Literal("p"), Literal("q")))))),
      (Literal("X2"), Clause(And(Literal("p"), Literal("q")))),
      (Literal("X3"), Clause(Or(Literal("r"), Literal("s"))))
    )
    result shouldBe expected
  }

  "The exp: ¬(a ∧ b)" should "be decomposed correctly in 2 subexpression: the input one and the one inside the ¬ operator " in {
    val exp: Expression = Clause(Not(Clause(And(Literal("a"), Literal("b")))))
    val result = zipWithLiteral(exp)
    val expected = List(
      (Literal("X0"), Clause(Not(Clause(And(Literal("a"), Literal("b")))))),
      (Literal("X1"), Clause(And(Literal("a"), Literal("b"))))
    )
    result shouldBe expected
  }

  "The exp: ((a ∨ (b ∧ c)) ∧ d) ∧ (s ∨ t)" should "be decomposed in 5 subexpressions following the nesting level" in {
    val exp: Expression = Clause(
      And(Clause(And(Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c"))))), Literal("d"))), Clause(Or(Literal("s"), Literal("t"))))
    )
    val list = zipWithLiteral(exp)
    val expected = List(
      (Literal("X0"), Clause(And(Clause(And(Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c"))))), Literal("d"))), Clause(Or(Literal("s"), Literal("t")))))),
      (Literal("X1"), Clause(And(Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c"))))), Literal("d")))),
      (Literal("X2"), Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c")))))),
      (Literal("X3"), Clause(And(Literal("b"), Literal("c")))),
      (Literal("X4"), Clause(Or(Literal("s"), Literal("t"))))
    )
    expected shouldBe list
  }
