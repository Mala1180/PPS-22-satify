package satify.updateTest.parser

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.cnf.*
import satify.update.parser.DimacsCNF

import scala.io.Source
import satify.model.*
import satify.model.tree.cnf.Variable

import scala.util.Using

class DimacsTest extends AnyFlatSpec with Matchers {

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
          Or(Symbol(Variable("x_1")), Symbol(Variable("x_2"))),
          Or(Symbol(Variable("x_3")), Not(Symbol(Variable("x_1"))))
        )
      )
  }

  "DimacsCNF" should "open a DIMACS file and parse it" in {
    DimacsCNF.read("src/main/resources/cnf/aim-100-1_6-no-1.txt") should matchPattern { case Some(_) => }
  }

  // TODO
  /*
  it should "dump CNF formulas" in {
    val formula = And(
      Or(Symbol(Variable("x_1")), Symbol(Variable("x_2"))),
      Or(Symbol(Variable("x_3")), Not(Symbol(Variable("x_1"))))
    )
    DimacsCNF.dump(formula) shouldEqual Seq("p cnf 3 2", "1 2 0", "3 -1 0")
  }
   */
}
