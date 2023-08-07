package steps

import io.cucumber.scala.{EN, ScalaDsl}

class ExampleCucumberSteps extends ScalaDsl with EN:
  var (a, b, sum) = (0, 0, 0)
  Given("""one operand {int}""")(a = _: Int)
  And("""another operand {int}""")(b = _: Int)
  When("""I multiply them together""")(() => sum = a * b)
  Then("""I should obtain result {int}""") { (expectedResult: Int) =>
    println(s"expectedResult: $expectedResult")
  }
