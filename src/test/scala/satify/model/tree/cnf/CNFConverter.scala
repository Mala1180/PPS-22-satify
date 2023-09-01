package satify.model.tree.cnf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.cnf.{CNF, Variable, And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.tree.expression.Expression.*
import satify.model.tree.expression.{And, Expression, Not, Or, *}
import satify.update.converters.TseitinTransformation.{convertToCNF, isCNF}

class CNFConverter extends AnyFlatSpec with Matchers:

  "The exp a and b" should "be converted to CNF" in {
    val exp: Expression = And(Symbol("a"), Symbol("b"))
    val result: CNF = convertToCNF(exp)
    val expected: CNF = CNFAnd(CNFSymbol(Variable("a")), CNFSymbol(Variable("b")))
    result shouldBe expected
  }

  "The exp a and b and c and d and e" should "be converted to CNF" in {
    val exp: Expression = And(
      Symbol("a"),
      And(
        Symbol("b"),
        And(Symbol("c"), And(Symbol("d"), Symbol("e")))
      )
    )
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
        Or(Symbol("X1"), Symbol("d")),
        Not(Symbol("X0"))
      ),
      And(
        Or(Not(Symbol("X1")), Symbol("X0")),
        And(
          Or(Not(Symbol("d")), Symbol("X0")),
          And(
            Or(
              Or(Symbol("X2"), Symbol("X3")),
              Not(Symbol("X1"))
            ),
            And(
              Or(Not(Symbol("X2")), Symbol("X1")),
              And(
                Or(Not(Symbol("X3")), Symbol("X1")),
                And(
                  Or(Not(Symbol("a")), Not(Symbol("X2"))),
                  And(
                    Or(Symbol("a"), Symbol("X2")),
                    And(
                      Or(
                        Or(Not(Symbol("b")), Not(Symbol("c"))),
                        Symbol("X3")
                      ),
                      And(
                        Or(Symbol("b"), Not(Symbol("X3"))),
                        Or(Symbol("c"), Not(Symbol("X3")))
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
        CNFOr(CNFSymbol(Variable("X1")), CNFSymbol(Variable("d"))),
        CNFNot(CNFSymbol(Variable("X0")))
      ),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol(Variable("X1"))), CNFSymbol(Variable("X0"))),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol(Variable("d"))), CNFSymbol(Variable("X0"))),
          CNFAnd(
            CNFOr(
              CNFOr(CNFSymbol(Variable("X2")), CNFSymbol(Variable("X3"))),
              CNFNot(CNFSymbol(Variable("X1")))
            ),
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
    result shouldBe expected
  }
