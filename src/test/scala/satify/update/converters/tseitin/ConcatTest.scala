package satify.update.converters.tseitin

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.cnf.Variable
import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.update.converters.tseitin.TseitinTransformation.concat

class ConcatTest extends AnyFlatSpec with Matchers:

  "Concatenate only one symbol" should "return the symbol without concatenating nothing to it" in {
    val exps: List[CNF] = List(Symbol(Variable("a")))
    val result: CNF = concat(exps)
    println(result)
    val expected: CNF = Symbol(Variable("a", None))
    result shouldBe expected
  }

  "Concatenate symbols" should "return a valid CNF introducing a new variable representing entire expression" in {
    val exps: List[CNF] = List(Symbol(Variable("a")), Symbol(Variable("b")), Symbol(Variable("c")))
    val result: CNF = concat(exps)
    val expected: CNF = And(
      Symbol(Variable("TSTN0")),
      And(Symbol(Variable("a")), And(Symbol(Variable("b")), Symbol(Variable("c"))))
    )
    result shouldBe expected
  }

  "Concatenate subexpressions" should "return a valid CNF always adding the equivalence constraint" in {
    val exps: List[CNF] =
      List(Or(Symbol(Variable("a")), Symbol(Variable("b"))), And(Symbol(Variable("c")), Symbol(Variable("d"))))
    val result: CNF = concat(exps)
    val expected: CNF = And(
      Symbol(Variable("TSTN0")),
      And(
        Or(Symbol(Variable("a")), Symbol(Variable("b"))),
        And(Symbol(Variable("c")), Symbol(Variable("d")))
      )
    )
    result shouldBe expected
  }
