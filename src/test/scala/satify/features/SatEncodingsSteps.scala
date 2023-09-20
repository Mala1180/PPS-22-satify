package satify.features

import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers.*
import satify.model.Solution
import satify.update.solver.Solver
import satify.update.solver.SolverType.DPLL

object SatEncodingsSteps extends ScalaDsl with EN:

  import DSLSteps.*
  var sol: Solution = _

  And("it is used as instance of SAT problem") {
    sol = Solver(DPLL).solveAll(expression.get)
  }

  Then("I should obtain {int} possible assignments") { (n: Int) =>
    sol.assignments.size shouldBe n
  }
