package model.dpll

import model.{PartialExpression, PartialVariable}
import model.dpll.DecisionTree.*

import scala.language.postfixOps
import scala.util.Random

object Dpll:

  private val rnd = Random(42)

  /**
   *
   * @param dec
   * @return
   */
  def dpll(dec: Decision): DecisionTree = dec match
    case Decision(parModel, parExp) =>
      val unContrVars = filterUnconstrVars(parModel)
      if (unContrVars.nonEmpty)
        //val i = rnd.between(0, unContrVars.size)
        //val constr = rnd.nextBoolean()
        Branch(dec, recStep(unContrVars.head, parModel, parExp, true),
          recStep(unContrVars.head, parModel, parExp, false))
      else Unsat

  private val recStep: (PartialVariable, PartialModel, PartialExpression, Boolean) => DecisionTree =
    (parVar, parModel, parExp, constr) =>
      dpll(
        Decision(updateParModel(parVar match
          case PartialVariable(name, _) => VarConstr(name, constr), parModel), parExp)
  )

  /**
   * 
   * @param parModel
   * @return
   */
  def filterUnconstrVars(parModel: PartialModel): PartialModel =
    parModel.filter { case PartialVariable(_, o) => o.isEmpty }

  /**
   * Update a PartialModel given as parameter constraining a variable (VariableConstraint)
   * @param varConstr
   * @param parModel
   * @return Updated PartialModel
   */
  def updateParModel(varConstr: VarConstr, parModel: PartialModel): PartialModel =
    parModel.map {
      case PartialVariable(name, _) if name == varConstr.varName =>
        PartialVariable(name, Option(varConstr.value))
      case v => v
    }