package satify.modelUtils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.cnf.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.tree.cnf.Variable
import satify.model.tree.{cnf, *}
import satify.model.tree.expression.Expression.*
import satify.model.tree.expression.{And, Expression, Not, Or}
import satify.update.converters.TseitinTransformation.{convertToCNF, isCNF}

class CNFConverter extends AnyFlatSpec with Matchers:

  "The exp a and b" should "be converted to CNF" in {
    val exp: Expression = And(expression.Symbol("a"), expression.Symbol("b"))
    val result: cnf.CNF = convertToCNF(exp)
    val expected: cnf.CNF = CNFAnd(CNFSymbol(Variable("a")), CNFSymbol(Variable("b")))
    result shouldBe expected
  }

  "The exp a and b and c and d and e" should "be converted to CNF" in {
    val exp: Expression = expression.And(expression.Symbol("a"), expression.And(expression.Symbol("b"), expression.And(expression.Symbol("c"), expression.And(expression.Symbol("d"), expression.Symbol("e")))))
    val result: cnf.CNF = convertToCNF(exp)
    val expected: cnf.CNF = CNFAnd(
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
        expression.Or(expression.Symbol("X1"), expression.Symbol("d")),
        Not(expression.Symbol("X0"))
      ),
      And(
        expression.Or(expression.Not(expression.Symbol("X1")), expression.Symbol("X0")),
        And(
          expression.Or(expression.Not(expression.Symbol("d")), expression.Symbol("X0")),
          And(
            Or(
              expression.Or(expression.Symbol("X2"), expression.Symbol("X3")),
              expression.Not(expression.Symbol("X1"))
            ),
            And(
              expression.Or(expression.Not(expression.Symbol("X2")), expression.Symbol("X1")),
              And(
                expression.Or(expression.Not(expression.Symbol("X3")), expression.Symbol("X1")),
                And(
                  Or(expression.Not(expression.Symbol("a")), expression.Not(expression.Symbol("X2"))),
                  And(
                    expression.Or(expression.Symbol("a"), expression.Symbol("X2")),
                    And(
                      expression.Or(
                        Or(expression.Not(expression.Symbol("b")), expression.Not(expression.Symbol("c"))),
                        expression.Symbol("X3")
                      ),
                      And(
                        expression.Or(expression.Symbol("b"), expression.Not(expression.Symbol("X3"))),
                        expression.Or(expression.Symbol("c"), expression.Not(expression.Symbol("X3")))
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

    val result: cnf.CNF = convertToCNF(exp)
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
