package satify.model.cnf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CNFOutputTest extends AnyFlatSpec with Matchers:

  import satify.model.cnf.CNF.*

  "The cnf symbol a" should "be printed as a in formal way" in {
    Symbol("a").printAsFormal(true) shouldBe "a"
    Symbol("a").printAsFormal() shouldBe "a"
  }

  "The cnf exp ((a ∨ ¬b) ∧ c)" should "be printed in the formal way and according to the output flat mode" in {
    val cnfExp: CNF = And(Or(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    cnfExp.printAsFormal(true) shouldBe "a ∨ ¬(b) ∧ c"
    cnfExp.printAsFormal() shouldBe "a ∨ ¬(b) ∧\nc"
  }

  "The only symbol a" should "be printed as a in DSL" in {
    Symbol("a").printAsDSL(true) shouldBe "a"
    Symbol("a").printAsDSL() shouldBe "a"
  }

  "The exp ((a ∨ ¬b) ∧ c)" should "be printed in the DSL format according to the output flat mode" in {
    val exp: CNF = And(Or(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    exp.printAsDSL(true) shouldBe "a or not(b) and c"
    exp.printAsDSL() shouldBe "a or not(b) and\nc"
  }
