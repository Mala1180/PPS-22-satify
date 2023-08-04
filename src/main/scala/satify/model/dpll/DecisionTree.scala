package satify.model.dpll

import satify.model.CNF.*
import satify.model.{CNF, Variable}

type Model[T <: Variable] = Seq[T]
type PartialModel = Model[Variable]

/** Decision is a node of DecisionTree.
  * @param partialModel The current state of the PartialModel with varName's PartialVariable constrained.
  * @param Expression Updated expression after the decision.
  */
case class Decision(partialModel: PartialModel, Expression: CNF)

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
