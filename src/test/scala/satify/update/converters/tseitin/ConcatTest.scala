package satify.update.converters.tseitin

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.update.converters.tseitin.TseitinTransformation.concat

class ConcatTest extends AnyFlatSpec with Matchers:

  "Concatenate only one symbol" should "return the symbol without concatenating nothing to it" in {
    val exps: List[CNF] = List(Symbol("a"))
    concat(exps) shouldBe Symbol("a")
  }

  "Concatenate symbols" should "return a valid CNF introducing a new variable representing entire expression" in {
    val exps: List[CNF] = List(Symbol("a"), Symbol("b"), Symbol("c"))
    val expected: CNF = And(
      Symbol("GEN0"),
      And(Symbol("a"), And(Symbol("b"), Symbol("c")))
    )
    concat(exps) shouldBe expected
  }

  "Concatenate subexpressions" should "return a valid CNF always adding the equivalence constraint" in {
    val exps: List[CNF] = List(Or(Symbol("a"), Symbol("b")), And(Symbol("c"), Symbol("d")))
    val expected: CNF = And(
      Symbol("GEN0"),
      And(
        Or(Symbol("a"), Symbol("b")),
        And(Symbol("c"), Symbol("d"))
      )
    )
    concat(exps) shouldBe expected
  }
