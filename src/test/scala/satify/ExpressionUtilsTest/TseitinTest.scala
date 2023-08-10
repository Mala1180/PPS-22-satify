package satify.ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.Expression.*
import satify.model.{CNF, Expression, Variable}
import satify.update.converters.TseitinTransformation.tseitin

class TseitinTest extends AnyFlatSpec with Matchers:

  "The CNF form of b" should "remain b" in {
    val exp = Symbol("b")
    val result = tseitin(exp)
    val expected: CNF = CNFSymbol(Variable("b", None))
    result shouldBe expected
  }

  "The CNF form of ¬b" should "be correctly generated" in {
    val exp = Not(Symbol("b"))
    val result = tseitin(exp)
    val expected: CNF = CNFAnd(
      CNFOr(CNFNot(CNFSymbol(Variable("b", None))), CNFNot(CNFSymbol(Variable("X0", None)))),
      CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("X0", None)))
    )
    result shouldBe expected
  }

  "For exp (a ∨ b) only the '∨' transformation" should "be applied" in {
    val exp = Or(Symbol("a"), Symbol("b"))
    val result = tseitin(exp)
    val expected: CNF = CNFAnd(
      CNFOr(
        CNFOr(CNFSymbol(Variable("a", None)), CNFSymbol(Variable("b", None))),
        CNFNot(CNFSymbol(Variable("X0", None)))
      ),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol(Variable("a", None))), CNFSymbol(Variable("X0", None))),
        CNFOr(CNFNot(CNFSymbol(Variable("b", None))), CNFSymbol(Variable("X0", None)))
      )
    )
    result shouldBe expected
  }

  "The CNF form of ((a ∧ ¬b) ∨ c)" should "be correctly generated" in {
    val exp = Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val result = tseitin(exp)
    println(result)
    val expected = CNFAnd(
      CNFOr(
        CNFOr(CNFSymbol(Variable("X1", None)), CNFSymbol(Variable("c", None))),
        CNFNot(CNFSymbol(Variable("X0", None)))
      ),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol(Variable("X1", None))), CNFSymbol(Variable("X0", None))),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol(Variable("c", None))), CNFSymbol(Variable("X0", None))),
          CNFAnd(
            CNFOr(
              CNFOr(CNFNot(CNFSymbol(Variable("a", None))), CNFNot(CNFSymbol(Variable("X2", None)))),
              CNFSymbol(Variable("X1", None))
            ),
            CNFAnd(
              CNFOr(CNFSymbol(Variable("a", None)), CNFNot(CNFSymbol(Variable("X1", None)))),
              CNFAnd(
                CNFOr(CNFSymbol(Variable("X2", None)), CNFNot(CNFSymbol(Variable("X1", None)))),
                CNFAnd(
                  CNFOr(CNFNot(CNFSymbol(Variable("b", None))), CNFNot(CNFSymbol(Variable("X2", None)))),
                  CNFOr(CNFSymbol(Variable("b", None)), CNFSymbol(Variable("X2", None)))
                )
              )
            )
          )
        )
      )
    )
    result shouldBe expected
  }
