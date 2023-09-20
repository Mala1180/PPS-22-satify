package satify.update.solver.dpll

import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.model.dpll.{Constraint, Decision, OptionalVariable, PartialAssignment}

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

    val f: (CNF, Option[Constraint]) => Option[Constraint] =
      (cnf, d) =>
        cnf match
          case Symbol(name: String) => Some(Constraint(name, true))
          case Not(Symbol(name: String)) => Some(Constraint(name, false))
          case _ => d

    f(
      cnf,
      cnf match
        case And(left, right) => f(left, f(right, unitLiteralIdentification(right)))
        case Or(_, _) => None
        case _ => None
    )

  /** Identify a pure literal.
    * @param dec previous decision
    * @return filled Option with the constraint to be applied
    *         to a pure literal if one has been found, empty otherwise.
    */
  @tailrec
  def pureLiteralIdentification(dec: Decision): Option[Constraint] =

    /** Get the literal type, if it is present inside the given CNF.
      * @param name of the symbol
      * @param cnf where to search
      * @return a filled Option with the literal type if it is present, an empty one otherwise.
      */
    def getLiteralType(name: String, cnf: CNF): Option[LitType] =

      val recursiveStep: (String, CNF, CNF) => Option[LitType] = (n, left, right) =>
        getLiteralType(n, left) match
          case Some(Pure(cLeft)) =>
            getLiteralType(n, right) match
              case Some(Pure(cRight)) if cLeft == cRight => Some(Pure(cLeft))
              case None => Some(Pure(cLeft))
              case _ => Some(Impure)
          case None => getLiteralType(n, right)
          case impure @ _ => impure

      cnf match
        case Symbol(n: String) if name == n => Some(Pure(Constraint(n, true)))
        case Not(Symbol(n: String)) if name == n => Some(Pure(Constraint(n, false)))
        case And(left, right) => recursiveStep(name, left, right)
        case Or(left, right) => recursiveStep(name, left, right)
        case _ => None

    dec match
      case Decision(PartialAssignment(optVariables), cnf) =>
        optVariables match
          case ::(head, next) =>
            head match
              case OptionalVariable(name, None) =>
                getLiteralType(name, cnf) match
                  case Some(Pure(c)) => Some(c)
                  case _ => pureLiteralIdentification(Decision(PartialAssignment(next), cnf))
              case OptionalVariable(_, _) => pureLiteralIdentification(Decision(PartialAssignment(next), cnf))
          case Nil => None
