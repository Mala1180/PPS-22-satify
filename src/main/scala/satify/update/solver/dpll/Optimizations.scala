package satify.update.solver.dpll

import satify.model.{CNF, Variable}
import satify.model.CNF.*
import satify.model.dpll.{Constraint, Decision}

import scala.annotation.tailrec
import scala.collection.immutable.{AbstractSeq, LinearSeq}

object Optimizations:

  import LitSearch.*
  enum LitSearch:
    case Concordant(c: Constraint)
    case Discordant
    case Missing

  def uProp(cnf: CNF): Option[Constraint] =

    val f: (CNF, Option[Constraint]) => Option[Constraint] =
      (cnf, d) =>
        cnf match
          case Symbol(Variable(name, _)) => Some(Constraint(name, true))
          case Not(Symbol(Variable(name, _))) => Some(Constraint(name, false))
          case _ => d

    f(
      cnf,
      cnf match
        case And(left, right) => f(left, f(right, uProp(right)))
        case Or(_, _) => None
        case _ => None
    )

  @tailrec
  def pureLit(dec: Decision): Option[Constraint] =

    def find(name: String, cnf: CNF): LitSearch =

      val f: (String, CNF, CNF) => LitSearch = (n, left, right) =>
        find(n, left) match
          case Concordant(cLeft) =>
            find(n, right) match
              case Concordant(cRight) if cLeft == cRight => Concordant(cLeft)
              case Missing => Concordant(cLeft)
              case _ => Discordant
          case Missing => find(n, right)
          case Discordant => Discordant

      cnf match
        case Symbol(Variable(n, _)) if name == n => Concordant(Constraint(n, true))
        case Not(Symbol(Variable(n, _))) if name == n => Concordant(Constraint(n, false))
        case And(left, right) => f(name, left, right)
        case Or(left, right) => f(name, left, right)
        case _ => Missing

    dec match
      case Decision(pm, cnf) =>
        pm match
          case Seq() => None
          case Variable(name, None) +: tail =>
            find(name, cnf) match
              case Concordant(c) => Some(c)
              case _ => pureLit(Decision(tail, cnf))
          case Variable(_, _) +: tail => pureLit(Decision(tail, cnf))
