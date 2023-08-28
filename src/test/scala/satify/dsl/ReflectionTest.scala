package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import satify.model.Expression

class ReflectionTest extends AnyFlatSpec:

  import satify.dsl.Reflection.*
  import satify.model.Expression.*

  """ processInput """ should """ add "" to all variables, excluding operators """ in {
    processInput("a or not(B) and c") shouldBe """"a" or not("B") and "c""""
    processInput("xor(or(a, b), and(c, d))") shouldBe """xor(or("a", "b"), and("c", "d"))"""
  }

  """ processInput """ should """ work also excluding math operators """ in {
    processInput("""a \/ !B /\ c""") shouldBe """"a" \/ !"B" /\ "c""""
    processInput("""\/(/\(a, b), !(b, c))""") shouldBe """\/(/\("a", "b"), !("b", "c"))"""
  }

  """ processInput """ should """ work also works with SAT encodings """ in {
    processInput(
      """atLeast(1)(a, b, c) and atMostOne(a, b)"""
    ) shouldBe """atLeast(1)("a", "b", "c") and atMostOne("a", "b")"""
  }

  """ processInput """ should """ ignore number constants """ in {
    processInput("""atLeast(two)(a, b, c)""") shouldBe """atLeast(two)("a", "b", "c")"""
    processInput("""(a, b, c) atLeast three""") shouldBe """("a", "b", "c") atLeast three"""
  }

  """ reflection """ should """return a valid expression """ in {
    reflect("a or not(B) and c") shouldBe And(Or(Symbol("a"), Not(Symbol("B"))), Symbol("c"))
    reflect("(a -> b) and !c") shouldBe And(Or(Not(Symbol("a")), Symbol("b")), Not(Symbol("c")))
    reflect("(a, b, c) atLeast two and ((c, d, e) atMost one)") shouldBe And(
      Or(Or(And(Symbol("a"), Symbol("b")), And(Symbol("a"), Symbol("c"))), And(Symbol("b"), Symbol("c"))),
      And(
        And(Or(Not(Symbol("c")), Symbol("ENC0")), Or(Not(Symbol("e")), Symbol("ENC1"))),
        And(
          And(Or(Not(Symbol("d")), Symbol("ENC1")), Or(Not(Symbol("ENC0")), Symbol("ENC1"))),
          Or(Not(Symbol("d")), Not(Symbol("ENC0")))
        )
      )
    )
  }
