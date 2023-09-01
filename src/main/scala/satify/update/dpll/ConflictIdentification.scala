package satify.update.dpll

import satify.model.tree.cnf.Bool.{False, True}
import satify.model.tree.cnf.{And, Not, Or, Symbol}
import satify.model.dpll.Constraint
import satify.model.tree.cnf.{Bool, CNF, Variable}

object ConflictIdentification:

  /** Check if CNF is UNSAT.
    * @param cnf to be checked.
    * @return true if cnf is UNSAT false otherwise. Note that !UNSAT doesn't imply SAT.
    */
  def isUnsat(cnf: CNF): Boolean =

    /** Returns true if CNF is a Symbol(False), d otherwise. */
    val f: (CNF, Boolean) => Boolean = (cnf, d) =>
      cnf match
        case Symbol(False) => true
        case _ => d

    cnf match
      case Not(Symbol(_: Variable)) | Symbol(_: Variable) | Not(Symbol(False)) | Symbol(True) | Or(_, _) => false
      case Not(Symbol(True)) | Symbol(False) => true
      case And(left, right) => f(left, f(right, isUnsat(left) | isUnsat(right)))
