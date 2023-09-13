package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import satify.model.expression.Expression.*
import satify.model.expression.SymbolGeneration.{ErasableSymbolGenerator, encodingVarPrefix}

class SatEncodingTest extends AnyFlatSpec:

  import satify.dsl.DSL.{*, given}

  given ErasableSymbolGenerator with
    override def prefix: String = encodingVarPrefix

  """ ("A", "B") atMost 1 """ should """ be equal to atMostOne("A", "B") """ in {
    ("A", "B") atMost 1 shouldBe atMostOne("A", "B")
  }

  """ ("A", "B") atMost one """ should """ be equal to ("A", "B") atMost 1 """ in {
    ("A", "B") atMost one shouldBe (("A", "B") atMost 1)
  }

  """ ("A", "B", "C") atLeast 1 """ should """ be equal to atLeastOne("A", "B", "C") """ in {
    ("A", "B", "C") atLeast 1 shouldBe atLeastOne("A", "B", "C")
  }

  """ ("A", "B", "C", "D") atLeast three  """ should """ be equal to atLeastK(3)("A", "B", "C", "D") """ in {
    ("A", "B", "C", "D") atLeast three shouldBe atLeastK(3)("A", "B", "C", "D")
  }

  """ ("A", "B") exactly one """ should """ be equal to exactly(1)("A", "B") """ in {
    ("A", "B") exactly one shouldBe exactlyK(1)("A", "B")
  }

  """ ("A", "B", "C") exactly two """ should """ be equal to exactlyK(1)("A", "B", "C") """ in {
    ("A", "B", "C") exactly two shouldBe exactlyK(2)("A", "B", "C")
  }
