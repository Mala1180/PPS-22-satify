package satify.update.converters.tseitin

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.CNF
import satify.model.cnf.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.expression.Expression.*
import satify.update.converters.tseitin.TseitinTransformation.tseitin

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
      CNFSymbol("GEN0"),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("GEN0"))),
        CNFOr(CNFSymbol("b"), CNFSymbol("GEN0"))
      )
    )
    result shouldBe expected
  }

  "For exp (a ∨ b) only the '∨' transformation" should "be applied" in {
    val exp = Or(Symbol("a"), Symbol("b"))
    val result = tseitin(exp)
    val expected: CNF = CNFAnd(
      CNFSymbol("GEN0"),
      CNFAnd(
        CNFOr(
          CNFOr(CNFSymbol("a"), CNFSymbol("b")),
          CNFNot(CNFSymbol("GEN0"))
        ),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol("a")), CNFSymbol("GEN0")),
          CNFOr(CNFNot(CNFSymbol("b")), CNFSymbol("GEN0"))
        )
      )
    )
    result shouldBe expected
  }

  "The CNF form of ((a ∧ ¬b) ∨ c)" should "be correctly generated" in {
    val exp = Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val result = tseitin(exp)
    val expected = CNFAnd(
      CNFSymbol("GEN0"),
      CNFAnd(
        CNFOr(
          CNFOr(CNFSymbol("GEN1"), CNFSymbol("c")),
          CNFNot(CNFSymbol("GEN0"))
        ),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol("GEN1")), CNFSymbol("GEN0")),
          CNFAnd(
            CNFOr(CNFNot(CNFSymbol("c")), CNFSymbol("GEN0")),
            CNFAnd(
              CNFOr(
                CNFOr(CNFNot(CNFSymbol("a")), CNFNot(CNFSymbol("GEN2"))),
                CNFSymbol("GEN1")
              ),
              CNFAnd(
                CNFOr(CNFSymbol("a"), CNFNot(CNFSymbol("GEN1"))),
                CNFAnd(
                  CNFOr(CNFSymbol("GEN2"), CNFNot(CNFSymbol("GEN1"))),
                  CNFAnd(
                    CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("GEN2"))),
                    CNFOr(CNFSymbol("b"), CNFSymbol("GEN2"))
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
    val expected: CNF = CNFAnd(
      CNFSymbol("GEN0"),
      CNFAnd(
        CNFOr(CNFOr(CNFSymbol("GEN1"), CNFSymbol("d")), CNFNot(CNFSymbol("GEN0"))),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol("GEN1")), CNFSymbol("GEN0")),
          CNFAnd(
            CNFOr(CNFNot(CNFSymbol("d")), CNFSymbol("GEN0")),
            CNFAnd(
              CNFOr(CNFOr(CNFSymbol("GEN2"), CNFSymbol("GEN3")), CNFNot(CNFSymbol("GEN1"))),
              CNFAnd(
                CNFOr(CNFNot(CNFSymbol("GEN2")), CNFSymbol("GEN1")),
                CNFAnd(
                  CNFOr(CNFNot(CNFSymbol("GEN3")), CNFSymbol("GEN1")),
                  CNFAnd(
                    CNFOr(CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("c"))), CNFSymbol("GEN3")),
                    CNFAnd(
                      CNFOr(CNFSymbol("b"), CNFNot(CNFSymbol("GEN3"))),
                      CNFAnd(
                        CNFOr(CNFSymbol("c"), CNFNot(CNFSymbol("GEN3"))),
                        CNFAnd(
                          CNFOr(CNFNot(CNFSymbol("a")), CNFNot(CNFSymbol("GEN2"))),
                          CNFOr(CNFSymbol("a"), CNFSymbol("GEN2"))
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
