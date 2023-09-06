package satify.update.converters

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.{Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.{CNF, Variable}
import satify.update.converters.TseitinTransformation.transform

class TseitinTransformationTest extends AnyFlatSpec with Matchers:

  "The transformation of ¬(a ∨ b)" should "throw an exception" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), Not(Or(Symbol("a"), Symbol("b"))))
    the[IllegalArgumentException] thrownBy transform(exp) should have message "Expression is not a Symbol or a Not"
  }

  "The transformation of ((a ∨ c) ∨ (b ∨ d))" should "throw an exception" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), Or(Or(Symbol("a"), Symbol("c")), Or(Symbol("b"), Symbol("d"))))
    the[IllegalArgumentException] thrownBy transform(exp) should have message "Expression is not a Symbol or a Not"
  }

  "The transformation of ¬b" should "return a list of CNF clauses relative to the ¬ operator" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), Not(Symbol("b")))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("TSTN0"))))
      ),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("TSTN0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("TSTN0"), And(Symbol("a"), Symbol("b")))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(
          CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("b")))),
          CNFSymbol(Variable("TSTN0"))
        )
      ),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("TSTN0"))))),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFSymbol(Variable("b")), CNFNot(CNFSymbol(Variable("TSTN0")))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("TSTN0"), And(Not(Symbol("a")), Symbol("b")))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(
          CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("b")))),
          CNFSymbol(Variable("TSTN0"))
        )
      ),
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("TSTN0"))))
      ),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFSymbol(Variable("b")), CNFNot(CNFSymbol(Variable("TSTN0")))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("TSTN0"), And(Symbol("a"), Not(Symbol("b"))))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(
          CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFSymbol(Variable("b"))),
          CNFSymbol(Variable("TSTN0"))
        )
      ),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("TSTN0"))))),
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("TSTN0"))))
      )
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("TSTN0"), And(Not(Symbol("a")), Not(Symbol("b"))))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("b"))), CNFSymbol(Variable("TSTN0")))
      ),
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("TSTN0"))))
      ),
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("TSTN0"))))
      )
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("TSTN0"), Or(Symbol("a"), Symbol("b")))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(
          CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("b"))),
          CNFNot(CNFSymbol(Variable("TSTN0")))
        )
      ),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFSymbol(Variable("TSTN0")))),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFSymbol(Variable("TSTN0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("TSTN0"), Or(Not(Symbol("a")), Symbol("b")))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(
          CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFSymbol(Variable("b"))),
          CNFNot(CNFSymbol(Variable("TSTN0")))
        )
      ),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("TSTN0")))),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFSymbol(Variable("TSTN0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("TSTN0"), Or(Symbol("a"), Not(Symbol("b"))))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(
          CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("b")))),
          CNFNot(CNFSymbol(Variable("TSTN0")))
        )
      ),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFSymbol(Variable("TSTN0")))),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("TSTN0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("TSTN0"), Or(Not(Symbol("a")), Not(Symbol("b"))))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("TSTN0")),
        CNFOr(
          CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("b")))),
          CNFNot(CNFSymbol(Variable("TSTN0")))
        )
      ),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("TSTN0")))),
      (CNFSymbol(Variable("TSTN0")), CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("TSTN0"))))
    )
    result shouldBe expected
  }
