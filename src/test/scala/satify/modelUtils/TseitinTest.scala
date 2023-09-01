package satify.modelUtils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.cnf.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.tree.cnf.Variable
import satify.model.tree.expression.{And, Not, Or}
import satify.model.tree.expression.Expression.*
import satify.model.tree.{cnf, *}
import satify.update.converters.TseitinTransformation.tseitin

class TseitinTest extends AnyFlatSpec with Matchers:

  "The CNF form of b" should "remain b" in {
    val exp = expression.Symbol("b")
    val result = tseitin(exp)
    val expected: cnf.CNF = CNFSymbol(Variable("b"))
    result shouldBe expected
  }

  "The CNF form of ¬b" should "be correctly generated" in {
    val exp = Not(expression.Symbol("b"))
    val result = tseitin(exp)
    val expected: cnf.CNF = CNFAnd(
      CNFSymbol(Variable("X0")),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("X0")))),
        CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("X0")))
      )
    )
    result shouldBe expected
  }

  "For exp (a ∨ b) only the '∨' transformation" should "be applied" in {
    val exp = Or(expression.Symbol("a"), expression.Symbol("b"))
    val result = tseitin(exp)
    val expected: cnf.CNF = CNFAnd(
      CNFSymbol(Variable("X0")),
      CNFAnd(
        CNFOr(
          CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("b"))),
          CNFNot(CNFSymbol(Variable("X0")))
        ),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFSymbol(Variable("X0"))),
          CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFSymbol(Variable("X0")))
        )
      )
    )
    result shouldBe expected
  }

  "The CNF form of ((a ∧ ¬b) ∨ c)" should "be correctly generated" in {
    val exp = expression.Or(And(expression.Symbol("a"), expression.Not(expression.Symbol("b"))), expression.Symbol("c"))
    val result = tseitin(exp)
    val expected = CNFAnd(
      CNFSymbol(Variable("X0")),
      CNFAnd(
        CNFOr(
          CNFOr(CNFSymbol(Variable("X1")), CNFSymbol(Variable("c"))),
          CNFNot(CNFSymbol(Variable("X0")))
        ),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol(Variable("X1"))), CNFSymbol(Variable("X0"))),
          CNFAnd(
            CNFOr(CNFNot(CNFSymbol(Variable("c"))), CNFSymbol(Variable("X0"))),
            CNFAnd(
              CNFOr(
                CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("X2")))),
                CNFSymbol(Variable("X1"))
              ),
              CNFAnd(
                CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("X1")))),
                CNFAnd(
                  CNFOr(CNFSymbol(Variable("X2")), CNFNot(CNFSymbol(Variable("X1")))),
                  CNFAnd(
                    CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("X2")))),
                    CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("X2")))
                  )
                )
              )
            )
          )
        )
      )
    )
    result shouldBe expected
  }

  "The CNF form of ((¬a ∨ (b ∧ c)) ∨ d)" should "be correctly generated" in {
    val exp = expression.Or(Or(expression.Not(expression.Symbol("a")), expression.And(expression.Symbol("b"), expression.Symbol("c"))), expression.Symbol("d"))
    val result = tseitin(exp)
    val expected = CNFAnd(
      CNFSymbol(Variable("X0")),
      CNFAnd(
        CNFOr(CNFOr(CNFSymbol(Variable("X1")), CNFSymbol(Variable("d"))), CNFNot(CNFSymbol(Variable("X0")))),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol(Variable("X1"))), CNFSymbol(Variable("X0"))),
          CNFAnd(
            CNFOr(CNFNot(CNFSymbol(Variable("d"))), CNFSymbol(Variable("X0"))),
            CNFAnd(
              CNFOr(CNFOr(CNFSymbol(Variable("X2")), CNFSymbol(Variable("X3"))), CNFNot(CNFSymbol(Variable("X1")))),
              CNFAnd(
                CNFOr(CNFNot(CNFSymbol(Variable("X2"))), CNFSymbol(Variable("X1"))),
                CNFAnd(
                  CNFOr(CNFNot(CNFSymbol(Variable("X3"))), CNFSymbol(Variable("X1"))),
                  CNFAnd(
                    CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("X2")))),
                    CNFAnd(
                      CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("X2"))),
                      CNFAnd(
                        CNFOr(
                          CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("c")))),
                          CNFSymbol(Variable("X3"))
                        ),
                        CNFAnd(
                          CNFOr(CNFSymbol(Variable("b")), CNFNot(CNFSymbol(Variable("X3")))),
                          CNFOr(CNFSymbol(Variable("c")), CNFNot(CNFSymbol(Variable("X3"))))
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )
    )

    result shouldBe expected
  }
