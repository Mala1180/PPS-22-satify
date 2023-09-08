package satify.update.converters

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.CNF
import satify.model.dpll.Variable
import satify.update.converters.TseitinTransformation.convertToCNF

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
        Or(Symbol("TSTN1"), Symbol("d")),
        Not(Symbol("TSTN0"))
      ),
      And(
        Or(Not(Symbol("TSTN1")), Symbol("TSTN0")),
        And(
          Or(Not(Symbol("d")), Symbol("TSTN0")),
          And(
            Or(
              Or(Symbol("TSTN2"), Symbol("TSTN3")),
              Not(Symbol("TSTN1"))
            ),
            And(
              Or(Not(Symbol("TSTN2")), Symbol("TSTN1")),
              And(
                Or(Not(Symbol("TSTN3")), Symbol("TSTN1")),
                And(
                  Or(Not(Symbol("a")), Not(Symbol("TSTN2"))),
                  And(
                    Or(Symbol("a"), Symbol("TSTN2")),
                    And(
                      Or(
                        Or(Not(Symbol("b")), Not(Symbol("c"))),
                        Symbol("TSTN3")
                      ),
                      And(
                        Or(Symbol("b"), Not(Symbol("TSTN3"))),
                        Or(Symbol("c"), Not(Symbol("TSTN3")))
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
        CNFOr(CNFSymbol("TSTN1"), CNFSymbol("d")),
        CNFNot(CNFSymbol("TSTN0"))
      ),
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
    result shouldBe expected
  }
