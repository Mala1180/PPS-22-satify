package satify.update.dpll

import satify.model.{CNF, Constraint, PartialModel, Variable}
import satify.model.CNF.*

object PartialModelUtils:

  /** Extract a PartialModel from an expression in CNF.
    *
    * @param cnf where to extract a Model
    * @return Correspondent model from CNF given as parameter.
    */
  def extractModelFromCnf(cnf: CNF): PartialModel =
    cnf match
      case Symbol(variable: Variable) => Seq(variable)
      case And(e1, e2) => extractModelFromCnf(e1) ++ extractModelFromCnf(e2)
      case Or(e1, e2) => extractModelFromCnf(e1) ++ extractModelFromCnf(e2)
      case Not(e) => extractModelFromCnf(e)
      case _ => Seq.empty

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
          rSols.map(p => Seq(Variable(name, Some(true))) ++ p) ++
            rSols.map(p => Seq(Variable(name, Some(false))) ++ p)
        else Set(Seq(Variable(name, Some(true))), Seq(Variable(name, Some(false))))
      case v @ Variable(_, _) =>
        if pm.tail.nonEmpty then explodeSolutions(pm.tail).map(p => Seq(v) ++ p)
        else Set(Seq(v))
