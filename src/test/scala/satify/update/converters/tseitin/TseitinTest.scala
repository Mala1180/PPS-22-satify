package satify.update.converters.tseitin

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.CNF
import satify.model.cnf.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.expression.Expression.*

class TseitinTest extends AnyFlatSpec with Matchers:

  import satify.update.converters.tseitin.TseitinTransformation.tseitin

  "The CNF form of b" should "remain b" in {
    val exp = Symbol("b")
    tseitin(exp) shouldBe CNFSymbol("b")
  }

  "The CNF form of ¬b" should "be correctly generated" in {
    val exp = Not(Symbol("b"))
    val expected: CNF = CNFAnd(
      CNFSymbol("GEN0"),
      CNFAnd(
        CNFOr(CNFNot(CNFSymbol("b")), CNFNot(CNFSymbol("GEN0"))),
        CNFOr(CNFSymbol("b"), CNFSymbol("GEN0"))
      )
    )
    tseitin(exp) shouldBe expected
  }

  "For exp (a ∨ b) only the '∨' transformation" should "be applied" in {
    val exp = Or(Symbol("a"), Symbol("b"))
    val expected: CNF = CNFAnd(
      CNFSymbol("GEN0"),
      CNFAnd(
        CNFOr(
          CNFOr(CNFSymbol("a"), CNFSymbol("b")),
          CNFNot(CNFSymbol("GEN0"))
        ),
        CNFAnd(
          CNFOr(CNFNot(CNFSymbol("a")), CNFSymbol("GEN0")),
          CNFOr(CNFNot(CNFSymbol("b")), CNFSymbol("GEN0"))
        )
      )
    )
    tseitin(exp) shouldBe expected
  }
