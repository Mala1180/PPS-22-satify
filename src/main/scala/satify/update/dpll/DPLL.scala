package satify.update.dpll

import satify.model.DecisionTree.*
import satify.model.{CNF, Constraint, DecisionTree, PartialModel, TreeState, Variable}
import satify.model.CNF.*
import satify.update.dpll.CNFSimplification.*
import satify.update.dpll.PartialModelUtils.*
import satify.update.dpll.ConflictIdentification.isUnsat
import satify.model
import satify.model.Constant.{False, True}

import scala.language.postfixOps

object DPLL:

  /** Exhaustive search of all possible decisions to the partial model.
    * The three branches left assigning true to the first unconstrained variable, false to the right one, until
    * no more assignments are possible.
    * @param dec decision to make
    * @return decision tree from the current decision
    */
  def dpll(dec: TreeState): DecisionTree =

    lazy val recCall: (PartialModel, CNF, Constraint) => DecisionTree =
      (parModel, cnf, constr) =>
        dpll(
          TreeState(updateParModel(parModel, constr), simplifyCnf(cnf, constr))
        )

    dec match
      case TreeState(parModel, cnf) =>
        if (isUnsat(cnf))
          Branch(dec, Leaf, Leaf)
        else
          filterUnconstrVars(extractModelFromCnf(cnf)) match
            case Variable(name, _) +: _ =>
              Branch(
                dec,
                recCall(parModel, cnf, Constraint(name, true)),
                recCall(parModel, cnf, Constraint(name, false))
              )
            case _ => Branch(dec, Leaf, Leaf)

  /** Get all SAT solutions, e.g. all parent nodes of DecisionTree leaves where the CNF
    * has been simplified to Symbol(True).
    * @param dt DecisionTree
    * @return a set of PartialModel(s).
    */
  def extractSolutionsFromDT(dt: DecisionTree): Set[PartialModel] =
    dt match
      case Branch(TreeState(pm, cnf), Leaf, Leaf) =>
        cnf match
          case Symbol(True) => Set(pm)
          case _ => Set.empty
      case Branch(_, left, right) => extractSolutionsFromDT(left) ++ extractSolutionsFromDT(right)
      case Leaf => Set.empty

  /** Cartesian product of all possible Variable assignments to a PartialModel.
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
