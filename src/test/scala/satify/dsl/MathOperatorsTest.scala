package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe

class MathOperatorsTest extends AnyFlatSpec:

  import satify.model.dsl.DSL.{*, given}
  import satify.model.dsl.Operators.{->, <->, iff, implies}

  """ "A" /\ "B" """ should """ be equal to "A" and "B" """ in {
    "A" /\ "B" shouldBe ("A" and "B")
  }

  """ "A" \/ "B" """ should """ be equal to "A" or "B" """ in {
    "A" \/ "B" shouldBe ("A" or "B")
  }

  """ !"A" """ should """ be equal to not("A") """ in {
    !"A" shouldBe not("A")
  }

  """ "A" ^ "B" """ should """ be equal to "A" xor "B" """ in {
    "A" ^ "B" shouldBe ("A" xor "B")
  }

  """ "A" -> "B" """ should """ be equal to "A" implies "B" """ in {
    "A" -> "B" shouldBe ("A" implies "B")
  }

  """ "A" <-> "B" """ should """ be equal to "A" iff "B" """ in {
    "A" <-> "B" shouldBe ("A" iff "B")
  }
