package satify.ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression.*
import satify.model.{EmptyExpression, EmptyVariable}

class SubexpressionsTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be decomposed correctly" in {
    val exp: EmptyExpression =
      Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
    val list = zipWithSymbol(exp)
    list should contain only (
      (Symbol(EmptyVariable("X2")), Not(Symbol(EmptyVariable("b")))),
      (Symbol(EmptyVariable("X1")), And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b"))))),
      (
        Symbol(EmptyVariable("X0")),
        Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
      )
    )
  }

  "In a the subexp a" should "be decomposed correctly" in {
    val exp: EmptyExpression = Symbol(EmptyVariable("a"))
    val list = zipWithSymbol(exp)
    list should contain only ((Symbol(EmptyVariable("X0")), Symbol(EmptyVariable("a"))))
  }

  "In ¬a the subexp ¬a" should "be decomposed correctly" in {
    val exp: EmptyExpression = Not(Symbol(EmptyVariable("a")))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only ((Symbol(EmptyVariable("X0")), Not(Symbol(EmptyVariable("a")))))
  }

  "In (c ∧ (a ∧ ¬b)) the subexp ¬b, (a ∧ ¬b) and (c ∧ (a ∧ ¬b))" should "be decomposed correctly" in {
    val exp: EmptyExpression =
      And(Symbol(EmptyVariable("c")), And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only (
      (
        Symbol(EmptyVariable("X0")),
        And(Symbol(EmptyVariable("c")), And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))))
      ),
      (Symbol(EmptyVariable("X1")), And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b"))))),
      (Symbol(EmptyVariable("X2")), Not(Symbol(EmptyVariable("b"))))
    )
  }

  "In (c ∧ a) the subexp (c ∧ a)" should "be decomposed correctly" in {
    val exp: EmptyExpression = And(Symbol(EmptyVariable("c")), Symbol(EmptyVariable("a")))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only (
      (Symbol(EmptyVariable("X0")), And(Symbol(EmptyVariable("c")), Symbol(EmptyVariable("a"))))
    )
  }

  "In (¬(p ∧ q) ∨ (r ∨ s)) the subexp (r ∨ s), (p ∧ q) and ¬(p ∧ q)" should "be decomposed correctly" in {
    val exp: EmptyExpression = Or(
      Not(And(Symbol(EmptyVariable("p")), Symbol(EmptyVariable("q")))),
      Or(Symbol(EmptyVariable("r")), Symbol(EmptyVariable("s")))
    )
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only (
      (
        Symbol(EmptyVariable("X0")),
        Or(
          Not(And(Symbol(EmptyVariable("p")), Symbol(EmptyVariable("q")))),
          Or(Symbol(EmptyVariable("r")), Symbol(EmptyVariable("s")))
        )
      ),
      (Symbol(EmptyVariable("X1")), Not(And(Symbol(EmptyVariable("p")), Symbol(EmptyVariable("q"))))),
      (Symbol(EmptyVariable("X2")), And(Symbol(EmptyVariable("p")), Symbol(EmptyVariable("q")))),
      (Symbol(EmptyVariable("X3")), Or(Symbol(EmptyVariable("r")), Symbol(EmptyVariable("s"))))
    )
  }

  "In ¬(a ∧ b) the subexp (a ∧ b) and ¬(a ∧ b)" should "be decomposed correctly" in {
    val exp: EmptyExpression = Not(And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b"))))
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only (
      (Symbol(EmptyVariable("X0")), Not(And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b"))))),
      (Symbol(EmptyVariable("X1")), And(Symbol(EmptyVariable("a")), Symbol(EmptyVariable("b"))))
    )
  }

  "In ((a ∨ (b ∧ c)) ∧ d) ∧ (s ∨ t) the subexp (b ∧ c), (a ∨ (b ∧ c))and ¬(a ∧ b)" should "be decomposed correctly" in {
    val exp: EmptyExpression = And(
      And(
        Or(Symbol(EmptyVariable("a")), And(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("c")))),
        Symbol(EmptyVariable("d"))
      ),
      Or(Symbol(EmptyVariable("s")), Symbol(EmptyVariable("t")))
    )
    val list = zipWithSymbol(exp)
    println(list)
    list should contain only (
      (
        Symbol(EmptyVariable("X0")),
        And(
          And(
            Or(Symbol(EmptyVariable("a")), And(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("c")))),
            Symbol(EmptyVariable("d"))
          ),
          Or(Symbol(EmptyVariable("s")), Symbol(EmptyVariable("t")))
        )
      ),
      (
        Symbol(EmptyVariable("X1")),
        And(
          Or(Symbol(EmptyVariable("a")), And(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("c")))),
          Symbol(EmptyVariable("d"))
        )
      ),
      (
        Symbol(EmptyVariable("X2")),
        Or(Symbol(EmptyVariable("a")), And(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("c"))))
      ),
      (Symbol(EmptyVariable("X3")), And(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("c")))),
      (Symbol(EmptyVariable("X4")), Or(Symbol(EmptyVariable("s")), Symbol(EmptyVariable("t"))))
    )
  }
