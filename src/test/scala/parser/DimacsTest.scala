package parser

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.*
import satify.update.parser.DimacsCNF

import satify.model.*

class DimacsTest extends AnyFlatSpec with Matchers {

  behavior of "DimacsCNF"

  "DimacsCNF" should "parse empty formulas" in {
    DimacsCNF.parse(Seq("p cnf 0 0")) should matchPattern { case None => }
  }

  "DimacsCNF" should "parse CNF formulas" in {
    val lines = Seq(
      "c a comment", "c another comment", "p cnf 3 2", "1 2 0", "3 -1 0"
    )
    DimacsCNF.parse(lines) shouldBe
      Some(
        And(
          Or(Symbol(Variable("X_1")), Symbol(Variable("X_2"))),
          Or(Symbol(Variable("X_3")), Not(Symbol(Variable("X_1"))))
        )
      )
  }

  // TODO: after dpll conflict id merge
  /*it should "dump CNF formulas" in {
    val formula = And(
      Or(Symbol(Variable("X_1")), Symbol(Variable("X_2"))),
      Or(Symbol(Variable("X_3")), Not(Symbol(Variable("X_1"))))
    )
    DimacsCNF.dump(formula) shouldEqual Seq("p cnf 3 2", "1 2 0", "3 -1 0")
  }
   */
}
