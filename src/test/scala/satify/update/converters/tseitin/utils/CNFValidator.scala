package satify.update.converters.tseitin.utils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.update.converters.tseitin.Utils.isCNF

class CNFValidator extends AnyFlatSpec with Matchers:

  "The transformation of ¬(a ∨ b)" should "throw an exception" in {
    val exp: Expression = Not(Or(Symbol("a"), Symbol("b")))
    val result: Boolean = isCNF(exp)
    result shouldBe false
  }

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
    val result: Boolean = isCNF(exp)
    result shouldBe true
  }
