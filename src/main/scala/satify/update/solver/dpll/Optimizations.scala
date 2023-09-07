package satify.update.solver.dpll

import satify.model.{CNF, Variable}
import satify.model.CNF.*
import satify.model.dpll.Constraint

object Optimizations:

  def unitPropagation(cnf: CNF): Option[Constraint] =
    val f: (CNF, Option[Constraint]) => Option[Constraint] = (cnf, d) =>
      cnf match
        case Symbol(Variable(name, _)) => Some(Constraint(name, true))
        case Not(Symbol(Variable(name, _))) => Some(Constraint(name, false))
        case _ => d

    f(
      cnf,
      cnf match
        case And(left, right) => f(left, f(right, unitPropagation(right)))
        case Or(_, _) => None
        case _ => None
    )

  /*@tailrec
   def pureLiteralElimination(dec: Decision): Option[Constraint] =

     def find(value: String, cnf: CNF): Option[Constraint] =
       val f: (String, CNF, CNF) => Option[Constraint] = (name, left, right) =>
         val leftRec = find(name, left)
         if leftRec == find(name, right) then leftRec else None
       cnf match
         case Symbol(Variable(name, _)) if value == name => Some(Constraint(name, true))
         case Not(Symbol(Variable(name, _))) if value == name => Some(Constraint(name, false))
         case And(left, right) => f(value, left, right)
         case Or(left, right) => f(value, left, right)
         case _ => None

     dec match
       case Decision(parModel, cnf) =>
         parModel match
           case Seq() => None
           case Variable(name, None) +: tail =>
             val fVar = find(name, cnf)
             if fVar.isEmpty then pureLiteralElimination(Decision(tail, cnf))
             else fVar
           case Variable(_, _) +: tail => pureLiteralElimination(Decision(tail, cnf))
   */
