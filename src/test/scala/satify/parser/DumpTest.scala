package satify.parser

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.*
import satify.model.cnf.CNF.*
import satify.model.cnf.{CNF, Variable}
import satify.update.parser.DimacsCNF

import scala.io.Source
import scala.util.Using

class DumpTest extends AnyFlatSpec with Matchers {

/*  "Dump method" should "dump an empty formulas" in {
    DimacsCNF.parse(Seq("p cnf 0 0")) should matchPattern { case None => }
  }*/

  "Dump method" should "dump a simple CNF formula" in {
    val cnf: CNF = And(
      Or(Symbol(Variable("x")), Symbol(Variable("y"))),
      Or(Symbol(Variable("z")), Symbol(Variable("k")))
    )
    val expected = Seq(
      "p cnf 4 2", "1 2 0", "3 4 0"
    )

    val result = DimacsCNF.dump(cnf)
    println(result)

    result shouldEqual expected
  }
}
/*  "DimacsCNF" should "open a DIMACS file and parse it" in {
    DimacsCNF.read("src/main/resources/cnf/aim-100-1_6-no-1.txt") should matchPattern { case Some(_) => }
  }*/

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

