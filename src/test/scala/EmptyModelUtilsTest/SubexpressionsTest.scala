package EmptyModelUtilsTest

import model.{EmptyModel, NamedVariable}
import model.Expression.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SubexpressionsTest extends AnyFlatSpec with Matchers:

  "The exp: ((a ∧ ¬b) ∨ c)" should "be decomposed correctly in 3 subexpressions following the nesting level" in {
    val exp: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val result = zipWithSymbol(exp)
    val expected = List(
        (Symbol(NamedVariable("X0")), Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))),
        (Symbol(NamedVariable("X1")), And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b"))))),
        (Symbol(NamedVariable("X2")), Not(Symbol(NamedVariable("b"))))
    )
    result shouldBe expected
  }

  "The exp: a" should "contain only the subexpression a" in {
    val exp: EmptyModel = Symbol(NamedVariable("a"))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol(NamedVariable("X0")), Symbol(NamedVariable("a")))
    )
    result shouldBe expected
  }

  "The exp: ¬a" should "contain only the subexpression ¬a" in {
    val exp: EmptyModel = Not(Symbol(NamedVariable("a")))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol(NamedVariable("X0")), Not(Symbol(NamedVariable("a"))))
    )
    result shouldBe expected
  }

  "The exp: (c ∧ (a ∧ ¬b))" should "be decomposed correctly" in {
    val exp: EmptyModel = And(Symbol(NamedVariable("c")), And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol(NamedVariable("X0")), And(Symbol(NamedVariable("c")), And(Symbol(NamedVariable("a")),
        Not(Symbol(NamedVariable("b")))))),
      (Symbol(NamedVariable("X1")), And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b"))))),
      (Symbol(NamedVariable("X2")), Not(Symbol(NamedVariable("b"))))
    )
    result shouldBe expected
  }

  "The exp: (c ∧ a) contains only the subexp witch is equal to the exp itself and" should "be decomposed anyway" in {
    val exp: EmptyModel = And(Symbol(NamedVariable("c")), Symbol(NamedVariable("a")))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol(NamedVariable("X0")), And(Symbol(NamedVariable("c")), Symbol(NamedVariable("a"))))
    )
    result shouldBe expected
  }

  "The exp: (¬(p ∧ q) ∨ (r ∨ s))" should "be decomposed in 4 subexpressions following the nesting level" in {
    val exp: EmptyModel = Or(Not(And(Symbol(NamedVariable("p")), Symbol(NamedVariable("q")))),
      Or(Symbol(NamedVariable("r")), Symbol(NamedVariable("s"))))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol(NamedVariable("X0")), Or(Not(And(Symbol(NamedVariable("p")), Symbol(NamedVariable("q")))),
        Or(Symbol(NamedVariable("r")), Symbol(NamedVariable("s"))))),
      (Symbol(NamedVariable("X1")), Not(And(Symbol(NamedVariable("p")), Symbol(NamedVariable("q"))))),
      (Symbol(NamedVariable("X2")), And(Symbol(NamedVariable("p")), Symbol(NamedVariable("q")))),
      (Symbol(NamedVariable("X3")), Or(Symbol(NamedVariable("r")), Symbol(NamedVariable("s"))))
    )
    result shouldBe expected
  }

  "The exp: ¬(a ∧ b)" should "be decomposed correctly in 2 subexpression: the input one and the one inside the ¬ operator" in {
    val exp: EmptyModel = Not(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol(NamedVariable("X0")), Not(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))))),
      (Symbol(NamedVariable("X1")), And(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))))
    )
    result shouldBe expected
  }

  "The exp: ((a ∨ (b ∧ c)) ∧ d) ∧ (s ∨ t)" should "be decomposed in 5 subexpressions following the nesting level" in {
    val exp: EmptyModel = And(And(Or(Symbol(NamedVariable("a")), And(Symbol(NamedVariable("b")), Symbol(NamedVariable("c")))),
      Symbol(NamedVariable("d"))), Or(Symbol(NamedVariable("s")), Symbol(NamedVariable("t"))))
    val result = zipWithSymbol(exp)
    val expected = List(
      (Symbol(NamedVariable("X0")), And(And(Or(Symbol(NamedVariable("a")), And(Symbol(NamedVariable("b")),
        Symbol(NamedVariable("c")))), Symbol(NamedVariable("d"))), Or(Symbol(NamedVariable("s")), Symbol(NamedVariable("t"))))),
      (Symbol(NamedVariable("X1")), And(Or(Symbol(NamedVariable("a")), And(Symbol(NamedVariable("b")), Symbol(NamedVariable("c")))),
          Symbol(NamedVariable("d")))),
      (Symbol(NamedVariable("X2")), Or(Symbol(NamedVariable("a")), And(Symbol(NamedVariable("b")), Symbol(NamedVariable("c"))))),
      (Symbol(NamedVariable("X3")), And(Symbol(NamedVariable("b")), Symbol(NamedVariable("c")))),
      (Symbol(NamedVariable("X4")), Or(Symbol(NamedVariable("s")), Symbol(NamedVariable("t"))))
    )
    result shouldBe expected
  }
