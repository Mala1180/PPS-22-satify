package satify.update.dpll

import satify.model.CNF.{And, Not, Or, Symbol}
import satify.model.Constant.{False, True}
import satify.model.{CNF, Constant, Constraint, Variable}

object ConflictIdentification:

  def isUnsat(cnf: CNF): Boolean =

    val f: (CNF, Boolean) => Boolean = (cnf, d) =>
      cnf match
        case Symbol(False) => true
        case _ => d

    cnf match
      case Not(Symbol(_: Variable)) | Symbol(_: Variable) |
           Not(Symbol(False)) | Symbol(True) | Or(_, _) => false
      case Not(Symbol(True)) | Symbol(False) => true
      case And(left, right) => f(left, f(right, isUnsat(left) | isUnsat(right)))