package satify.update.parser

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.*
import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.update.parser.DimacsCNF

import scala.io.Source
import scala.util.Using

class DumpTest extends AnyFlatSpec with Matchers {

  val header: List[String] = List("c SOURCE: Satify", "c https://github.com/Mala1180/PPS-22-satify", "c",
    "c AUTHORS: Matteini Mattia, Paganelli Alberto, Fabri Luca", "c")

  "Dump method" should "dump a simple CNF formula increasing variable values" in {
    val cnf: CNF = And(
      Or(Symbol("x"), Symbol("y")),
      Or(Symbol("z"), Symbol("k"))
    )
    val expected = header ::: List(
      "p cnf 4 2",
      "1 2 0",
      "3 4 0"
    )
    val result = DimacsCNF.dump(cnf)
    result shouldEqual expected
  }

  "Dump method" should "dump CNF formula reusing some variable values for symbols" in {
    val cnf: CNF =
      And(Or(Not(Symbol("x")), Symbol("y")), Or(Symbol("z"), Not(Symbol("x"))))
    val expected = header ::: List(
      "p cnf 3 2",
      "-1 2 0",
      "3 -1 0"
    )
    val result = DimacsCNF.dump(cnf)
    result shouldEqual expected
  }

  "Dump method" should "dump CNF formula reusing some variable values for literals" in {
    val cnf: CNF =
      And(Or(Symbol("x"), Symbol("y")), Or(Symbol("z"), Not(Symbol("x"))))
    val expected = header ::: List(
      "p cnf 3 2",
      "1 2 0",
      "3 -1 0"
    )
    val result = DimacsCNF.dump(cnf)
    result shouldEqual expected
  }
}
