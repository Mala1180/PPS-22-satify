package satify.ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.Expression.*
import satify.model.{CNF, Expression, Variable}
import satify.update.converters.TseitinTransformation.tseitin

class TseitinTestConversion extends AnyFlatSpec with Matchers:

  "The CNF form" should "be correctly generated" in {
    // val expLeft = And(Symbol("a"), Or(Symbol("b"), Symbol("c")))
    val expRight = Or(And(Symbol("a"), Symbol("b")), Symbol("c"))
    // val expRight = Or(And(Symbol("a"), Symbol("b")), And(Symbol("c"), Symbol("d")))

    // val resultLeft = tseitin(expLeft)
    val resultRight = tseitin(expRight)

    // val expectedLeft: CNF = CNFAnd(CNFSymbol(Variable("a")), CNFOr(CNFSymbol(Variable("b")), CNFSymbol(Variable("c"))))

    val expectedRight: CNF = CNFAnd(
      CNFOr(CNFOr(CNFSymbol(Variable("X1")), CNFSymbol(Variable("c"))), CNFNot(CNFSymbol(Variable("X0")))),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol(Variable("X1"))), CNFSymbol(Variable("X0"))),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol(Variable("c"))), CNFSymbol(Variable("X0"))),
          CNFAnd(
            CNFOr(CNFOr(CNFNot(CNFSymbol(Variable("a"))), CNFNot(CNFSymbol(Variable("b")))), CNFSymbol(Variable("X1"))),
            CNFAnd(
              CNFOr(CNFSymbol(Variable("a")), CNFNot(CNFSymbol(Variable("X1")))),
              CNFOr(CNFSymbol(Variable("b")), CNFNot(CNFSymbol(Variable("X1"))))
            )
          )
        )
      )
    )

    /*val ttt = And(
      Or(Symbol(Variable(b, None)), Not(Symbol(Variable(X1, None)))),
      And(
        Or(Symbol(Variable(a, None)), Not(Symbol(Variable(X1, None)))),
        Or(Or(Not(Symbol(Variable(a, None))), Not(Symbol(Variable(b, None)))), Symbol(Variable(X1, None)))
      )
    )*/

    /*    val v =
      And(
        And(
          Or(Or(Not(Symbol(Variable(a, None))), Not(Symbol(Variable(b, None)))), Symbol(Variable(X1, None))),
          And(
            Or(Symbol(Variable(a, None)), Not(Symbol(Variable(X1, None)))),
            Or(Symbol(Variable(b, None)), Not(Symbol(Variable(X1, None))))
          )
        ),
        And(
          Or(Or(Symbol(Variable(X1, None)), Symbol(Variable(c, None))), Not(Symbol(Variable(X0, None)))),
          And(
            Or(Not(Symbol(Variable(X1, None))), Symbol(Variable(X0, None))),
            Or(Not(Symbol(Variable(c, None))), Symbol(Variable(X0, None)))
          )
        )
      )*/

    /*      And(
            And(
              Or(Or(Not(Symbol(Variable(a, None))), Not(Symbol(Variable(b, None)))), Symbol(Variable(X1, None))),
              And(
                Or(Symbol(Variable(a, None)), Not(Symbol(Variable(X1, None)))),
                Or(Symbol(Variable(b, None)), Not(Symbol(Variable(X1, None))))
              )
            ),
            And(
              Or(Or(Symbol(Variable(X1, None)), Symbol(Variable(c, None))), Not(Symbol(Variable(X0, None)))),
              And(
                Or(Not(Symbol(Variable(X1, None))), Symbol(Variable(X0, None))),
                Or(Not(Symbol(Variable(c, None))), Symbol(Variable(X0, None)))
              )
            )
          )*/

    /*    val eeee = And(
          And(
            Or(Or(Not(Symbol(Variable(a, None))), Not(Symbol(Variable(b, None)))), Symbol(Variable(X1, None))),
            And(
              Or(Symbol(Variable(a, None)), Not(Symbol(Variable(X1, None)))),
              Or(Symbol(Variable(b, None)), Not(Symbol(Variable(X1, None))))
            )
          ),
          And(
            Or(Or(Symbol(Variable(X1, None)), Symbol(Variable(c, None))), Not(Symbol(Variable(X0, None)))),
            And(
              Or(Not(Symbol(Variable(X1, None))), Symbol(Variable(X0, None))),
              Or(Not(Symbol(Variable(c, None))), Symbol(Variable(X0, None)))
            )
          )
        )*/

    // println(resultLeft)
    println("Risultato")
    println(resultRight)

    resultRight shouldBe expectedRight
  }
