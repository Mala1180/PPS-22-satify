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
      s"${solution.result}: ${
          if solution.assignment.size > 1 then solution.assignment.size + " Solutions"
          else solution.assignment.size + " Solution"
        }  \n${
          if solution.assignment.isEmpty then ""
          else solution.assignment.head.variables.foldLeft("")((b, c) => b + c.name + ": " + c.value + "\n")
        }"
