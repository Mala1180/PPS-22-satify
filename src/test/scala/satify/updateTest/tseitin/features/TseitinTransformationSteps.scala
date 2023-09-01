package satify.updateTest.tseitin.features

import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers.*
import satify.dsl.Reflection.reflect
import satify.update.converters.TseitinTransformation.tseitin

class TseitinTransformationSteps extends ScalaDsl with EN:
  var exp: Expression = _
  var cnf: CNF = _
  Given("""The expression {string}""")((strExp: String) => exp = reflect(strExp))
  When("""I convert it to CNF Form""")(() => cnf = tseitin(exp))
  Then("""I should obtain the CNF {string}""") { (expected: String) =>
    cnf.printAsDSL(true) shouldBe expected
  }
