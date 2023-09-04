package satify.features

import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers.*
import satify.dsl.Reflection.reflect
import satify.model.expression.Expression

class ReflectionTest extends ScalaDsl with EN:

  var input: String = _
  var expression: Expression = _
  var error: Exception = _

  Given("the input {string}")((strExp: String) => input = strExp)
  Given("an empty input")(() => input = "")

  When("it is reflected to scala compiler")(() =>
    try expression = reflect(input)
    catch case e: IllegalArgumentException => error = IllegalArgumentException(e.getMessage)
  )

  Then("I should obtain the expression {string}") { (expected: String) =>
    expression.toString shouldBe expected
  }
  Then("I should obtain an IllegalArgumentException") { () =>
    error shouldBe a[IllegalArgumentException]
  }
