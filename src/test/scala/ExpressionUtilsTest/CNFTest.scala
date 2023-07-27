package ExpressionUtilsTest

import model.Expression
import model.Operator
import Expression.*
import model.Operator.*
import tseitin.Transformation.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class CNFTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ ¬(c∧d)) the CNF form" should "be correctly generated" in {
    //TODO: implement test
    val exp = Clause(Or(
      Clause(And(Literal("a"), Clause(Not(Literal("b"))))),
      Clause(Not(Clause(And(Literal("c"), Literal("d")))))))


  }
