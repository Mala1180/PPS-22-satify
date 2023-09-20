package satify.update.converters.tseitin.utils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.CNF
import satify.model.cnf.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.update.converters.tseitin.Utils.convertToCNF

class CNFConverter extends AnyFlatSpec with Matchers:

  "The exp a and b" should "be converted to CNF" in {
    val exp: Expression = And(Symbol("a"), Symbol("b"))
    val result: CNF = convertToCNF(exp)
    val expected: CNF = CNFAnd(CNFSymbol("a"), CNFSymbol("b"))
    result shouldBe expected
  }

  "The exp a and b and c and d and e" should "be converted to CNF" in {
    val exp: Expression = And(Symbol("a"), And(Symbol("b"), And(Symbol("c"), And(Symbol("d"), Symbol("e")))))
    val result: CNF = convertToCNF(exp)
    val expected: CNF = CNFAnd(
      CNFSymbol("a"),
      CNFAnd(
        CNFSymbol("b"),
        CNFAnd(
          CNFSymbol("c"),
          CNFAnd(
            CNFSymbol("d"),
            CNFSymbol("e")
          )
        )
      )
    )
    result shouldBe expected
  }

  "The exp" should "be converted to CNF" in {
    val exp = And(
      Or(
        Or(Symbol("GEN1"), Symbol("d")),
        Not(Symbol("GEN0"))
      ),
      And(
        Or(Not(Symbol("GEN1")), Symbol("GEN0")),
        And(
          Or(Not(Symbol("d")), Symbol("GEN0")),
          And(
            Or(
              Or(Symbol("GEN2"), Symbol("GEN3")),
              Not(Symbol("GEN1"))
            ),
            And(
              Or(Not(Symbol("GEN2")), Symbol("GEN1")),
              And(
                Or(Not(Symbol("GEN3")), Symbol("GEN1")),
                And(
                  Or(Not(Symbol("a")), Not(Symbol("GEN2"))),
                  And(
                    Or(Symbol("a"), Symbol("GEN2")),
                    And(
                      Or(
                        Or(Not(Symbol("b")), Not(Symbol("c"))),
                        Symbol("GEN3")
                      ),
                      And(
                        Or(Symbol("b"), Not(Symbol("GEN3"))),
                        Or(Symbol("c"), Not(Symbol("GEN3")))
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

    val result: CNF = convertToCNF(exp)
    val expected = CNFAnd(
      CNFOr(
        CNFOr(CNFSymbol("GEN1"), CNFSymbol("d")),
        CNFNot(CNFSymbol("GEN0"))
      ),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol("GEN1")), CNFSymbol("GEN0")),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol("d")), CNFSymbol("GEN0")),
          CNFAnd(
            CNFOr(
              CNFOr(CNFSymbol("GEN2"), CNFSymbol("GEN3")),
              CNFNot(CNFSymbol("GEN1"))
            ),
            CNFAnd(
              CNFOr(CNFNot(CNFSymbol("GEN2")), CNFSymbol("GEN1")),
              CNFAnd(
                CNFOr(CNFNot(CNFSymbol("GEN3")), CNFSymbol("GEN1")),
                CNFAnd(
                  CNFOr(CNFNot(CNFSymbol("a")), CNFNot(CNFSymbol("GEN2"))),
                  CNFAnd(
                    CNFOr(CNFSymbol("a"), CNFSymbol("GEN2")),
                    CNFAnd(
                      CNFOr(
                        CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("c"))),
                        CNFSymbol("GEN3")
                      ),
                      CNFAnd(
                        CNFOr(CNFSymbol("b"), CNFNot(CNFSymbol("GEN3"))),
                        CNFOr(CNFSymbol("c"), CNFNot(CNFSymbol("GEN3")))
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
