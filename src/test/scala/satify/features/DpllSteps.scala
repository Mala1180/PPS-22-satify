package satify.features

import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers.{a, shouldBe}
import satify.dsl.Reflection.reflect
import satify.features.DSLSteps.{Given, Then, When}
import satify.model.Solution
import satify.model.expression.Expression
import satify.model.Result.*
import satify.update.solver.Solver
import satify.update.solver.SolverType.DPLL

object DpllSteps extends ScalaDsl with EN:

  import DSLSteps.*
  var sol: Solution = _

  And("ran using the DPLL algorithm")(() => sol = Solver(DPLL).solveAll(expression.get))

  Then("the result should be SAT") { () =>
    sol.result shouldBe SAT
  }

  And("I should obtain the assignments {string}") { (expectedAssignments: String) =>
    val assignmentList: List[String] = sol.assignment
      .map(a =>
        a.variables.foldLeft("(")((p, c) =>
          p + s"${c.name}: ${c.value}${if a.variables.last == c then "" else ", "}"
        ) + ")"
      )
    val assignments = assignmentList
      .foldLeft("")((p, c) => s"$p$c${if assignmentList.last == c then "" else s", "}")
    assignments shouldBe expectedAssignments
  }
