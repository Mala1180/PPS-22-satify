package satify.ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.expression.Expression.*
import satify.model.{CNF, Variable}
import satify.update.converters.TseitinTransformation.tseitin

class TseitinTest extends AnyFlatSpec with Matchers:

  "The CNF form of b" should "remain b" in {
    val exp = Symbol("b")
    val result = tseitin(exp)
    val expected: CNF = CNFSymbol(Variable("b"))
    result shouldBe expected
  }

  "The CNF form of ¬b" should "be correctly generated" in {
    val exp = Not(Symbol("b"))
    val result = tseitin(exp)
    val expected: CNF = CNFAnd(
      CNFSymbol(Variable("TSTN0")),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("TSTN0")))),
        CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("TSTN0")))
      )
    )
    result shouldBe expected
  }

  "For exp (a ∨ b) only the '∨' transformation" should "be applied" in {
    val exp = Or(Symbol("a"), Symbol("b"))
    val result = tseitin(exp)
    val expected: CNF = CNFAnd(
      CNFSymbol(Variable("TSTN0")),
      CNFAnd(
        CNFOr(
          CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("b"))),
          CNFNot(CNFSymbol(Variable("TSTN0")))
        ),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFSymbol(Variable("TSTN0"))),
          CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFSymbol(Variable("TSTN0")))
        )
      )
    )
    result shouldBe expected
  }

  "The CNF form of ((a ∧ ¬b) ∨ c)" should "be correctly generated" in {
    val exp = Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val result = tseitin(exp)
    val expected = CNFAnd(
      CNFSymbol(Variable("TSTN0")),
      CNFAnd(
        CNFOr(
          CNFOr(CNFSymbol(Variable("TSTN1")), CNFSymbol(Variable("c"))),
          CNFNot(CNFSymbol(Variable("TSTN0")))
        ),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol(Variable("TSTN1"))), CNFSymbol(Variable("TSTN0"))),
          CNFAnd(
            CNFOr(CNFNot(CNFSymbol(Variable("c"))), CNFSymbol(Variable("TSTN0"))),
            CNFAnd(
              CNFOr(
                CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("TSTN2")))),
                CNFSymbol(Variable("TSTN1"))
              ),
              CNFAnd(
                CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("TSTN1")))),
                CNFAnd(
                  CNFOr(CNFSymbol(Variable("TSTN2")), CNFNot(CNFSymbol(Variable("TSTN1")))),
                  CNFAnd(
                    CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("TSTN2")))),
                    CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("TSTN2")))
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
    val exp = Or(Or(Not(Symbol("a")), And(Symbol("b"), Symbol("c"))), Symbol("d"))
    val result = tseitin(exp)
    val expected = CNFAnd(
      CNFSymbol(Variable("TSTN0")),
      CNFAnd(
        CNFOr(CNFOr(CNFSymbol(Variable("TSTN1")), CNFSymbol(Variable("d"))), CNFNot(CNFSymbol(Variable("TSTN0")))),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol(Variable("TSTN1"))), CNFSymbol(Variable("TSTN0"))),
          CNFAnd(
            CNFOr(CNFNot(CNFSymbol(Variable("d"))), CNFSymbol(Variable("TSTN0"))),
            CNFAnd(
              CNFOr(CNFOr(CNFSymbol(Variable("TSTN2")), CNFSymbol(Variable("TSTN3"))), CNFNot(CNFSymbol(Variable("TSTN1")))),
              CNFAnd(
                CNFOr(CNFNot(CNFSymbol(Variable("TSTN2"))), CNFSymbol(Variable("TSTN1"))),
                CNFAnd(
                  CNFOr(CNFNot(CNFSymbol(Variable("TSTN3"))), CNFSymbol(Variable("TSTN1"))),
                  CNFAnd(
                    CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("TSTN2")))),
                    CNFAnd(
                      CNFOr(CNFSymbol(Variable("a")), CNFSymbol(Variable("TSTN2"))),
                      CNFAnd(
                        CNFOr(
                          CNFOr(CNFNot(CNFSymbol(Variable("b"))), CNFNot(CNFSymbol(Variable("c")))),
                          CNFSymbol(Variable("TSTN3"))
                        ),
                        CNFAnd(
                          CNFOr(CNFSymbol(Variable("b")), CNFNot(CNFSymbol(Variable("TSTN3")))),
                          CNFOr(CNFSymbol(Variable("c")), CNFNot(CNFSymbol(Variable("TSTN3"))))
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
