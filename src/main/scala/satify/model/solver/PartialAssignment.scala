package satify.model.solver

import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.model.solver.*
import satify.model.{Assignment, Variable}

/** Represents an [[Assignment]] where the variables could not be yet constrained by the DPLL algorithm.
  * @param optVariables optional variables.
  */
case class PartialAssignment(optVariables: List[OptionalVariable]):
  lazy val toAssignments: List[Assignment] = explodeAssignments(this)

  /** Cartesian product of all possible variable assignments to a partial assignment.
    * @param pa partial assignment
    * @return cartesian product of pa
    */
  private def explodeAssignments(pa: PartialAssignment): List[Assignment] = pa match
    case PartialAssignment(optVariables) =>
      optVariables match
        case ::(head, next) =>
          head match
            case OptionalVariable(name, None) =>
              if next.nonEmpty then
                val assignments = PartialAssignment(next).toAssignments
                assignments.flatMap { case Assignment(v) =>
                  Assignment(Variable(name, true) +: v) :: Assignment(Variable(name, false) +: v) :: Nil
                }
              else Assignment(List(Variable(name, true))) :: Assignment(List(Variable(name, false))) :: Nil
            case v @ OptionalVariable(_, Some(_)) =>
              if next.nonEmpty then
                PartialAssignment(next).toAssignments
                  .map { case Assignment(variables) => Assignment(v.toVariable +: variables) }
              else List(Assignment(List(v.toVariable)))
        case Nil => Nil
