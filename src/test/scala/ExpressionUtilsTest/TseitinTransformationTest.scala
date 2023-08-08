package ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.Expression.*
import satify.model.{CNF, Expression, Variable}
import satify.update.converters.TseitinTransformation.transform

class TseitinTransformationTest extends AnyFlatSpec with Matchers:

  "The transformation of ¬b" should "return a list of CNF clauses relative to the ¬ operator" in {
    val exp: (Symbol, Expression) = (Symbol("X0"), Not(Symbol("b")))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("X0", None)),
        CNFOr(CNFNot(CNFSymbol(Variable("b", None))), CNFNot(CNFSymbol(Variable("X0", None))))
      ),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFSymbol(Variable("b", None)), CNFSymbol(Variable("X0", None))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), And(Symbol("a"), Symbol("b")))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("X0", None)),
        CNFOr(
          CNFOr(CNFNot(CNFSymbol(Variable("a", None))), CNFNot(CNFSymbol(Variable("b", None)))),
          CNFSymbol(Variable("X0", None))
        )
      ),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFSymbol(Variable("a", None)), CNFNot(CNFSymbol(Variable("X0", None))))),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFSymbol(Variable("b", None)), CNFNot(CNFSymbol(Variable("X0", None)))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), And(Not(Symbol("a")), Symbol("b")))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("X0", None)),
        CNFOr(CNFOr(CNFSymbol(Variable("a", None)), CNFNot(CNFSymbol(Variable("b", None)))), CNFSymbol(Variable("X0", None)))
      ),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFNot(CNFSymbol(Variable("a", None))), CNFNot(CNFSymbol(Variable("X0", None))))),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFSymbol(Variable("b", None)), CNFNot(CNFSymbol(Variable("X0", None)))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), And(Symbol("a"), Not(Symbol("b"))))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("X0", None)),
        CNFOr(CNFOr(CNFNot(CNFSymbol(Variable("a", None))), CNFSymbol(Variable("b", None))), CNFSymbol(Variable("X0", None)))
      ),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFSymbol(Variable("a", None)), CNFNot(CNFSymbol(Variable("X0", None))))),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFNot(CNFSymbol(Variable("b", None))), CNFNot(CNFSymbol(Variable("X0", None)))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), And(Not(Symbol("a")), Not(Symbol("b"))))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("X0", None)),
        CNFOr(CNFOr(CNFSymbol(Variable("a", None)), CNFSymbol(Variable("b", None))), CNFSymbol(Variable("X0", None)))
      ),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFNot(CNFSymbol(Variable("a", None))), CNFNot(CNFSymbol(Variable("X0", None))))),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFNot(CNFSymbol(Variable("b", None))), CNFNot(CNFSymbol(Variable("X0", None)))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), Or(Symbol("a"), Symbol("b")))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("X0", None)),
        CNFOr(CNFOr(CNFSymbol(Variable("a", None)), CNFSymbol(Variable("b", None))), CNFNot(CNFSymbol(Variable("X0", None))))
      ),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFNot(CNFSymbol(Variable("a", None))), CNFSymbol(Variable("X0", None)))),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFNot(CNFSymbol(Variable("b", None))), CNFSymbol(Variable("X0", None))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), Or(Not(Symbol("a")), Symbol("b")))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("X0", None)),
        CNFOr(CNFOr(CNFNot(CNFSymbol(Variable("a", None))), CNFSymbol(Variable("b", None))), CNFNot(CNFSymbol(Variable("X0", None))))
      ),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFSymbol(Variable("a", None)), CNFSymbol(Variable("X0", None)))),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFNot(CNFSymbol(Variable("b", None))), CNFSymbol(Variable("X0", None))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), Or(Symbol("a"), Not(Symbol("b"))))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("X0", None)),
        CNFOr(CNFOr(CNFSymbol(Variable("a", None)), CNFNot(CNFSymbol(Variable("b", None)))), CNFNot(CNFSymbol(Variable("X0", None))))
      ),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFNot(CNFSymbol(Variable("a", None))), CNFSymbol(Variable("X0", None)))),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFSymbol(Variable("b", None)), CNFSymbol(Variable("X0", None))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (Symbol, Expression) =
      (Symbol("X0"), Or(Not(Symbol("a")), Not(Symbol("b"))))
    val result: List[(CNFSymbol, CNF)] = transform(exp)
    val expected: List[(CNFSymbol, CNF)] = List(
      (
        CNFSymbol(Variable("X0", None)),
        CNFOr(CNFOr(CNFNot(CNFSymbol(Variable("a", None))), CNFNot(CNFSymbol(Variable("b", None)))), CNFNot(CNFSymbol(Variable("X0", None))))
      ),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFSymbol(Variable("a", None)), CNFSymbol(Variable("X0", None)))),
      (CNFSymbol(Variable("X0", None)), CNFOr(CNFSymbol(Variable("b", None)), CNFSymbol(Variable("X0", None))))
    )
    result shouldBe expected
  }
