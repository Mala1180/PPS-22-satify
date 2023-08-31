package satify.modelUtils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.{CNF, Variable}

class OutputTest extends AnyFlatSpec with Matchers:

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

  import CNF.*
  "The cnf exp ((a ∧ ¬b) ∨ c)" should "be printed in the formal way and according to the output flat mode" in {
    val cnfExp: CNF =
      And(Or(Symbol(Variable("a")), Not(Symbol(Variable("b")))), Symbol(Variable("c")))
    val fRes: String = cnfExp.printAsFormal(true)
    val fExp: String = "a ∨ ¬b ∧ c"
    val nfRes: String = cnfExp.printAsFormal()
    val nfExp = "a ∨ ¬b ∧\nc"
    List(fRes, nfRes) shouldBe List(fExp, nfExp)
  }

  "The cnf symbol a" should "be printed as a in any case" in {
    val cnfExp: CNF = Symbol(Variable("a"))
    val fRes: String = cnfExp.printAsFormal(true)
    val nfRes: String = cnfExp.printAsFormal()
    val expected = "a"
    List(fRes, nfRes) shouldBe List(expected, expected)
  }
