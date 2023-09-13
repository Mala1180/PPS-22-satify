package satify.model.dpll

import satify.model.cnf.CNF
import satify.model.dpll.PartialAssignment

/** Decision is a node of [[DecisionTree]].
  * The decision represents a boolean [[Constraint]] to a [[OptionalVariable]] and it is implicitly
  * defined inside [[pa]].
  * @param pa partial assignment.
  * @param cnf is the respective simplified CNF expression after the constraint is applied.
  */
case class Decision(pa: PartialAssignment, cnf: CNF)

/** Decision tree is the main data structure for the DPLL algorithm. */
enum DecisionTree:
  case Leaf(d: Decision)
  case Branch(d: Decision, left: DecisionTree, right: DecisionTree)

/** A Constraint is a boolean setting to a variable.
  * @param name is the name of the variable
  * @param value boolean value
  */
case class Constraint(name: String, value: Boolean)
