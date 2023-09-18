package satify.update.solver.dpll

import satify.model.cnf.CNF
import satify.model.cnf.CNF.*
import satify.model.dpll.{Constraint, Decision, OptionalVariable, PartialAssignment}

import scala.annotation.tailrec
import scala.collection.immutable.{AbstractSeq, LinearSeq}

object Optimizations:

  import PureLitSearch.*

  /** Enum to search pure literals inside a CNF expression. */
  private enum PureLitSearch:
    case Concordant(c: Constraint)
    case Discordant
    case Missing

  /** Identify unit literals.
    * @param cnf expression in Conjunctive Normal Form.
    * @return eventual Constraint to be applied to a unit literal.
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

  /** Identify pure literals
    * @param dec previous decision
    * @return eventual Constraint to be applied to a pure literal.
    */
  @tailrec
  def pureLiteralIdentification(dec: Decision): Option[Constraint] =
    def find(name: String, cnf: CNF): PureLitSearch =
      val f: (String, CNF, CNF) => PureLitSearch = (n, left, right) =>
        find(n, left) match
          case Concordant(cLeft) =>
            find(n, right) match
              case Concordant(cRight) if cLeft == cRight => Concordant(cLeft)
              case Missing => Concordant(cLeft)
              case _ => Discordant
          case Missing => find(n, right)
          case Discordant => Discordant

      cnf match
        case Symbol(n: String) if name == n => Concordant(Constraint(n, true))
        case Not(Symbol(n: String)) if name == n => Concordant(Constraint(n, false))
        case And(left, right) => f(name, left, right)
        case Or(left, right) => f(name, left, right)
        case _ => Missing

    dec match
      case Decision(PartialAssignment(optVariables), cnf) =>
        optVariables match
          case ::(head, next) =>
            head match
              case OptionalVariable(name, None) =>
                find(name, cnf) match
                  case Concordant(c) => Some(c)
                  case _ => pureLiteralIdentification(Decision(PartialAssignment(next), cnf))
              case OptionalVariable(_, _) => pureLiteralIdentification(Decision(PartialAssignment(next), cnf))
          case Nil => None
