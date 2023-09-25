package satify.features

import io.cucumber.datatable.DataTable
import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.must.Matchers.{have, not}
import org.scalatest.matchers.should.Matchers.{a, should, shouldBe}
import satify.model.Result.*
import satify.model.{Assignment, Solution, Variable}
import satify.update.solver.Solver
import satify.update.solver.SolverType.DPLL

object SolverSteps extends ScalaDsl with EN:

  import DSLSteps.*
  var sol: Solution = _

  private val assignmentAsString: Assignment => String = assignment => {
    val v = assignment.variables
    val variableAsString: Variable => String = variable => s"${variable.name}: ${variable.value}"
    v.tail.foldLeft(s"(${variableAsString(v.head)}")((p, c) => p + s", ${variableAsString(c)}") + ")"
  }

  Then("the result should be SAT")(sol.result shouldBe SAT)

  Then("the result should be UNSAT")(sol.result shouldBe UNSAT)

  And("it is passed in input to a solver that returns all the assignments") {
    sol = Solver(DPLL).solveAll(expression.get, true)
  }

  And("it is passed in input to a solver that returns one assignment at a time") {
    sol = Solver(DPLL).solve(expression.get)
  }

  And("I should obtain the assignments:") { (expectedAssignments: DataTable) =>
    Set(sol.assignments.map(assignmentAsString): _*) shouldBe
      Set(expectedAssignments.asList().toArray: _*)
  }

  And("I should obtain an assignment in:") { (expectedAssignments: DataTable) =>
    val assignments = sol.assignments
    assignments should have size 1
    expectedAssignments.asList().toArray contains assignmentAsString(sol.assignments.head)
  }

  And("I should obtain another assignment different from the previous one") {
    Solver(DPLL).next should not be sol.assignments.head
  }

  And("I should obtain no assignments")(sol.assignments should have size 0)
