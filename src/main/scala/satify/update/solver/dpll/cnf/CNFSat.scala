package satify.update.solver.dpll.cnf

import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.cnf.{Bool, CNF}

import scala.annotation.tailrec

object CNFSat:
  
  
  /** Check if CNF is UNSAT.
    * @param cnf to be checked.
    * @return true if cnf is UNSAT false otherwise. Note that !UNSAT doesn't imply SAT.
    */
  def isUnsat(cnf: CNF): Boolean =
  
    @tailrec
    def loop(todo: List[CNF], done: Boolean = false): Boolean =
      todo match
        case Nil => false
        case And(left, right) :: tail => loop(left :: right :: tail, false)
        case head :: tail =>
          head match
            case Not(Symbol(_: String)) | Symbol(_: String) | Not(Symbol(False)) | Symbol(True) | Or(_, _) =>
              loop(tail, false)
            case Not(Symbol(True)) | Symbol(False) => true
            case _ => false

    loop(cnf :: Nil)

  def isSat(cnf: CNF): Boolean = cnf match
    case Symbol(True) => true
    case _ => false


