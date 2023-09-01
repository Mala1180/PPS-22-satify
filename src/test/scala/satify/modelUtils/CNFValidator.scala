package satify.modelUtils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.*
import satify.model.tree.expression.Expression.*
import satify.model.tree.expression.{And, Expression, Not, Or}
import satify.update.converters.TseitinTransformation.isCNF

class CNFValidator extends AnyFlatSpec with Matchers:

  "The symbol a" should "be validated as CNF" in {
    val exp: Expression = expression.Symbol("a")
    val result: Boolean = isCNF(exp)
    result shouldBe true
  }

  "The exp a ∧ b ∧ c ∧ d ∧ e" should "be validated as CNF" in {
    val exp: Expression = And(expression.Symbol("a"), expression.And(expression.Symbol("b"), expression.And(expression.Symbol("c"), expression.And(expression.Symbol("d"), expression.Symbol("e")))))
    val result: Boolean = isCNF(exp)
    result shouldBe true
  }

  "The exp ((a ∧ ¬b) ∨ c)" should "not be validated as CNF" in {
    val exp: Expression =
      Or(expression.And(expression.Symbol("a"), Not(expression.Symbol("b"))), expression.Symbol("c"))

    val result: Boolean = isCNF(exp)
    result shouldBe false
  }

  "The exp ((a ∧ ¬b) ∨ c)" should "be validated as CNF" in {
    val exp = And(
      Or(
        expression.Or(expression.Symbol("X1"), expression.Symbol("d")),
        expression.Not(expression.Symbol("X0"))
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
    val result: Boolean = isCNF(exp)
    result shouldBe true
  }
