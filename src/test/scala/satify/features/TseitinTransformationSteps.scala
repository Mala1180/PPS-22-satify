package satify.features

import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers.*
import satify.model.cnf.CNF
import satify.update.converters.{Converter, ConverterType}

object TseitinTransformationSteps extends ScalaDsl with EN:

  import DSLSteps.*
  var cnf: Option[CNF] = None

  And("converted to CNF Form")(() =>
    try cnf = Some(Converter(ConverterType.Tseitin).convert(expression.get))
    catch case e: IllegalArgumentException => error = IllegalArgumentException(e.getMessage)
  )
  And("no CNF has to be generated")(() => cnf.isEmpty shouldBe true)

  Then("I should obtain the CNF {string}") { (expected: String) =>
    cnf.get.printAsDSL(true) shouldBe expected
  }
