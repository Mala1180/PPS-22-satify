package satify.modelUtils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import satify.model.tree.expression.Expression.*
import satify.model.tree.*
import satify.model.tree.expression.Encodings.*
import satify.model.tree.expression.{And, Not, Or}

class SatEncodingsTest extends AnyFlatSpec:

  """ atLeastOne("A", "B", "C")  """ should """ be equal to "A" or "B" or "C" """ in {
    atLeastOne(expression.Symbol("A"), expression.Symbol("B"), expression.Symbol("C")) shouldBe Or(
      expression.Or(expression.Symbol("A"), expression.Symbol("B")),
      expression.Symbol("C")
    )
  }

  """ atMostOne("A", "B") """ should """ be equal to (Not("A") or "ENC0") and (Not("B") or "ENC0") """ in {
    atMostOne(expression.Symbol("A"), expression.Symbol("B")) shouldBe Not(
      And(expression.Symbol("A"), expression.Symbol("B"))
    )
//    atMostOne(Symbol("A"), Symbol("B")) shouldBe And(
//      Or(Not(Symbol("A")), Symbol("ENC0")),
//      Or(Not(Symbol("B")), Symbol("ENC0"))
//    )
  }

  """ exactlyOne("A", "B") """ should """ be equal to atMostOne("A", "B") and atLeastOne("A", "B") """ in {
    exactlyOne(expression.Symbol("A"), expression.Symbol("B")) shouldBe And(
      atLeastOne(expression.Symbol("A"), expression.Symbol("B")),
      atMostOne(expression.Symbol("A"), expression.Symbol("B"))
    )
  }

//  """ atMostOne("A", "B", "C") """ should """ be equal to  """ in {
//    atMostOne(Symbol("A"), Symbol("B"), Symbol("C")) shouldBe And(
//      And(
//        Or(Not(Symbol("A")), Symbol("ENC0")),
//        Or(
//          Not(Symbol("C")),
//          Symbol("ENC1")
//        )
//      ),
//      And(
//        And(Or(Not(Symbol("B")), Symbol("ENC1")), Or(Not(Symbol("ENC0")), Symbol("ENC1"))),
//        Or(Not(Symbol("B")), Not(Symbol("ENC0")))
//      )
//    )
//  }

//  """ ("A", "B") atMost 1 """ should """ be equal to atMostOne("A", "B") """ in {
//    ("A", "B") atMost 1 shouldBe ((Not("A") or "ENC0") and (Not("B") or "ENC0"))
//  }
//
//  """ ("A", "B") atMost one """ should """ be equal to ("A", "B") atMost 1 """ in {
//    ("A", "B") atMost one shouldBe (("A", "B") atMost 1)
//  }
//
//  """ ("A", "B", "C") atLeast 1 """ should """ be equal to atLeastOne("A", "B") """ in {
//    ("A", "B", "C") atLeast 1 shouldBe ("A" or "B" or "C")
//  }
//
//  """ ("A", "B", "C") atLeast one """ should """ be equal to ("A", "B", "C") atLeast 1 """ in {
//    ("A", "B", "C") atLeast one shouldBe (("A", "B", "C") atLeast 1)
//  }
//
//  """ atLeastK(2)("A", "B", "C")  """ should """ be equal to "A" or "B" or "C" """ in {
//    atLeastK(2)("A", "B", "C") shouldBe (("A" and "B") or ("A" and "C") or ("B" and "C"))
//  }
