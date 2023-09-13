package satify.model

import satify.model.dpll.PartialAssignment

/** Sum Type representing the two possible results of SAT problem. */
enum Result:
  case SAT
  case UNSAT

/** Represent a completed variable, constrained to a value.
  * @param name name of the variable
  * @param value boolean assignment
  */
case class Variable(name: String, value: Boolean)

/** Represents a list of [[Variable]] assigned to a value.
  * @param variables the list of variables.
  */
case class Assignment(variables: List[Variable])

/** Represents a solution to the SAT problem. It is used by [[update.Solver]].
  * @param result The result of the SAT problem (SAT or UNSAT).
  * @param assignment The assignment of variables to values, if the problem is SAT.
  */
case class Solution(result: Result, assignment: List[Assignment] = Nil)

object Solution:

  extension (solution: Solution)
    def print: String =
      s"${solution.result} \n ${solution.assignment
          .map(a =>
            s"Solution ${solution.assignment.indexOf(a) + 1}" + "\n" +
              a.variables.foldLeft("")((b, c) => b + c.name + ": " + c.value + "\n")
          )
          .foldLeft("")((p, c) => p + "\n" + c)}"
