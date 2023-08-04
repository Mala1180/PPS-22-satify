package ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression
import satify.model.Expression.*
import satify.update.converters.TseitinTransformation.toCNF

class CNFTest extends AnyFlatSpec with Matchers:

  "The CNF form of ((a ∧ ¬b) ∨ c)" should "be correctly generated" in {
    /*
    EXAMPLE RESULT: ((a ∧ ¬b) ∨ c): (A OR B OR X1) AND (NOT A OR NOT X1 OR X2) AND (B OR NOT X1 OR X0)
                                   AND (X1 OR C OR X2) AND (NOT X1 OR NOT C OR X2)
                                   AND (B OR X0) AND (NOT B OR NOT X0)
     */
    val exp = Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val result = toCNF(exp)

    val expected = And(
      Or(Or(Symbol("X1"), Symbol("c")), Not(Symbol("X0"))),
      And(
        Or(Not(Symbol("X1")), Symbol("X0")),
        And(
          Or(Not(Symbol("c")), Symbol("X0")),
          And(
            Or(Or(Not(Symbol("a")), Not(Symbol("X2"))), Symbol("X1")),
            And(
              Or(Symbol("a"), Not(Symbol("X1"))),
              And(
                Or(Symbol("X2"), Not(Symbol("X1"))),
                And(
                  Or(Not(Symbol("b")), Not(Symbol("X2"))),
                  Or(Symbol("b"), Symbol("X2"))
                )
              )
            )
          )
        )
      )
    )

    result shouldBe expected
  }

  "The CNF form of ¬b" should "be correctly generated" in {
    val exp = Not(Symbol("b"))
    val result = toCNF(exp)
    val expected: Expression = And(
      Or(Not(Symbol("b")), Not(Symbol("X0"))),
      Or(Symbol("b"), Symbol("X0"))
    )
    result shouldBe expected
  }
