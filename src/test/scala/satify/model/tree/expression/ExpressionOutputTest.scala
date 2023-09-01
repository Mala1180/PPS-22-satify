package satify.model.tree.expression

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.tree.*
import satify.model.tree.cnf.Variable
import satify.model.tree.expression.ExpressionUtils.*

class ExpressionOutputTest extends AnyFlatSpec with Matchers:

  import satify.model.tree.expression.*
  "The exp ((a ∧ ¬b) ∨ c)" should "be printed as in DSL format and according to the output flat mode" in {
    val exp: Expression =
      Or(And(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val fRes: String = exp.printAsFormal(true)
    val fExp = "a and not(b) or c"
    val nfRes: String = exp.printAsFormal(false)
    val nfExp = "a and\nnot(b) or c"
    List(fRes, nfRes) shouldBe List(fExp, nfExp)
  }

  "The only symbol a" should "be printed as a in any case" in {
    val exp: Expression = Symbol("a")
    val fRes: String = exp.printAsFormal(true)
    val nfRes: String = exp.printAsFormal(false)
    val expected = "a"
    List(fRes, nfRes) shouldBe List(expected, expected)
  }
