package satify.update.converters

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.CNF.*
import satify.model.{CNF}

class CNFOutputTest extends AnyFlatSpec with Matchers:

  "The cnf symbol a" should "be printed as a in formal way" in {
    val cnfExp: CNF = Symbol("a")
    val fRes: String = cnfExp.printAsFormal(true)
    val nfRes: String = cnfExp.printAsFormal()
    val expected = "a"
    List(fRes, nfRes) shouldBe List(expected, expected)
  }

  "The cnf exp ((a ∨ ¬b) ∧ c)" should "be printed in the formal way and according to the output flat mode" in {
    val cnfExp: CNF =
      And(Or(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val fRes: String = cnfExp.printAsFormal(true)
    val fExp: String = "a ∨ ¬(b) ∧ c"
    val nfRes: String = cnfExp.printAsFormal()
    val nfExp = "a ∨ ¬(b) ∧\nc"
    List(fRes, nfRes) shouldBe List(fExp, nfExp)
  }

  "The only symbol a" should "be printed as a in DSL" in {
    val exp: CNF = Symbol("a")
    val fRes: String = exp.printAsDSL(true)
    val nfRes: String = exp.printAsDSL()
    val expected = "a"
    List(fRes, nfRes) shouldBe List(expected, expected)
  }

  "The exp ((a ∨ ¬b) ∧ c)" should "be printed in the DSL format according to the output flat mode" in {
    val exp: CNF =
      And(Or(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    val fRes: String = exp.printAsDSL(true)
    val fExp = "a or not(b) and c"
    val nfRes: String = exp.printAsDSL()
    val nfExp = "a or not(b) and\nc"
    List(fRes, nfRes) shouldBe List(fExp, nfExp)
  }
