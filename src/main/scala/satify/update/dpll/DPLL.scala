package satify.update.dpll

import satify.model.DecisionTree.*
import satify.model.{CNF, Constraint, TreeState, DecisionTree, PartialModel, Variable}
import satify.model.CNF.*
import satify.update.dpll.CNFSimplification.*
import satify.model

import scala.language.postfixOps

object DPLL:
  
  /**
   * Exhaustive search of all possible decisions to the partial model.
   * The three branches left assigning true to the first unconstrained variable, false to the right one, until
   * no more assignments are possible.
   * @param dec decision to make
   * @return decision tree from the current decision
   */
  def dpll(dec: TreeState): DecisionTree = dec match
    case TreeState(parModel, cnf) =>
      val unContrVars = filterUnconstrVars(parModel)
      if (unContrVars.nonEmpty)
        Branch(dec, recStep(unContrVars.head, parModel, cnf, true),
          recStep(unContrVars.head, parModel, cnf, false))
      else Branch(dec, Leaf, Leaf)

  /**
   * Recursive search step.
   * @param v partial variable
   * @param parModel partial model
   * @param cnf CNF expression
   * @param b boolean assignment
   * @return DecisionTree from dpll
   */
  private def recStep(v: Variable, parModel: PartialModel,
                      cnf: CNF, b: Boolean): DecisionTree =

    val nModel = updateParModel(Constraint(v.name, b), parModel)
    println(nModel)
    dpll(
      TreeState(nModel,
        simplifyCnf(cnf, Constraint(v.name, b))
      )
  )

  /**
   * Extract a PartialModel from an expression in CNF.
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

  /**
   * Filters unconstrained variables from the partial model
   * @param parModel partial model
   * @return filtered partial model
   */
  private def filterUnconstrVars(parModel: PartialModel): PartialModel =
    parModel.filter { case Variable(_, o) => o.isEmpty }

  /**
   * Update a PartialModel given as parameter constraining a variable (VariableConstraint)
   * @param varConstr variable constraint
   * @param parModel partial model
   * @return Updated PartialModel
   */
  private def updateParModel(varConstr: Constraint, parModel: PartialModel): PartialModel =
    parModel.map {
      case Variable(name, _) if name == varConstr.name =>
        Variable(name, Some(varConstr.value))
      case v => v
    }
