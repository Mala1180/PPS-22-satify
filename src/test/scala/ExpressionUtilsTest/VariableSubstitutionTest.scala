package ExpressionUtilsTest

import model.Expression
import model.Operator
import Expression.*
import model.Operator.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class VariableSubstitutionTest extends AnyFlatSpec with Matchers:

  import tseitin.Transformation.*
  //((a ∧ ¬b) ∨ c) => List[x2 = ¬b, x1 = (a ∧ x2), x0 = (x1 ∨ c)]

  "In ((a ∧ ¬b) ∨ c) the replacement of subexpressions with variables " should "be done from the nested level to the top" in {
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val list: List[(Literal, Expression)] = replaceVariables(exp)

    println("-------------------")
    println(list)
    println("-------------------")
    
    val expectedList : List[(Literal, Expression)] = List(
      (Literal("X2"), Clause(Not(Literal("b")))),
      (Literal("X1"), Clause(And(Literal("a"), Literal("X2")))),
      (Literal("X0"), Clause(Or(Literal("X1"), Literal("c"))))
    )

    expectedList shouldBe list
  }