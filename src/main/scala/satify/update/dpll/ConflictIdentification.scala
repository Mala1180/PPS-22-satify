package satify.update.dpll

import satify.model.CNF.{And, Not, Or, Symbol}
import satify.model.Constant.{False, True}
import satify.model.{CNF, Constant, Constraint, Variable}

object ConflictIdentification:

  def hasConflict(cnf: CNF): Boolean =

    val f: (CNF, Boolean) => Boolean = (cnf, d) =>
      cnf match
        case Symbol(False) => true
        case _ => d

    cnf match
      case Symbol(_: Variable) | Symbol(_: Constant) | Or(_, _) | Not(_) => false
      case And(left, right) => f(left, f(right, hasConflict(left) | hasConflict(right)))