package satify.update.solver.dpll.utils

import satify.model.cnf.Bool.True
import satify.model.cnf.CNF.*
import satify.model.cnf.CNF
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.model.dpll.OrderedSeq.*
import satify.model.dpll.{Constraint, Decision, DecisionTree, PartialModel, Variable}

object PartialModelUtils:

  import satify.model.dpll.OrderedSeq.given_Ordering_Variable

  /** Extract a PartialModel from an expression in CNF.
    *
    * @param cnf where to extract a Model
    * @return Correspondent model from CNF given as parameter.
    */
  def extractModelFromCnf(cnf: CNF): PartialModel =
    cnf match
      case Symbol(name: String) => seq(Variable(name))
      case And(e1, e2) => seq(extractModelFromCnf(e1) ++ extractModelFromCnf(e2): _*)
      case Or(e1, e2) => seq(extractModelFromCnf(e1) ++ extractModelFromCnf(e2): _*)
      case Not(e) => extractModelFromCnf(e)
      case _ => seq()

  /** Filters unconstrained variables from the partial model
    *
    * @param parModel partial model
    * @return filtered partial model
    */
  def filterUnconstrVars(parModel: PartialModel): PartialModel =
    parModel.filter { case Variable(_, o) => o.isEmpty }

  /** Update a PartialModel given as parameter constraining a variable (VariableConstraint)
    *
    * @param parModel  partial model
    * @param varConstr variable constraint
    * @return Updated PartialModel
    */
  def updateParModel(parModel: PartialModel, varConstr: Constraint): PartialModel =
    parModel.map {
      case Variable(name, _) if name == varConstr.name =>
        Variable(name, Some(varConstr.value))
      case v => v
    }

  /** Get all SAT solutions, e.g. all Leaf nodes where the CNF has been simplified to Symbol(True).
    *
    * @param dt DecisionTree
    * @return a set of PartialModel(s).
    */
  def extractSolutionsFromDT(dt: DecisionTree): Set[PartialModel] =
    dt match
      case Leaf(Decision(pm, cnf)) =>
        cnf match
          case Symbol(True) =>
            Set(
              pm.filter(v =>
                v match
                  case Variable(name, _) if name.startsWith("TSTN") || name.startsWith("ENC") => false
                  case _ => true
              )
            )
          case _ => Set.empty
      case Branch(_, left, right) => extractSolutionsFromDT(left) ++ extractSolutionsFromDT(right)

  /** Cartesian product of all possible Variable assignments to a PartialModel.
    *
    * @param pm PartialModel
    * @return Cartesian product of pm
    */
  def explodeSolutions(pm: PartialModel): Set[PartialModel] =
    pm.head match
      case Variable(name, None) =>
        if pm.tail.nonEmpty then
          val rSols = explodeSolutions(pm.tail)
          rSols.map(p => seq(Variable(name, Some(true))) ++ p) ++
            rSols.map(p => seq(Variable(name, Some(false))) ++ p)
        else Set(seq(Variable(name, Some(true))), seq(Variable(name, Some(false))))
      case v @ Variable(_, _) =>
        if pm.tail.nonEmpty then explodeSolutions(pm.tail).map(p => seq(v) ++ p)
        else Set(seq(v))
