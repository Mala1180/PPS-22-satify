package satify.model.expression

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.expression.Expression.*

class ExpressionUtilsTest extends AnyFlatSpec with Matchers:

  "The method clauses" should "return the number of clauses contained in the expression" in {
    val exp1: Expression = Symbol("a")
    exp1.clauses shouldBe 1
    val exp2: Expression = Not(Symbol("a"))
    exp2.clauses shouldBe 1
    val exp3: Expression = Or(Symbol("a"), Symbol("b"))
    exp3.clauses shouldBe 1
    val exp4: Expression = Or(Not(Symbol("a")), Symbol("b"))
    exp4.clauses shouldBe 2
    val exp5: Expression = Or(Not(Symbol("a")), Not(Symbol("b")))
    exp5.clauses shouldBe 3
  }

  "The method contains" should "return true if a subexpression is contained in the expression" in {
    val exp1: Expression = Symbol("a")
    exp1.contains(Symbol("b")) shouldBe false
    // note, a literal cannot contain a literal
    val exp2: Expression = Not(Symbol("a"))
    exp2.contains(Symbol("a")) shouldBe false
    val exp3: Expression = Or(Symbol("a"), Or(Symbol("b"), Symbol("c")))
    exp3.contains(Or(Symbol("b"), Symbol("c"))) shouldBe true
    val exp4: Expression = Or(Symbol("a"), Or(Symbol("b"), Symbol("c")))
    exp4.contains(Or(Symbol("a"), Symbol("c"))) shouldBe false
  }
