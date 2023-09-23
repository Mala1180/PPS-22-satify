package satify.update.solver.dpll.utils

import satify.model.cnf.Bool.True
import satify.model.cnf.CNF
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.solver.DecisionTree.{Branch, Leaf}
import satify.model.solver.OrderedList.list
import satify.model.solver.{Constraint, Decision, DecisionTree, OptionalVariable, PartialAssignment}
import satify.model.expression.SymbolGeneration.{converterVarPrefix, encodingVarPrefix}
object PartialAssignmentUtils:

  import satify.model.solver.OrderedList.given

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
                    case OptionalVariable(name, _)
                        if name.startsWith(converterVarPrefix) || name.startsWith(encodingVarPrefix) =>
                      false
                    case _ => true
                )
              )
            )
          case _ => Nil
      case Branch(_, left, right) => extractParAssignments(left) ++ extractParAssignments(right)

  /** Extract a partial assignment from an expression in CNF.
    * @param cnf where to extract a Model
    * @return correspondent partial assignment from CNF given as parameter.
    */
  def extractParAssignmentFromCnf(cnf: CNF): PartialAssignment =

    def extractOptVars(cnf: CNF): List[OptionalVariable] = cnf match
      case Symbol(name: String) => List(OptionalVariable(name))
      case And(e1, e2) => extractOptVars(e1) ++ extractOptVars(e2)
      case Or(e1, e2) => extractOptVars(e1) ++ extractOptVars(e2)
      case Not(e) => extractOptVars(e)
      case _ => Nil

    PartialAssignment(list(extractOptVars(cnf): _*))

  /** Filters unconstrained variables from the partial model
    * @param partialAssignment partial model
    * @return filtered partial model
    */
  def filterUnconstrVars(partialAssignment: PartialAssignment): List[OptionalVariable] =
    partialAssignment match
      case PartialAssignment(optVariables) =>
        optVariables.filter { case OptionalVariable(_, o) => o.isEmpty }

  /** Update a partial assignment given a constraint as parameter
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
