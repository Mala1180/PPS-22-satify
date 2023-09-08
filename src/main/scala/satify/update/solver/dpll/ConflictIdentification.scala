package satify.update.solver.dpll

import satify.model.Bool.{False, True}
import satify.model.CNF.{And, Not, Or, Symbol}
import satify.model.{Bool, CNF, Variable}

private object ConflictIdentification:

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
      case And(left, right) => f(left, f(right,
        if isUnsat(left) then true
        else if isUnsat(right) then true
        else false
      ))
      
  def isSat(cnf: CNF): Boolean = cnf match
    case Symbol(True) => true
    case _ => false
