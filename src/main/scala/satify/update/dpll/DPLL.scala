package satify.update.dpll

import satify.model.DecisionTree.*
import satify.model.{CNF, Constraint, TreeState, DecisionTree, PartialModel, Variable}
import satify.model.CNF.*
import satify.update.dpll.CNFSimplification.*
import satify.update.dpll.PartialModelUtils.*
import satify.model

import scala.language.postfixOps

object DPLL:

  /** Exhaustive search of all possible decisions to the partial model.
   * The three branches left assigning true to the first unconstrained variable, false to the right one, until
   * no more assignments are possible.
   * @param dec decision to make
   * @return decision tree from the current decision
   */
  def dpll(dec: TreeState): DecisionTree = dec match
    case TreeState(parModel, cnf) =>
      val unContrVars = filterUnconstrVars(parModel)
      if (unContrVars.nonEmpty)
        Branch(dec, recStep(unContrVars.head, parModel, cnf, true), recStep(unContrVars.head, parModel, cnf, false))
      else Branch(dec, Leaf, Leaf)

  /** Recursive search step.
   * @param v partial variable
   * @param parModel partial model
   * @param cnf CNF expression
   * @param b boolean assignment
   * @return DecisionTree from dpll
   */
  private def recStep(v: Variable, parModel: PartialModel, cnf: CNF, b: Boolean): DecisionTree =
    dpll(
      TreeState(updateParModel(parModel, Constraint(v.name, b)), simplifyCnf(cnf, Constraint(v.name, b)))
    )
