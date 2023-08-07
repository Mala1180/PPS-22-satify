package satify.model

/** Sum Type representing the two possible results of SAT problem. */
enum Result:
  case SAT
  case UNSAT

/** Represents a list of [[Variable]] assigned to a value.
  * @param list The list of [[Variable]].
  */
case class Assignment(list: List[Variable])

/** Represents a solution to the SAT problem. It is used by [[update.Solver]].
  * @param result The result of the SAT problem (SAT or UNSAT).
  * @param assignment The assignment of variables to values, if the problem is SAT.
  */
case class Solution(result: Result, assignment: Option[Assignment] = None)
