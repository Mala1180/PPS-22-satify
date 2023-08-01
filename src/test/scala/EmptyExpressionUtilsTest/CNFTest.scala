package EmptyExpressionUtilsTest

import model.{Expression, EmptyVariable}
import Expression.*
import update.converters.TseitinTransformation.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class CNFTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ ¬(c∧d)) the CNF form" should "be correctly generated" in {
    //TODO: to implement test
    val exp = Or(
      And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))),
      Not(And(Symbol(EmptyVariable("c")), Symbol(EmptyVariable("d")))))
  }
