package satify.model.dpll

import satify.model.CNF
import satify.model.dpll.PartialModel

/** Decision is a node of DecisionTree.
  * @param pm The current state of the PartialModel with varName's PartialVariable constrained.
  * @param cnf Updated Cnf after the decision.
  */
case class Decision(pm: PartialModel, cnf: CNF)

/** DecisionTree is the main data structure for the DPLL algorithm. */
enum DecisionTree:
  case Leaf(d: Decision)
  case Branch(d: Decision, left: DecisionTree, right: DecisionTree)

/** A Constraint is a boolean assignment to a variable.
  * @param name Name of the variable
  * @param value Boolean value
  */
case class Constraint(name: String, value: Boolean)
