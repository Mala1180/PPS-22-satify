package satify.modelUtils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.expression.Expression.*
import satify.model.tree.expression.{And, Expression, Not, Or}
import satify.model.tree.*

class SubexpressionsTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be decomposed correctly" in {
    val exp: Expression =
      Or(And(expression.Symbol("a"), Not(expression.Symbol("b"))), expression.Symbol("c"))
    val list = zipWithSymbol(exp)
    list should contain only (
      (expression.Symbol("X2"), expression.Not(expression.Symbol("b"))),
      (expression.Symbol("X1"), expression.And(expression.Symbol("a"), expression.Not(expression.Symbol("b")))),
      (
        expression.Symbol("X0"),
        expression.Or(
          expression.And(expression.Symbol("a"), expression.Not(expression.Symbol("b"))),
          expression.Symbol("c")
        )
      )
    )
  }

  "In a the subexp a" should "be decomposed correctly" in {
    val exp: Expression = expression.Symbol("a")
    val list = zipWithSymbol(exp)
    list should contain only ((expression.Symbol("X0"), expression.Symbol("a")))
  }

  "In ¬a the subexp ¬a" should "be decomposed correctly" in {
    val exp: Expression = expression.Not(expression.Symbol("a"))
    val list = zipWithSymbol(exp)
    list should contain only ((expression.Symbol("X0"), expression.Not(expression.Symbol("a"))))
  }

  "In (c ∧ (a ∧ ¬b)) the subexp ¬b, (a ∧ ¬b) and (c ∧ (a ∧ ¬b))" should "be decomposed correctly" in {
    val exp: Expression =
      expression.And(
        expression.Symbol("c"),
        expression.And(expression.Symbol("a"), expression.Not(expression.Symbol("b")))
      )
    val list = zipWithSymbol(exp)
    list should contain only (
      (
        expression.Symbol("X0"),
        expression.And(
          expression.Symbol("c"),
          expression.And(expression.Symbol("a"), expression.Not(expression.Symbol("b")))
        )
      ),
      (expression.Symbol("X1"), expression.And(expression.Symbol("a"), expression.Not(expression.Symbol("b")))),
      (expression.Symbol("X2"), expression.Not(expression.Symbol("b")))
    )
  }

  "In (c ∧ a) the subexp (c ∧ a)" should "be decomposed correctly" in {
    val exp: Expression = expression.And(expression.Symbol("c"), expression.Symbol("a"))
    val list = zipWithSymbol(exp)
    list should contain only (
      (expression.Symbol("X0"), expression.And(expression.Symbol("c"), expression.Symbol("a")))
    )
  }

  "The exp: a" should "contain only the subexpression a" in {
    val exp: Expression = expression.Symbol("a")
    val result = zipWithSymbol(exp)
    val expected = List(
      (expression.Symbol("X0"), expression.Symbol("a"))
    )
    result shouldBe expected
  }

  "The exp: ¬a" should "contain only the subexpression ¬a" in {
    val exp: Expression = expression.Not(expression.Symbol("a"))
    val result = zipWithSymbol(exp)
    val expected = List(
      (expression.Symbol("X0"), expression.Not(expression.Symbol("a")))
    )
    result shouldBe expected
  }

  "The exp: (c ∧ (a ∧ ¬b))" should "be decomposed correctly" in {
    val exp: Expression =
      expression.And(
        expression.Symbol("c"),
        expression.And(expression.Symbol("a"), expression.Not(expression.Symbol("b")))
      )
    val result = zipWithSymbol(exp)
    val expected = List(
      (
        expression.Symbol("X0"),
        expression
          .And(expression.Symbol("c"), expression.And(expression.Symbol("a"), expression.Not(expression.Symbol("b"))))
      ),
      (expression.Symbol("X1"), expression.And(expression.Symbol("a"), expression.Not(expression.Symbol("b")))),
      (expression.Symbol("X2"), expression.Not(expression.Symbol("b")))
    )
    result shouldBe expected
  }

  "The exp: (c ∧ a) contains only the subexp witch is equal to the exp itself and" should "be decomposed anyway" in {
    val exp: Expression = expression.And(expression.Symbol("c"), expression.Symbol("a"))
    val result = zipWithSymbol(exp)
    val expected = List(
      (expression.Symbol("X0"), expression.And(expression.Symbol("c"), expression.Symbol("a")))
    )
    result shouldBe expected
  }

  "In (¬(p ∧ q) ∨ (r ∨ s)) the subexp (r ∨ s), (p ∧ q) and ¬(p ∧ q)" should "be decomposed correctly" in {
    val exp: Expression = Or(
      Not(expression.And(expression.Symbol("p"), expression.Symbol("q"))),
      expression.Or(expression.Symbol("r"), expression.Symbol("s"))
    )
    val result = zipWithSymbol(exp)
    val expected = List(
      (
        expression.Symbol("X0"),
        Or(
          Not(expression.And(expression.Symbol("p"), expression.Symbol("q"))),
          expression.Or(expression.Symbol("r"), expression.Symbol("s"))
        )
      ),
      (expression.Symbol("X1"), Not(expression.And(expression.Symbol("p"), expression.Symbol("q")))),
      (expression.Symbol("X2"), expression.And(expression.Symbol("p"), expression.Symbol("q"))),
      (expression.Symbol("X3"), expression.Or(expression.Symbol("r"), expression.Symbol("s")))
    )
    result shouldBe expected
  }

  "In ¬(a ∧ b) the subexp (a ∧ b) and ¬(a ∧ b)" should "be decomposed correctly" in {
    val exp: Expression = Not(expression.And(expression.Symbol("a"), expression.Symbol("b")))
    val result = zipWithSymbol(exp)
    val expected = List(
      (expression.Symbol("X0"), Not(expression.And(expression.Symbol("a"), expression.Symbol("b")))),
      (expression.Symbol("X1"), expression.And(expression.Symbol("a"), expression.Symbol("b")))
    )
    result shouldBe expected
  }

  "The (¬(p ∧ q) ∨ (r ∨ s))" should "be decomposed in 4 subexpressions following the nesting level" in {
    val exp: Expression = Or(
      Not(expression.And(expression.Symbol("p"), expression.Symbol("q"))),
      expression.Or(expression.Symbol("r"), expression.Symbol("s"))
    )
    val result = zipWithSymbol(exp)
    val expected = List(
      (
        expression.Symbol("X0"),
        Or(
          Not(expression.And(expression.Symbol("p"), expression.Symbol("q"))),
          expression.Or(expression.Symbol("r"), expression.Symbol("s"))
        )
      ),
      (expression.Symbol("X1"), Not(expression.And(expression.Symbol("p"), expression.Symbol("q")))),
      (expression.Symbol("X2"), expression.And(expression.Symbol("p"), expression.Symbol("q"))),
      (expression.Symbol("X3"), expression.Or(expression.Symbol("r"), expression.Symbol("s")))
    )
    result shouldBe expected
  }

  "The exp: ¬(a ∧ b)" should "be decomposed correctly in 2 subexpression: the input one and the one inside the ¬ operator" in {
    val exp: Expression = Not(expression.And(expression.Symbol("a"), expression.Symbol("b")))
    val result = zipWithSymbol(exp)
    val expected = List(
      (expression.Symbol("X0"), Not(expression.And(expression.Symbol("a"), expression.Symbol("b")))),
      (expression.Symbol("X1"), expression.And(expression.Symbol("a"), expression.Symbol("b")))
    )
    result shouldBe expected
  }

  "((a ∨ (b ∧ c)) ∧ d) ∧ (s ∨ t)" should "be decomposed in 5 subexpressions following the nesting level" in {
    val exp: Expression = And(
      expression.And(
        expression.Or(expression.Symbol("a"), expression.And(expression.Symbol("b"), expression.Symbol("c"))),
        expression.Symbol("d")
      ),
      expression.Or(expression.Symbol("s"), expression.Symbol("t"))
    )
    val result = zipWithSymbol(exp)
    val expected = List(
      (
        expression.Symbol("X0"),
        And(
          expression.And(
            expression.Or(expression.Symbol("a"), expression.And(expression.Symbol("b"), expression.Symbol("c"))),
            expression.Symbol("d")
          ),
          expression.Or(expression.Symbol("s"), expression.Symbol("t"))
        )
      ),
      (
        expression.Symbol("X1"),
        expression.And(
          expression.Or(expression.Symbol("a"), expression.And(expression.Symbol("b"), expression.Symbol("c"))),
          expression.Symbol("d")
        )
      ),
      (
        expression.Symbol("X2"),
        expression.Or(expression.Symbol("a"), expression.And(expression.Symbol("b"), expression.Symbol("c")))
      ),
      (expression.Symbol("X3"), expression.And(expression.Symbol("b"), expression.Symbol("c"))),
      (expression.Symbol("X4"), expression.Or(expression.Symbol("s"), expression.Symbol("t")))
    )
    result shouldBe expected
  }
