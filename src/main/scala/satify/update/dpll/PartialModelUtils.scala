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
