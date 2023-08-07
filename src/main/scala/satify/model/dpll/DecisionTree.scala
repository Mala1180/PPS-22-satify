package satify.model

import satify.model.{CNF, Variable}

type PartialModel = Seq[Variable]

/** Decision is a node of DecisionTree.
  * @param parModel The current state of the PartialModel with varName's PartialVariable constrained.
  * @param cnf Updated Cnf after the decision.
  */
case class TreeState(parModel: PartialModel, cnf: CNF)

/** DecisionTree is the main data structure for the DPLL algorithm. */
enum DecisionTree:
  case Sat
  case Unsat
  case Branch(d: TreeState, left: DecisionTree, right: DecisionTree)

/** A Constraint is a boolean assignment to a variable.
  * @param name Name of the variable
  * @param value Boolean value
  */
case class Constraint(name: String, value: Boolean)
