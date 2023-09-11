package satify.update.converters.tseitin

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.CNF.{Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.cnf.CNF
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.update.converters.tseitin.TseitinTransformation.transform

class TransformationTest extends AnyFlatSpec with Matchers:

  "The transformation of ¬(a ∨ b)" should "throw an exception" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), Not(Or(Symbol("a"), Symbol("b"))))
    the[IllegalArgumentException] thrownBy transform(exp) should have message "Expression is not a literal"
  }

  "The transformation of ((a ∨ c) ∨ (b ∨ d))" should "throw an exception" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), Or(Or(Symbol("a"), Symbol("c")), Or(Symbol("b"), Symbol("d"))))
    the[IllegalArgumentException] thrownBy transform(exp) should have message "Expression is not a literal"
  }

  "The transformation of ¬b" should "return a list of CNF clauses relative to the ¬ operator" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), Not(Symbol("b")))
    val result: List[CNF] = transform(exp)
    val expected: List[CNF] = List(
      CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("TSTN0"))),
      CNFOr(CNFSymbol("b"), CNFSymbol("TSTN0"))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), And(Symbol("a"), Symbol("b")))
    val result: List[CNF] = transform(exp)
    val expected: List[CNF] = List(
      CNFOr(
        CNFOr(CNFNot(CNFSymbol("a")), CNFNot(CNFSymbol("b"))),
        CNFSymbol("TSTN0")
      ),
      CNFOr(CNFSymbol("a"), CNFNot(CNFSymbol("TSTN0"))),
      CNFOr(CNFSymbol("b"), CNFNot(CNFSymbol("TSTN0")))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), And(Not(Symbol("a")), Symbol("b")))
    val result: List[CNF] = transform(exp)
    val expected: List[CNF] = List(
      CNFOr(
        CNFOr(CNFSymbol("a"), CNFNot(CNFSymbol("b"))),
        CNFSymbol("TSTN0")
      ),
      CNFOr(CNFNot(CNFSymbol("a")), CNFNot(CNFSymbol("TSTN0"))),
      CNFOr(CNFSymbol("b"), CNFNot(CNFSymbol("TSTN0")))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), And(Symbol("a"), Not(Symbol("b"))))
    val result: List[CNF] = transform(exp)
    val expected: List[CNF] = List(
      CNFOr(
        CNFOr(CNFNot(CNFSymbol("a")), CNFSymbol("b")),
        CNFSymbol("TSTN0")
      ),
      CNFOr(CNFSymbol("a"), CNFNot(CNFSymbol("TSTN0"))),
      CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("TSTN0")))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), And(Not(Symbol("a")), Not(Symbol("b"))))
    val result: List[CNF] = transform(exp)
    val expected: List[CNF] = List(
      CNFOr(CNFOr(CNFSymbol("a"), CNFSymbol("b")), CNFSymbol("TSTN0")),
      CNFOr(CNFNot(CNFSymbol("a")), CNFNot(CNFSymbol("TSTN0"))),
      CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("TSTN0")))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), Or(Symbol("a"), Symbol("b")))
    val result: List[CNF] = transform(exp)
    val expected: List[CNF] = List(
      CNFOr(
        CNFOr(CNFSymbol("a"), CNFSymbol("b")),
        CNFNot(CNFSymbol("TSTN0"))
      ),
      CNFOr(CNFNot(CNFSymbol("a")), CNFSymbol("TSTN0")),
      CNFOr(CNFNot(CNFSymbol("b")), CNFSymbol("TSTN0"))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), Or(Not(Symbol("a")), Symbol("b")))
    val result: List[CNF] = transform(exp)
    val expected: List[CNF] = List(
      CNFOr(
        CNFOr(CNFNot(CNFSymbol("a")), CNFSymbol("b")),
        CNFNot(CNFSymbol("TSTN0"))
      ),
      CNFOr(CNFSymbol("a"), CNFSymbol("TSTN0")),
      CNFOr(CNFNot(CNFSymbol("b")), CNFSymbol("TSTN0"))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), Or(Symbol("a"), Not(Symbol("b"))))
    val result: List[CNF] = transform(exp)
    val expected: List[CNF] = List(
      CNFOr(
        CNFOr(CNFSymbol("a"), CNFNot(CNFSymbol("b"))),
        CNFNot(CNFSymbol("TSTN0"))
      ),
      CNFOr(CNFNot(CNFSymbol("a")), CNFSymbol("TSTN0")),
      CNFOr(CNFSymbol("b"), CNFSymbol("TSTN0"))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) = (Symbol("TSTN0"), Or(Not(Symbol("a")), Not(Symbol("b"))))
    val result: List[CNF] = transform(exp)
    val expected: List[CNF] = List(
      CNFOr(
        CNFOr(CNFNot(CNFSymbol("a")), CNFNot(CNFSymbol("b"))),
        CNFNot(CNFSymbol("TSTN0"))
      ),
      CNFOr(CNFSymbol("a"), CNFSymbol("TSTN0")),
      CNFOr(CNFSymbol("b"), CNFSymbol("TSTN0"))
    )
    result shouldBe expected
  }
