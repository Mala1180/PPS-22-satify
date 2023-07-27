package EmptyModelUtilsTest

import model.EmptyModel
import model.Expression.*
import model.NamedVariable
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class SubexpressionsTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be decomposed correctly" in {
    val exp: EmptyModel = Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))), Symbol(NamedVariable("c")))
    val list = zipWithSymbol(exp)
    list should contain only(
        (Symbol(NamedVariable("X2")), Not(Symbol(NamedVariable("b")))),
        (Symbol(NamedVariable("X1")), And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b"))))),
        (Symbol(NamedVariable("X0")), Or(And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b")))),
          Symbol(NamedVariable("c"))))
    )
  }

  "In a the subexp a" should "be decomposed correctly" in {
    val exp: EmptyModel = Symbol(NamedVariable("a"))
    val list = zipWithSymbol(exp)
    list should contain only ((Symbol(NamedVariable("X0")), Symbol(NamedVariable("a"))))
  }

  "In ¬a the subexp ¬a" should "be decomposed correctly" in {
    val exp: EmptyModel = Not(Symbol(NamedVariable("a")))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only ((Symbol(NamedVariable("X0")), Not(Symbol(NamedVariable("a")))))
  }

  "In (c ∧ (a ∧ ¬b)) the subexp ¬b, (a ∧ ¬b) and (c ∧ (a ∧ ¬b))" should "be decomposed correctly" in {
    val exp: EmptyModel = And(Symbol(NamedVariable("c")), And(Symbol(NamedVariable("a")),
      Not(Symbol(NamedVariable("b")))))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only (
      (Symbol(NamedVariable("X0")), And(Symbol(NamedVariable("c")), And(Symbol(NamedVariable("a")),
        Not(Symbol(NamedVariable("b")))))),
      (Symbol(NamedVariable("X1")), And(Symbol(NamedVariable("a")), Not(Symbol(NamedVariable("b"))))),
      (Symbol(NamedVariable("X2")), Not(Symbol(NamedVariable("b"))))
      )
  }

  "In (c ∧ a) the subexp (c ∧ a)" should "be decomposed correctly" in {
    val exp: EmptyModel = And(Symbol(NamedVariable("c")), Symbol(NamedVariable("a")))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only(
      (Symbol(NamedVariable("X0")), And(Symbol(NamedVariable("c")), Symbol(NamedVariable("a"))))
    )
  }

  "In (¬(p ∧ q) ∨ (r ∨ s)) the subexp (r ∨ s), (p ∧ q) and ¬(p ∧ q)" should "be decomposed correctly" in {
    val exp: EmptyModel = Or(Not(And(Symbol(NamedVariable("p")), Symbol(NamedVariable("q")))),
      Or(Symbol(NamedVariable("r")), Symbol(NamedVariable("s"))))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only (
      (Symbol(NamedVariable("X0")), Or(Not(And(Symbol(NamedVariable("p")), Symbol(NamedVariable("q")))),
        Or(Symbol(NamedVariable("r")), Symbol(NamedVariable("s"))))),
      (Symbol(NamedVariable("X1")), Not(And(Symbol(NamedVariable("p")), Symbol(NamedVariable("q"))))),
      (Symbol(NamedVariable("X2")), And(Symbol(NamedVariable("p")), Symbol(NamedVariable("q")))),
      (Symbol(NamedVariable("X3")), Or(Symbol(NamedVariable("r")), Symbol(NamedVariable("s"))))
      )
  }

  "In ¬(a ∧ b) the subexp (a ∧ b) and ¬(a ∧ b)" should "be decomposed correctly" in {
    val exp: EmptyModel = Not(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only(
      (Symbol(NamedVariable("X0")), Not(And(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))))),
      (Symbol(NamedVariable("X1")), And(Symbol(NamedVariable("a")), Symbol(NamedVariable("b"))))
    )
  }

  "In ((a ∨ (b ∧ c)) ∧ d) ∧ (s ∨ t) the subexp (b ∧ c), (a ∨ (b ∧ c))and ¬(a ∧ b)" should "be decomposed correctly" in {
    val exp: EmptyModel = And(And(Or(Symbol(NamedVariable("a")), And(Symbol(NamedVariable("b")), Symbol(NamedVariable("c")))),
      Symbol(NamedVariable("d"))), Or(Symbol(NamedVariable("s")), Symbol(NamedVariable("t"))))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only(
      (Symbol(NamedVariable("X0")), And(And(Or(Symbol(NamedVariable("a")), And(Symbol(NamedVariable("b")),
        Symbol(NamedVariable("c")))), Symbol(NamedVariable("d"))), Or(Symbol(NamedVariable("s")), Symbol(NamedVariable("t"))))),
      (Symbol(NamedVariable("X1")), And(Or(Symbol(NamedVariable("a")), And(Symbol(NamedVariable("b")), Symbol(NamedVariable("c")))),
          Symbol(NamedVariable("d")))),
      (Symbol(NamedVariable("X2")), Or(Symbol(NamedVariable("a")), And(Symbol(NamedVariable("b")), Symbol(NamedVariable("c"))))),
      (Symbol(NamedVariable("X3")), And(Symbol(NamedVariable("b")), Symbol(NamedVariable("c")))),
      (Symbol(NamedVariable("X4")), Or(Symbol(NamedVariable("s")), Symbol(NamedVariable("t"))))
    )
  }
