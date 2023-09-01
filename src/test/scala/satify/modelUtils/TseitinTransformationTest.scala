package satify.modelUtils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.cnf.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.tree.cnf.Variable
import satify.model.tree.*
import satify.model.tree.expression.Expression.*
import satify.model.tree.expression.{And, Expression, Not, Or}
import satify.update.converters.TseitinTransformation.transform

class TseitinTransformationTest extends AnyFlatSpec with Matchers:

  "The transformation of ¬b" should "return a list of CNF clauses relative to the ¬ operator" in {
    val exp: (expression.Symbol, Expression) = (expression.Symbol("X0"), Not(expression.Symbol("b")))
    val result: List[(CNFSymbol, cnf.CNF)] = transform(exp)
    val expected: List[(CNFSymbol, cnf.CNF)] = List(
      (
        CNFSymbol(Variable("X0")),
        CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("X0"))))
      ),
      (CNFSymbol(Variable("X0")), CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (expression.Symbol, Expression) =
      (expression.Symbol("X0"), And(expression.Symbol("a"), expression.Symbol("b")))
    val result: List[(CNFSymbol, cnf.CNF)] = transform(exp)
    val expected: List[(CNFSymbol, cnf.CNF)] = List(
      (
        CNFSymbol(Variable("X0")),
        CNFOr(
          CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("b")))),
          CNFSymbol(Variable("X0"))
        )
      ),
      (CNFSymbol(Variable("X0")), CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("X0"))))),
      (CNFSymbol(Variable("X0")), CNFOr(CNFSymbol(Variable("b")), CNFNot(CNFSymbol(Variable("X0")))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (expression.Symbol, Expression) =
      (expression.Symbol("X0"), expression.And(expression.Not(expression.Symbol("a")), expression.Symbol("b")))
    val result: List[(CNFSymbol, cnf.CNF)] = transform(exp)
    val expected: List[(CNFSymbol, cnf.CNF)] = List(
      (
        CNFSymbol(Variable("X0")),
        CNFOr(
          CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("b")))),
          CNFSymbol(Variable("X0"))
        )
      ),
      (
        CNFSymbol(Variable("X0")),
        CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("X0"))))
      ),
      (CNFSymbol(Variable("X0")), CNFOr(CNFSymbol(Variable("b")), CNFNot(CNFSymbol(Variable("X0")))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (expression.Symbol, Expression) =
      (expression.Symbol("X0"), expression.And(expression.Symbol("a"), expression.Not(expression.Symbol("b"))))
    val result: List[(CNFSymbol, cnf.CNF)] = transform(exp)
    val expected: List[(CNFSymbol, cnf.CNF)] = List(
      (
        CNFSymbol(Variable("X0")),
        CNFOr(
          CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFSymbol(Variable("b"))),
          CNFSymbol(Variable("X0"))
        )
      ),
      (CNFSymbol(Variable("X0")), CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("X0"))))),
      (
        CNFSymbol(Variable("X0")),
        CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("X0"))))
      )
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∧ ¬b)" should "return a list of CNF clauses relative to the ∧ operator" in {
    val exp: (expression.Symbol, Expression) =
      (expression.Symbol("X0"), And(expression.Not(expression.Symbol("a")), expression.Not(expression.Symbol("b"))))
    val result: List[(CNFSymbol, cnf.CNF)] = transform(exp)
    val expected: List[(CNFSymbol, cnf.CNF)] = List(
      (
        CNFSymbol(Variable("X0")),
        CNFOr(CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("b"))), CNFSymbol(Variable("X0")))
      ),
      (
        CNFSymbol(Variable("X0")),
        CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("X0"))))
      ),
      (
        CNFSymbol(Variable("X0")),
        CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("X0"))))
      )
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (expression.Symbol, Expression) =
      (expression.Symbol("X0"), Or(expression.Symbol("a"), expression.Symbol("b")))
    val result: List[(CNFSymbol, cnf.CNF)] = transform(exp)
    val expected: List[(CNFSymbol, cnf.CNF)] = List(
      (
        CNFSymbol(Variable("X0")),
        CNFOr(
          CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("b"))),
          CNFNot(CNFSymbol(Variable("X0")))
        )
      ),
      (CNFSymbol(Variable("X0")), CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFSymbol(Variable("X0")))),
      (CNFSymbol(Variable("X0")), CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFSymbol(Variable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (expression.Symbol, Expression) =
      (expression.Symbol("X0"), expression.Or(expression.Not(expression.Symbol("a")), expression.Symbol("b")))
    val result: List[(CNFSymbol, cnf.CNF)] = transform(exp)
    val expected: List[(CNFSymbol, cnf.CNF)] = List(
      (
        CNFSymbol(Variable("X0")),
        CNFOr(
          CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFSymbol(Variable("b"))),
          CNFNot(CNFSymbol(Variable("X0")))
        )
      ),
      (CNFSymbol(Variable("X0")), CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("X0")))),
      (CNFSymbol(Variable("X0")), CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFSymbol(Variable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (expression.Symbol, Expression) =
      (expression.Symbol("X0"), expression.Or(expression.Symbol("a"), expression.Not(expression.Symbol("b"))))
    val result: List[(CNFSymbol, cnf.CNF)] = transform(exp)
    val expected: List[(CNFSymbol, cnf.CNF)] = List(
      (
        CNFSymbol(Variable("X0")),
        CNFOr(
          CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("b")))),
          CNFNot(CNFSymbol(Variable("X0")))
        )
      ),
      (CNFSymbol(Variable("X0")), CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFSymbol(Variable("X0")))),
      (CNFSymbol(Variable("X0")), CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("X0"))))
    )
    result shouldBe expected
  }

  "The transformation of (¬a ∨ ¬b)" should "return a list of CNF clauses relative to the ∨ operator" in {
    val exp: (expression.Symbol, Expression) =
      (expression.Symbol("X0"), Or(expression.Not(expression.Symbol("a")), expression.Not(expression.Symbol("b"))))
    val result: List[(CNFSymbol, cnf.CNF)] = transform(exp)
    val expected: List[(CNFSymbol, cnf.CNF)] = List(
      (
        CNFSymbol(Variable("X0")),
        CNFOr(
          CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("b")))),
          CNFNot(CNFSymbol(Variable("X0")))
        )
      ),
      (CNFSymbol(Variable("X0")), CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("X0")))),
      (CNFSymbol(Variable("X0")), CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("X0"))))
    )
    result shouldBe expected
  }
