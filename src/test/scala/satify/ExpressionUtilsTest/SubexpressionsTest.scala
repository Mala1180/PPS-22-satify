package satify.ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression.*
import satify.model.{Expression, Variable}

class SubexpressionsTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be decomposed correctly" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val list = zipWithSymbol(exp)
    list should contain only (
      (Symbol("X2"), Not(Symbol("b"))),
      (Symbol("X1"), And(Symbol("a"), Not(Symbol("b")))),
      (
        Symbol("X0"),
        Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
      )
    )
  }

  "In a the subexp a" should "be decomposed correctly" in {
    val exp: Expression = Symbol("a")
    val list = zipWithSymbol(exp)
    list should contain only ((Symbol("X0"), Symbol("a")))
  }

  "In ¬a the subexp ¬a" should "be decomposed correctly" in {
    val exp: Expression = Not(Symbol("a"))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only ((Symbol("X0"), Not(Symbol("a"))))
  }

  "In (c ∧ (a ∧ ¬b)) the subexp ¬b, (a ∧ ¬b) and (c ∧ (a ∧ ¬b))" should "be decomposed correctly" in {
    val exp: Expression =
      And(Symbol("c"), And(Symbol("a"), Not(Symbol("b"))))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only (
      (
        Symbol("X0"),
        And(Symbol("c"), And(Symbol("a"), Not(Symbol("b"))))
      ),
      (Symbol("X1"), And(Symbol("a"), Not(Symbol("b")))),
      (Symbol("X2"), Not(Symbol("b")))
    )
  }

  "In (c ∧ a) the subexp (c ∧ a)" should "be decomposed correctly" in {
    val exp: Expression = And(Symbol("c"), Symbol("a"))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only (
      (Symbol("X0"), And(Symbol("c"), Symbol("a")))
    )
  }

  "The exp: a" should "contain only the subexpression a" in {
    val exp: Expression = Symbol("a")
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol("X0"), Symbol("a"))
    )
    result shouldBe expected
  }

  "The exp: ¬a" should "contain only the subexpression ¬a" in {
    val exp: Expression = Not(Symbol("a"))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol("X0"), Not(Symbol("a")))
    )
    result shouldBe expected
  }

  "The exp: (c ∧ (a ∧ ¬b))" should "be decomposed correctly" in {
    val exp: Expression =
      And(Symbol("c"), And(Symbol("a"), Not(Symbol("b"))))
    val result = zipWithSymbol(exp)
    val expected = List(
      (
        Symbol("X0"),
        And(Symbol("c"), And(Symbol("a"), Not(Symbol("b"))))
      ),
      (Symbol("X1"), And(Symbol("a"), Not(Symbol("b")))),
      (Symbol("X2"), Not(Symbol("b")))
    )
    result shouldBe expected
  }

  "The exp: (c ∧ a) contains only the subexp witch is equal to the exp itself and" should "be decomposed anyway" in {
    val exp: Expression = And(Symbol("c"), Symbol("a"))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol("X0"), And(Symbol("c"), Symbol("a")))
    )
    result shouldBe expected
  }

  "In (¬(p ∧ q) ∨ (r ∨ s)) the subexp (r ∨ s), (p ∧ q) and ¬(p ∧ q)" should "be decomposed correctly" in {
    val exp: Expression = Or(
      Not(And(Symbol("p"), Symbol("q"))),
      Or(Symbol("r"), Symbol("s"))
    )
    val result = zipWithSymbol(exp)
    val expected = List(
      (
        Symbol("X0"),
        Or(
          Not(And(Symbol("p"), Symbol("q"))),
          Or(Symbol("r"), Symbol("s"))
        )
      ),
      (Symbol("X1"), Not(And(Symbol("p"), Symbol("q")))),
      (Symbol("X2"), And(Symbol("p"), Symbol("q"))),
      (Symbol("X3"), Or(Symbol("r"), Symbol("s")))
    )
    result shouldBe expected
  }

  "In ¬(a ∧ b) the subexp (a ∧ b) and ¬(a ∧ b)" should "be decomposed correctly" in {
    val exp: Expression = Not(And(Symbol("a"), Symbol("b")))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol("X0"), Not(And(Symbol("a"), Symbol("b")))),
      (Symbol("X1"), And(Symbol("a"), Symbol("b")))
    )
    result shouldBe expected
  }

  "The (¬(p ∧ q) ∨ (r ∨ s))" should "be decomposed in 4 subexpressions following the nesting level" in {
    val exp: Expression = Or(
      Not(And(Symbol("p"), Symbol("q"))),
      Or(Symbol("r"), Symbol("s"))
    )
    val result = zipWithSymbol(exp)
    val expected = List(
      (
        Symbol("X0"),
        Or(
          Not(And(Symbol("p"), Symbol("q"))),
          Or(Symbol("r"), Symbol("s"))
        )
      ),
      (Symbol("X1"), Not(And(Symbol("p"), Symbol("q")))),
      (Symbol("X2"), And(Symbol("p"), Symbol("q"))),
      (Symbol("X3"), Or(Symbol("r"), Symbol("s")))
    )
    result shouldBe expected
  }

  "The exp: ¬(a ∧ b)" should "be decomposed correctly in 2 subexpression: the input one and the one inside the ¬ operator" in {
    val exp: Expression = Not(And(Symbol("a"), Symbol("b")))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol("X0"), Not(And(Symbol("a"), Symbol("b")))),
      (Symbol("X1"), And(Symbol("a"), Symbol("b")))
    )
    result shouldBe expected
  }

  "((a ∨ (b ∧ c)) ∧ d) ∧ (s ∨ t)" should "be decomposed in 5 subexpressions following the nesting level" in {
    val exp: Expression = And(
      And(
        Or(Symbol("a"), And(Symbol("b"), Symbol("c"))),
        Symbol("d")
      ),
      Or(Symbol("s"), Symbol("t"))
    )
    val result = zipWithSymbol(exp)
    val expected = List(
      (
        Symbol("X0"),
        And(
          And(
            Or(Symbol("a"), And(Symbol("b"), Symbol("c"))),
            Symbol("d")
          ),
          Or(Symbol("s"), Symbol("t"))
        )
      ),
      (
        Symbol("X1"),
        And(
          Or(Symbol("a"), And(Symbol("b"), Symbol("c"))),
          Symbol("d")
        )
      ),
      (
        Symbol("X2"),
        Or(Symbol("a"), And(Symbol("b"), Symbol("c")))
      ),
      (Symbol("X3"), And(Symbol("b"), Symbol("c"))),
      (Symbol("X4"), Or(Symbol("s"), Symbol("t")))
    )
    result shouldBe expected
  }
