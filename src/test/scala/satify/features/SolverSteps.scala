package satify.features

import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers.{a, shouldBe}
import satify.dsl.Reflection.reflect
import satify.features.DSLSteps.{Given, Then, When}
import satify.model.{Assignment, Solution, Variable}
import satify.model.expression.Expression
import satify.model.Result.*
import satify.update.solver.Solver
import satify.update.solver.SolverType.DPLL

object SolverSteps extends ScalaDsl with EN:

  import DSLSteps.*
  var sol: Solution = _

  private val assignmentsAsString: List[Assignment] => String = assignments =>
    val assignmentList: List[String] = assignments
      .map(assignment =>
        val v = assignment.variables
        val variableAsString: Variable => String = variable => s"${variable.name}: ${variable.value}"
        v.tail.foldLeft(s"(${variableAsString(v.head)}")((p, c) => p + s", ${variableAsString(c)}") + ")"
      )
    assignmentList.foldLeft("")((p, c) => s"$p$c${if assignmentList.last == c then "" else s", "}")

  Then("the result should be SAT")(() => sol.result shouldBe SAT)

  Then("the result should be UNSAT")(() => sol.result shouldBe UNSAT)

  And("ran using a solver that returns all the assignments")(() => sol = Solver(DPLL).solveAll(expression.get))

  And("ran using a solver that returns one assignment at a time")(() => sol = Solver(DPLL).solve(expression.get))

  And("I should obtain the assignments {string}") { (expectedAssignments: String) =>
    assignmentsAsString(sol.assignments) shouldBe expectedAssignments
  }

  And("I should obtain another assignment {string}") { (expectedAssignments: String) =>
    assignmentsAsString(Solver(DPLL).next() :: Nil) shouldBe expectedAssignments
  }
