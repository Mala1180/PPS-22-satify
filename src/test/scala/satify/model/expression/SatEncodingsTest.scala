package satify.model.expression

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import satify.model.expression.Expression.*
import satify.model.expression.SymbolGeneration.{ErasableSymbolGenerator, encodingVarPrefix}

class SatEncodingsTest extends AnyFlatSpec:

  given ErasableSymbolGenerator with
    override def prefix: String = encodingVarPrefix

  """ atLeastOne("A", "B", "C")  """ should """ be equal to "A" or "B" or "C" """ in {
    atLeastOne(Symbol("A"), Symbol("B"), Symbol("C")) shouldBe Or(Or(Symbol("A"), Symbol("B")), Symbol("C"))
  }

  """ atMostOne("A", "B") """ should """ be equal to (Not("A") or "ENC0") and (Not("B") or "ENC0") """ in {
    atMostOne(Symbol("A"), Symbol("B")) shouldBe And(
      Or(Not(Symbol("A")), Symbol("ENC0")),
      Or(Not(Symbol("B")), Not(Symbol("ENC0")))
    )
  }

  """ exactly(1)("A", "B") """ should """ be equal to atMostOne("A", "B") and atLeastOne("A", "B") """ in {
    exactlyK(1)(Symbol("A"), Symbol("B")) shouldBe And(
      atLeastOne(Symbol("A"), Symbol("B")),
      atMostOne(Symbol("A"), Symbol("B"))
    )
  }

  """ exactly(2)("A", "B", "C") """ should """ be equal to atMostK(2)("A", "B", "C") and atLeastK(2)("A", "B", "C") """ in {
    exactlyK(2)(Symbol("A"), Symbol("B"), Symbol("C")) shouldBe And(
      atLeastK(2)(Symbol("A"), Symbol("B"), Symbol("C")),
      atMostK(2)(Symbol("A"), Symbol("B"), Symbol("C"))
    )
  }
