package satify.EmptyExpressionUtilsTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Expression.*
import satify.model.{EmptyVariable, Expression}

class CNFTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ ¬(c∧d)) the CNF form" should "be correctly generated" in {
    // TODO: to implement test
    val exp = Or(
      And(Symbol(EmptyVariable("a")), Not(Symbol(EmptyVariable("b")))),
      Not(And(Symbol(EmptyVariable("c")), Symbol(EmptyVariable("d"))))
    )
  }
