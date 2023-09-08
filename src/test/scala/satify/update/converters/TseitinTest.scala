package satify.update.converters

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.expression.Expression.*
import satify.model.CNF
import satify.update.converters.TseitinTransformation.tseitin

class TseitinTest extends AnyFlatSpec with Matchers:

  "The CNF form of b" should "remain b" in {
    val exp = Symbol("b")
    val result = tseitin(exp)
    val expected: CNF = CNFSymbol("b")
    result shouldBe expected
  }

  "The CNF form of ¬b" should "be correctly generated" in {
    val exp = Not(Symbol("b"))
    val result = tseitin(exp)
    val expected: CNF = CNFAnd(
      CNFSymbol("TSTN0"),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("TSTN0"))),
        CNFOr(CNFSymbol("b"), CNFSymbol("TSTN0"))
      )
    )
    result shouldBe expected
  }

  "For exp (a ∨ b) only the '∨' transformation" should "be applied" in {
    val exp = Or(Symbol("a"), Symbol("b"))
    val result = tseitin(exp)
    val expected: CNF = CNFAnd(
      CNFSymbol("TSTN0"),
      CNFAnd(
        CNFOr(
          CNFOr(CNFSymbol("a"), CNFSymbol("b")),
          CNFNot(CNFSymbol("TSTN0"))
        ),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol("a")), CNFSymbol("TSTN0")),
          CNFOr(CNFNot(CNFSymbol("b")), CNFSymbol("TSTN0"))
        )
      )
    )
    result shouldBe expected
  }

  "The CNF form of ((a ∧ ¬b) ∨ c)" should "be correctly generated" in {
    val exp = Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val result = tseitin(exp)
    val expected = CNFAnd(
      CNFSymbol("TSTN0"),
      CNFAnd(
        CNFOr(
          CNFOr(CNFSymbol("TSTN1"), CNFSymbol("c")),
          CNFNot(CNFSymbol("TSTN0"))
        ),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol("TSTN1")), CNFSymbol("TSTN0")),
          CNFAnd(
            CNFOr(CNFNot(CNFSymbol("c")), CNFSymbol("TSTN0")),
            CNFAnd(
              CNFOr(
                CNFOr(CNFNot(CNFSymbol("a")), CNFNot(CNFSymbol("TSTN2"))),
                CNFSymbol("TSTN1")
              ),
              CNFAnd(
                CNFOr(CNFSymbol("a"), CNFNot(CNFSymbol("TSTN1"))),
                CNFAnd(
                  CNFOr(CNFSymbol("TSTN2"), CNFNot(CNFSymbol("TSTN1"))),
                  CNFAnd(
                    CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("TSTN2"))),
                    CNFOr(CNFSymbol("b"), CNFSymbol("TSTN2"))
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
      CNFSymbol("TSTN0"),
      CNFAnd(
        CNFOr(CNFOr(CNFSymbol("TSTN1"), CNFSymbol("d")), CNFNot(CNFSymbol("TSTN0"))),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol("TSTN1")), CNFSymbol("TSTN0")),
          CNFAnd(
            CNFOr(CNFNot(CNFSymbol("d")), CNFSymbol("TSTN0")),
            CNFAnd(
              CNFOr(
                CNFOr(CNFSymbol("TSTN2"), CNFSymbol("TSTN3")),
                CNFNot(CNFSymbol("TSTN1"))
              ),
              CNFAnd(
                CNFOr(CNFNot(CNFSymbol("TSTN2")), CNFSymbol("TSTN1")),
                CNFAnd(
                  CNFOr(CNFNot(CNFSymbol("TSTN3")), CNFSymbol("TSTN1")),
                  CNFAnd(
                    CNFOr(CNFNot(CNFSymbol("a")), CNFNot(CNFSymbol("TSTN2"))),
                    CNFAnd(
                      CNFOr(CNFSymbol("a"), CNFSymbol("TSTN2")),
                      CNFAnd(
                        CNFOr(
                          CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("c"))),
                          CNFSymbol("TSTN3")
                        ),
                        CNFAnd(
                          CNFOr(CNFSymbol("b"), CNFNot(CNFSymbol("TSTN3"))),
                          CNFOr(CNFSymbol("c"), CNFNot(CNFSymbol("TSTN3")))
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
