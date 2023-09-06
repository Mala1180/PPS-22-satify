package satify.update.converters

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.{CNF, Variable}
import satify.update.converters.TseitinTransformation.{convertToCNF, isCNF}

class CNFConverter extends AnyFlatSpec with Matchers:

  "The exp a and b" should "be converted to CNF" in {
    val exp: Expression = And(Symbol("a"), Symbol("b"))
    val result: CNF = convertToCNF(exp)
    val expected: CNF = CNFAnd(CNFSymbol(Variable("a")), CNFSymbol(Variable("b")))
    result shouldBe expected
  }

  "The exp a and b and c and d and e" should "be converted to CNF" in {
    val exp: Expression = And(Symbol("a"), And(Symbol("b"), And(Symbol("c"), And(Symbol("d"), Symbol("e")))))
    val result: CNF = convertToCNF(exp)
    val expected: CNF = CNFAnd(
      CNFSymbol(Variable("a")),
      CNFAnd(
        CNFSymbol(Variable("b")),
        CNFAnd(
          CNFSymbol(Variable("c")),
          CNFAnd(
            CNFSymbol(Variable("d")),
            CNFSymbol(Variable("e"))
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
        CNFOr(CNFSymbol(Variable("TSTN1")), CNFSymbol(Variable("d"))),
        CNFNot(CNFSymbol(Variable("TSTN0")))
      ),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol(Variable("TSTN1"))), CNFSymbol(Variable("TSTN0"))),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol(Variable("d"))), CNFSymbol(Variable("TSTN0"))),
          CNFAnd(
            CNFOr(
              CNFOr(CNFSymbol(Variable("TSTN2")), CNFSymbol(Variable("TSTN3"))),
              CNFNot(CNFSymbol(Variable("TSTN1")))
            ),
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
    result shouldBe expected
  }
