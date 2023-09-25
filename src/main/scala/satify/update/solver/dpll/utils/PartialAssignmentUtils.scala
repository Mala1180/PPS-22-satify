package satify.update.solver.dpll.utils

import satify.model.cnf.Bool.True
import satify.model.cnf.CNF
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.expression.SymbolGeneration.{converterVarPrefix, encodingVarPrefix}
import satify.model.solver.*
import satify.model.solver.DecisionTree.{Branch, Leaf}

import scala.annotation.tailrec

object PartialAssignmentUtils:

  import satify.model.solver.OrderedList.given

  /** Get all SAT solutions, e.g. all Leaf nodes where the CNF has been simplified to Symbol(True).
    * @param dt DecisionTree
    * @return a set of PartialModel(s).
    */
  def extractParAssignments(dt: DecisionTree): List[PartialAssignment] =

    @tailrec
    def extract(todo: List[DecisionTree], done: List[PartialAssignment] = Nil): List[PartialAssignment] =
      todo match
        case ::(head, next) =>
          head match
            case Leaf(Decision(PartialAssignment(optVars), cnf)) =>
              cnf match
                case Symbol(True) =>
                  extract(
                    next,
                    done :+
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
                case _ => extract(next, done)
            case Branch(_, left, right) => extract(next ++ (left :: right :: Nil), done)
        case Nil => done

    extract(dt :: Nil).distinct

  /** Extract a partial assignment from an expression in CNF.
    * @param cnf where to extract a Model
    * @return correspondent partial assignment from CNF given as parameter.
    */
  def extractParAssignmentFromCnf(cnf: CNF): PartialAssignment =

    import satify.model.solver.OrderedList.{given_Ordering_OptionalVariable, list}

    @tailrec
    def extractOptVars(todo: List[CNF], done: List[OptionalVariable] = Nil): List[OptionalVariable] = todo match
      case ::(head, next) =>
        head match
          case Symbol(name: String) => extractOptVars(next, OptionalVariable(name) +: done)
          case And(e1, e2) => extractOptVars(next ++ (e1 :: e2 :: Nil), done)
          case Or(e1, e2) => extractOptVars(next ++ (e1 :: e2 :: Nil), done)
          case Not(e) => extractOptVars(next :+ e, done)
          case _ => done
      case Nil => done

    PartialAssignment(list(extractOptVars(cnf :: Nil): _*))

  /** Filters unconstrained variables from the partial model
    * @param partialAssignment partial model
    * @return filtered partial model
    */
  def filterUnconstrVars(partialAssignment: PartialAssignment): List[OptionalVariable] =
    partialAssignment match
      case PartialAssignment(optVariables) =>
        optVariables.filter { case OptionalVariable(_, o) => o.isEmpty }

  /** Update a partial assignment given a constraint as parameter
    * @param partialAssignment partial model
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
