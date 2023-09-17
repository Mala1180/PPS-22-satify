package satify.model.expression

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.expression.Expression
import satify.model.expression.Expression.*

class ExpressionUtilsTest extends AnyFlatSpec with Matchers:

  "The method clauses" should "return the number of clauses contained in the expression" in {
    val exp1: Expression = Symbol("a")
    clauses(exp1) shouldBe 1
    val exp2: Expression = Not(Symbol("a"))
    clauses(exp2) shouldBe 1
    val exp3: Expression = Or(Symbol("a"), Symbol("b"))
    clauses(exp3) shouldBe 1
    val exp4: Expression = Or(Not(Symbol("a")), Symbol("b"))
    clauses(exp4) shouldBe 2
    val exp5: Expression = Or(Not(Symbol("a")), Not(Symbol("b")))
    clauses(exp5) shouldBe 3
  }

  "The method contains" should "return true if a subexpression is contained in the expression" in {
    val exp1: Expression = Symbol("a")
    contains(exp1, Symbol("b")) shouldBe false
    // note, a literal cannot contain a literal
    val exp2: Expression = Not(Symbol("a"))
    contains(exp2, Symbol("a")) shouldBe false
    val exp3: Expression = Or(Symbol("a"), Or(Symbol("b"), Symbol("c")))
    contains(exp3, Or(Symbol("b"), Symbol("c"))) shouldBe true
    val exp4: Expression = Or(Symbol("a"), Or(Symbol("b"), Symbol("c")))
    contains(exp4, Or(Symbol("a"), Symbol("c"))) shouldBe false
  }
