package EmptyExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression.*
import satify.model.{EmptyExpression, EmptyVariable}
import satify.update.converters.TseitinTransformation.toCNF

class CNFTest extends AnyFlatSpec with Matchers:

  "The CNF form of ((a ∧ ¬b) ∨ c)" should "be correctly generated" in {
    /*
    EXAMPLE RESULT: ((a ∧ ¬b) ∨ c): (A OR B OR X1) AND (NOT A OR NOT X1 OR X2) AND (B OR NOT X1 OR X0)
                                   AND (X1 OR C OR X2) AND (NOT X1 OR NOT C OR X2)
                                   AND (B OR X0) AND (NOT B OR NOT X0)
     */
    val exp = Or(And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))), Symbol(EmptyVariable("c")))
    val result = toCNF(exp)

    val expected = And(
      Or(Or(Symbol(EmptyVariable("X1")), Symbol(EmptyVariable("c"))), Not(Symbol(EmptyVariable("X0")))),
      And(
        Or(Not(Symbol(EmptyVariable("X1"))), Symbol(EmptyVariable("X0"))),
        And(
          Or(Not(Symbol(EmptyVariable("c"))), Symbol(EmptyVariable("X0"))),
          And(
            Or(Or(Not(Symbol(EmptyVariable("a"))), Not(Symbol(EmptyVariable("X2")))), Symbol(EmptyVariable("X1"))),
            And(
              Or(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("X1")))),
              And(
                Or(Symbol(EmptyVariable("X2")), Not(Symbol(EmptyVariable("X1")))),
                And(
                  Or(Not(Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("X2")))),
                  Or(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("X2")))
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
    val exp = Not(Symbol(EmptyVariable("b")))
    val result = toCNF(exp)
    val expected: EmptyExpression = And(
      Or(Not(Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("X0")))),
      Or(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("X0")))
    )
    result shouldBe expected
  }
