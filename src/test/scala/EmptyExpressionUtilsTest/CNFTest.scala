package EmptyExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression.*
import satify.model.{EmptyExpression, EmptyVariable}
import satify.update.converters.TseitinTransformation.toCNF

class CNFTest extends AnyFlatSpec with Matchers:

  "((a ∧ ¬b) ∨ c) the CNF form" should "be correctly generated" in {
    /*val test = CNF expression
    /*for Input Formula:
      (A OR B OR X1) AND (NOT A OR NOT X1 OR X2) AND (B OR NOT X1 OR X0
    ) AND(X1 OR C OR X2) AND (NOT X1 OR NOT C OR X2) AND (B OR X0) AND (NOT B OR NOT X0)
     */*/
    // ((a ∧ ¬b) ∨ c)
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

  "In ¬b the CNF form" should "be correctly generated" in {
    // TODO: to implement test
    val exp = Not(Symbol(EmptyVariable("b")))

    // concat in AND le sottoespressioni NOT(B) e A AND NOT(B)
    // NOT(B) => X0
    // (Symbol(EmptyVariable(X0)),Or(Not(Symbol(EmptyVariable(b))),Not(Symbol(EmptyVariable(X0)))))
    // (Symbol(EmptyVariable(X0)),Or(Symbol(EmptyVariable(b)),Symbol(EmptyVariable(X0))))
    val result = toCNF(exp)
    println(result)
    val expected: EmptyExpression = And(
      Or(Not(Symbol(EmptyVariable("b"))), Not(Symbol(EmptyVariable("X0")))),
      Or(Symbol(EmptyVariable("b")), Symbol(EmptyVariable("X0")))
    )

    result shouldBe expected
  }
