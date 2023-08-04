package ExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression
import satify.model.Expression.*
import satify.update.converters.TseitinTransformation.transform

class TseitinCNFTest extends AnyFlatSpec with Matchers:

  "The CNF form test" should "be" in {
    val exp = Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val result = toCNF(exp)
    println(result)
    result shouldBe expected
  }

