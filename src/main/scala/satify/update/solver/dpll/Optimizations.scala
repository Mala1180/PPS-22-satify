package satify.update.solver.dpll

import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.model.solver.{Constraint, Decision, OptionalVariable, PartialAssignment}

import scala.annotation.tailrec
import scala.collection.immutable.{AbstractSeq, LinearSeq}

private[dpll] object Optimizations:

  import LitType.*

  /** Enum which expresses the literal type.
    * A literal is Pure if it appears only in positive form inside an expression,
    * impure otherwise.
    */
  private enum LitType:
    case Pure(c: Constraint)
    case Impure

  /** Identify a unit literal.
    * @param cnf expression in Conjunctive Normal Form.
    * @return filled Option with the constraint to be applied
    *         to a unit literal if one has been found, empty otherwise.
    */
  def unitLiteralIdentification(cnf: CNF): Option[Constraint] =

    @tailrec
    def loop(cnfList: List[CNF], result: Option[Constraint] = None): Option[Constraint] =
      cnfList match
        case Nil => result
        case And(left, right) :: tail => loop(left :: right :: tail, None)
        case head :: tail =>
          head match
            case Symbol(name: String) => Some(Constraint(name, true))
            case Not(Symbol(name: String)) => Some(Constraint(name, false))
            case _ => loop(tail, None)

    loop(cnf :: Nil)

  /** Identify a pure literal.
    * @param dec previous decision
    * @return filled Option with the constraint to be applied
    *         to a pure literal if one has been found, empty otherwise.
    */
  @tailrec
  def pureLiteralIdentification(dec: Decision): Option[Constraint] =

    /** Get the literal type, if it is present inside the given CNF.
      * @param name of the symbol.
      * @param cnfList where to search.
      * @return a filled Option with the literal type if it is present, an empty one otherwise.
      */
    @tailrec
    def getLiteralType(name: String, cnfList: List[CNF],
                       result: Option[LitType] = None): Option[LitType] =
      cnfList match
        case ::(head, tail) =>
          head match
            case Symbol(n: String) if name == n =>
              result match
                case Some(Pure(Constraint(_, false))) => Some(Impure)
                case _ => getLiteralType(n, tail, Some(Pure(Constraint(n, true))))
            case Not(Symbol(n: String)) if name == n =>
              result match
                case Some(Pure(Constraint(_, true))) => Some(Impure)
                case _ => getLiteralType(n, tail, Some(Pure(Constraint(n, false))))
            case And(left, right) => getLiteralType(name, left :: right :: tail, result)
            case Or(left, right) => getLiteralType(name, left :: right :: tail, result)
            case _ => getLiteralType(name, tail, result)
        case Nil => result

    dec match
      case Decision(PartialAssignment(optVariables), cnf) =>
        optVariables match
          case ::(head, tail) =>
            head match
              case OptionalVariable(name, None) =>
                getLiteralType(name, cnf :: Nil) match
                  case Some(Pure(c)) => Some(c)
                  case _ => pureLiteralIdentification(Decision(PartialAssignment(tail), cnf))
              case OptionalVariable(_, _) =>
                pureLiteralIdentification(Decision(PartialAssignment(tail), cnf))
          case Nil => None
