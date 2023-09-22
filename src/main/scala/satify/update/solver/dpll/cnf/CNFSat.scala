package satify.update.solver.dpll.cnf

import satify.model.cnf.Bool.{False, True}
import satify.model.cnf.CNF.{And, Not, Or, Symbol}
import satify.model.cnf.{Bool, CNF}

import scala.annotation.tailrec

private[dpll] object CNFSat:

  /** Check if the given CNF is UNSAT.
    * @param cnf expression in Conjunctive Normal Form.
    * @return true if cnf is UNSAT false otherwise. Note that !UNSAT doesn't imply SAT.
    */
  def isUnsat(cnf: CNF): Boolean =

    @tailrec
    def loop(cnfList: List[CNF], result: Boolean = false): Boolean =
      cnfList match
        case Nil => result
        case And(left, right) :: tail => loop(left :: right :: tail)
        case head :: tail =>
          head match
            case Not(Symbol(True)) | Symbol(False) => true
            case _ => loop(tail)
    
    loop(cnf :: Nil)

  /** Check if the given CNF is SAT.
    * @param cnf expression in Conjunctive Normal Form.
    * @return true if cnf is SAT false otherwise.
    */
  def isSat(cnf: CNF): Boolean = cnf match
    case Symbol(True) => true
    case _ => false
