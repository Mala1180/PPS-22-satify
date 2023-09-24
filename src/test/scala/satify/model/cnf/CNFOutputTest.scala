package satify.model.cnf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CNFOutputTest extends AnyFlatSpec with Matchers:

  import satify.model.cnf.CNF.*

  "The cnf symbol a" should "be printed as a in formal way" in {
    Symbol("a").asFormal(true) shouldBe "a"
    Symbol("a").asFormal() shouldBe "a"
  }

  "The cnf exp ((a ∨ ¬b) ∧ c)" should "be printed in the formal way and according to the output flat mode" in {
    val cnfExp: CNF = And(Or(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    cnfExp.asFormal(true) shouldBe "a ∨ ¬(b) ∧ c"
    cnfExp.asFormal() shouldBe "a ∨ ¬(b) ∧\nc"
  }

  "The only symbol a" should "be printed as a in DSL" in {
    Symbol("a").asDSL(true) shouldBe "a"
    Symbol("a").asDSL() shouldBe "a"
  }

  "The exp ((a ∨ ¬b) ∧ c)" should "be printed in the DSL format according to the output flat mode" in {
    val exp: CNF = And(Or(Symbol("a"), Not(Symbol("b"))), Symbol("c"))
    exp.asDSL(true) shouldBe "a or not(b) and c"
    exp.asDSL() shouldBe "a or not(b) and\nc"
  }
