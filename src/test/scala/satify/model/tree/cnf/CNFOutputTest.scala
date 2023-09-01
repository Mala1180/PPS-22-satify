package satify.model.tree.cnf

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.shouldBe
import satify.model.tree.cnf.*
import satify.model.tree.expression.Expression
import satify.model.tree.expression.Encodings.*
import satify.model.tree.cnf.CNF.*
import satify.model.tree.cnf.CNFUtils.*

class CNFOutputTest extends AnyFlatSpec with Matchers:

  "The cnf exp ((a ∧ ¬b) ∨ c)" should "be printed in the formal way and according to the output flat mode" in {
    val cnf: CNF =
      And(Or(Symbol(Variable("a")), Not(Symbol(Variable("b")))), Symbol(Variable("c")))
    val fRes: String = cnf.printAsFormal(true)
    val fExp: String = "a ∨ ¬b ∧ c"
    val nfRes: String = cnf.printAsFormal()
    val nfExp = "a ∨ ¬b ∧\nc"
    List(fRes, nfRes) shouldBe List(fExp, nfExp)
  }

  "The cnf symbol a" should "be printed as a in any case" in {
    val cnf: CNF = Symbol(Variable("a"))
    val fRes: String = cnf.printAsFormal(true)
    val nfRes: String = cnf.printAsFormal()
    val expected = "a"
    List(fRes, nfRes) shouldBe List(expected, expected)
  }
