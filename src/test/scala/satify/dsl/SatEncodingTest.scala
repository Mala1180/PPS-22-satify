package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import satify.model.expression.Expression.*

class SatEncodingTest extends AnyFlatSpec:

  import satify.dsl.DSL.{*, given}

  given ResettableSymbolGenerator with
    def prefix: String = "ENC"

  """ ("A", "B") atMost 1 """ should """ be equal to atMostOne("A", "B") """ in {
//    given SymbolGenerator with
//      def prefix: String = "cuai"
    ("A", "B") atMost 1 shouldBe atMostOne("A", "B")
  }

  """ ("A", "B") atMost one """ should """ be equal to ("A", "B") atMost 1 """ in {
    ("A", "B") atMost one shouldBe (("A", "B") atMost 1)
  }

  """ ("A", "B", "C") atLeast 1 """ should """ be equal to atLeastOne("A", "B", "C") """ in {
    ("A", "B", "C") atLeast 1 shouldBe atLeastOne("A", "B", "C")
  }

  """ ("A", "B", "C") atLeast 2  """ should """ be equal to atLeastK(2)("A", "B", "C") """ in {
    ("A", "B", "C") atLeast 2 shouldBe atLeastK(2)("A", "B", "C")
  }
