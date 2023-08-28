package satify.ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.{CNF, Variable}
import satify.update.converters.TseitinTransformation.isCNF

class CNFValidator extends AnyFlatSpec with Matchers:

  "The symbol a" should "be validated as CNF" in {
    val exp: Expression = Symbol("a")
    val result: Boolean = isCNF(exp)
    result shouldBe true
  }

  "The exp a ∧ b ∧ c ∧ d ∧ e" should "be validated as CNF" in {
    val exp: Expression = And(Symbol("a"), And(Symbol("b"), And(Symbol("c"), And(Symbol("d"), Symbol("e")))))
    val result: Boolean = isCNF(exp)
    result shouldBe true
  }

  "The exp ((a ∧ ¬b) ∨ c)" should "not be validated as CNF" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))

    val result: Boolean = isCNF(exp)
    result shouldBe false
  }

  "The exp ((a ∧ ¬b) ∨ c)" should "be validated as CNF" in {
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
    val result: Boolean = isCNF(exp)
    result shouldBe true
  }
