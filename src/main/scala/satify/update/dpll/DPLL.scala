package satify.update.dpll

import satify.model.DecisionTree.*
import satify.model.{CNF, Constraint, Decision, DecisionTree, PartialModel, Variable}
import satify.model.CNF.*
import satify.update.dpll.CNFSimplification.*
import satify.update.dpll.PartialModelUtils.*
import satify.update.dpll.ConflictIdentification.isUnsat
import satify.model
import satify.model.Bool.{False, True}

import scala.language.postfixOps
import scala.util.Random

object DPLL:

  private val rnd = Random(42)

  /** Exhaustive search of all possible decisions to the partial model.
    * The three branches left assigning true to the first unconstrained variable, false to the right one, until
    * no more assignments are possible.
    * @param dec decision to make
    * @return decision tree from the current decision
    */
  def dpll(dec: Decision): DecisionTree =

    def decide(parModel: PartialModel, cnf: CNF, constr: Constraint): DecisionTree =
      dpll(Decision(updateParModel(parModel, constr), simplifyCnf(cnf, constr)))

    def unitPropBranch(dec: Decision): Option[Branch] = dec match
      case Decision(parModel, cnf) =>
        unitPropagation(cnf) match
          case Some(c @ Constraint(name, value)) =>
            Some(
              Branch(
                dec,
                decide(parModel, cnf, c),
                Leaf(Decision(updateParModel(parModel, Constraint(name, !value)), Symbol(False)))
              )
            )
          case None => None

    def randomBranch(dec: Decision): DecisionTree = dec match
      case Decision(parModel, cnf) =>
        val unVars = filterUnconstrVars(extractModelFromCnf(cnf))
        if unVars.nonEmpty then
          val v = rnd.nextBoolean()
          unVars(rnd.between(0, unVars.size)) match
            case Variable(name, _) =>
              Branch(
                dec,
                decide(parModel, cnf, Constraint(name, v)),
                decide(parModel, cnf, Constraint(name, !v))
              )
        else Leaf(dec)

    dec match
      case Decision(parModel, cnf) =>
        if isUnsat(cnf) then Leaf(dec)
        else
          unitPropBranch(dec) match
            case Some(branch) => branch
            case None => randomBranch(dec)

  /** Get all SAT solutions, e.g. all Leaf nodes where the CNF has been simplified to Symbol(True).
    * @param dt DecisionTree
    * @return a set of PartialModel(s).
    */
  def extractSolutionsFromDT(dt: DecisionTree): Set[PartialModel] =
    dt match
      case Leaf(Decision(pm, cnf)) =>
        cnf match
          case Symbol(True) => Set(pm)
          case _ => Set.empty
      case Branch(_, left, right) => extractSolutionsFromDT(left) ++ extractSolutionsFromDT(right)

  private def unitPropagation(cnf: CNF): Option[Constraint] =

    val f: (CNF, Option[Constraint]) => Option[Constraint] = (cnf, d) =>
      cnf match
        case Symbol(Variable(name, _)) => Some(Constraint(name, true))
        case Not(Symbol(Variable(name, _))) => Some(Constraint(name, false))
        case _ => d

    f(
      cnf,
      cnf match
        case And(left, right) => f(left, f(right, unitPropagation(right)))
        case Or(left, right) => None
        case _ => None
    )
