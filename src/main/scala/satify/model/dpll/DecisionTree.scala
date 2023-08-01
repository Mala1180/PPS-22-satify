package satify.model.dpll

import satify.model.Expression.*
import satify.model.{PartialExpression, PartialVariable, Variable}

type Model[T <: Variable] = Seq[T]
type PartialModel = Model[PartialVariable]

/** Decision is a node of DecisionTree.
  * @param partialModel The current state of the PartialModel with varName's PartialVariable constrained.
  * @param partialExpression Updated expression after the decision.
  */
case class Decision(partialModel: PartialModel, partialExpression: PartialExpression)

/** DecisionTree is the main data structure for the DPLL algorithm. */
enum DecisionTree:
  case Sat
  case Unsat
  case Branch(d: Decision, left: DecisionTree, right: DecisionTree)

/** A Constraint is a boolean assignment to a variable.
  * @param variable Name of the variable
  * @param value Boolean value
  */
case class Constraint(variable: String, value: Boolean)
