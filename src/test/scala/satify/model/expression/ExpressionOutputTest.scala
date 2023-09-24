package satify.model.expression

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.expression.Expression
import satify.model.expression.Expression.*

class ExpressionOutputTest extends AnyFlatSpec with Matchers:

  "The only symbol a" should "be printed as a in formal way" in {
    val exp: Expression = Symbol("a")
    val fRes: String = exp.asFormal(true)
    val nfRes: String = exp.asFormal(false)
    val expected = "a"
    List(fRes, nfRes) shouldBe List(expected, expected)
  }

  "The exp ((a ∧ ¬b) ∨ c)" should "be printed in the formal way according to the output flat mode" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val fRes: String = exp.asFormal(true)
    val fExp = "((a ∧ ¬(b)) ∨ c)"
    val nfRes: String = exp.asFormal(false)
    val nfExp = "((a ∧\n¬(b)) ∨ c)"
    List(fRes, nfRes) shouldBe List(fExp, nfExp)
  }

  "The only symbol a" should "be printed as a in DSL" in {
    val exp: Expression = Symbol("a")
    val fRes: String = exp.asDSL(true)
    val nfRes: String = exp.asDSL(false)
    val expected = "a"
    List(fRes, nfRes) shouldBe List(expected, expected)
  }

  "The exp ((a ∧ ¬b) ∨ c)" should "be printed in the DSL format according to the output flat mode" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val fRes: String = exp.asDSL(true)
    val fExp = "((a and not(b)) or c)"
    val nfRes: String = exp.asDSL(false)
    val nfExp = "((a and\nnot(b)) or c)"
    List(fRes, nfRes) shouldBe List(fExp, nfExp)
  }
