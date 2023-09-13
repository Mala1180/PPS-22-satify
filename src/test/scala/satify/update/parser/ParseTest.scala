package satify.update.parser

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.*
import satify.model.cnf.CNF.*
import satify.update.parser.DimacsCNF

import scala.io.Source
import scala.util.Using

class ParseTest extends AnyFlatSpec with Matchers {

  "DimacsCNF" should "parse empty formulas" in {
    DimacsCNF.parse(Seq("p cnf 0 0")) should matchPattern { case None => }
  }

  "DimacsCNF" should "parse CNF formulas" in {
    val lines = Seq(
      "c a comment", "c another comment", "p cnf 3 2", "1 2 0", "3 -1 0"
    )
    DimacsCNF.parse(lines) shouldBe
      Some(And(Or(Not(Symbol("x_1")), Symbol("x_3")), Or(Symbol("x_2"), Symbol("x_1"))))
  }

  "DimacsCNF" should "open a DIMACS file and parse it" in {
    DimacsCNF.read("src/main/resources/cnf/aim-100-1_6-no-1.txt") should matchPattern { case Some(_) => }
  }
}
