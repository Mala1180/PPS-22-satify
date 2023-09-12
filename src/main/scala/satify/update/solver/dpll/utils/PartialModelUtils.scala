package satify.update.solver.dpll.utils

import satify.model.{Assignment, Variable}
import satify.model.cnf.Bool.True
import satify.model.cnf.CNF.*
import satify.model.cnf.CNF
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.OrderedList.{list, *}
import satify.model.dpll.{Constraint, Decision, DecisionTree, OptionalVariable, PartialAssignment}

import scala.::

object PartialModelUtils:

  import satify.model.dpll.OrderedList.given_Ordering_OptionalVariable

  /** Extract a partial assignment from an expression in CNF.
    *
    * @param cnf where to extract a Model
    * @return correspondent partial assignment from CNF given as parameter.
    */
  def extractParAssignmentFromCnf(cnf: CNF): PartialAssignment =

    def extractOptVars(cnf: CNF): List[OptionalVariable] = cnf match
      case Symbol(name: String) => list(OptionalVariable(name))
      case And(e1, e2) => list(extractOptVars(e1) ++ extractOptVars(e2): _*)
      case Or(e1, e2) => list(extractOptVars(e1) ++ extractOptVars(e2): _*)
      case Not(e) => extractOptVars(e)
      case _ => list()

    PartialAssignment(extractOptVars(cnf))

  /** Filters unconstrained variables from the partial model
    *
    * @param partialAssignment partial model
    * @return filtered partial model
    */
  def filterUnconstrVars(partialAssignment: PartialAssignment): List[OptionalVariable] =
    partialAssignment match
      case PartialAssignment(optVariables) =>
        optVariables.filter { case OptionalVariable(_, o) => o.isEmpty }

  /** Update a partial assignment given a constraint as parameter
    *
    * @param partialAssignment  partial model
    * @param varConstr variable constraint
    * @return updated partial assignment
    */
  def updatePartialAssignment(partialAssignment: PartialAssignment, varConstr: Constraint): PartialAssignment =
    partialAssignment match
      case PartialAssignment(optVariables) =>
        PartialAssignment(optVariables.map {
          case OptionalVariable(name, _) if name == varConstr.name =>
            OptionalVariable(name, Some(varConstr.value))
          case v => v
        })

  /** Get all SAT solutions, e.g. all Leaf nodes where the CNF has been simplified to Symbol(True).
    *
    * @param dt DecisionTree
    * @return a set of PartialModel(s).
    */
  def extractParAssignments(dt: DecisionTree): List[PartialAssignment] =
    dt match
      case Leaf(Decision(PartialAssignment(optVars), cnf)) =>
        cnf match
          case Symbol(True) =>
            List(
              PartialAssignment(
                optVars.filter(v =>
                  v match
                    case OptionalVariable(name, _) if name.startsWith("TSTN") || name.startsWith("ENC") => false
                    case _ => true
                )
              )
            )
          case _ => Nil
      case Branch(_, left, right) => extractParAssignments(left) ++ extractParAssignments(right)

  /** Cartesian product of all possible variable assignments to a partial assignment.
    *
    * @param pa partial assignment
    * @return cartesian product of pa
    */
  def explodeAssignments(pa: PartialAssignment): List[Assignment] = pa match
    case PartialAssignment(optVariables) =>
      optVariables match
        case ::(head, next) =>
          head match
            case OptionalVariable(name, None) =>
              if next.nonEmpty then
                val assignments = explodeAssignments(PartialAssignment(next))
                assignments.flatMap { case Assignment(v) =>
                  Assignment(Variable(name, true) +: v) :: Assignment(Variable(name, false) +: v) :: Nil
                }
              else Assignment(List(Variable(name, true))) :: Assignment(List(Variable(name, false))) :: Nil
            case v @ OptionalVariable(_, Some(_)) =>
              if next.nonEmpty then explodeAssignments(PartialAssignment(next))
                .map { case Assignment(variables) => Assignment(v.toVariable +: variables) }
              else List(Assignment(List(v.toVariable)))
        case Nil => Nil
