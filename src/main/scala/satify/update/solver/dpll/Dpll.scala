package satify.update.solver.dpll

import satify.model.Bool.{False, True}
import satify.model.CNF.{And, Not, Or, Symbol}
import satify.model.{CNF, Variable}
import satify.model.dpll.{Constraint, Decision, DecisionTree, PartialModel}
import satify.model.dpll.DecisionTree.{Branch, Leaf}
import satify.update.solver.dpll.CNFSimplification.simplifyCnf
import satify.update.solver.dpll.ConflictIdentification.isUnsat
import satify.update.solver.dpll.PartialModelUtils.*

import java.util.concurrent.Executors
import scala.annotation.tailrec
import scala.util.Random

object Dpll:

  private val rnd = Random(64)

  def randomDecision(d: Decision): List[Decision] = d match
    case Decision(pm, cnf) =>
      val fv = filterUnconstrVars(pm)
      if fv.nonEmpty then
        val v = rnd.nextBoolean()
        fv(rnd.between(0, fv.size)) match
          case Variable(n, _) =>
            println(s"randomly choosing $n with value $v")
            List(
              Decision(updateParModel(pm, Constraint(n, v)), simplifyCnf(cnf, Constraint(n, v))),
              Decision(updateParModel(pm, Constraint(n, !v)), simplifyCnf(cnf, Constraint(n, !v)))
            )
      else Nil

  def unitPropDecision(d: Decision, c: Constraint): List[Decision] = d match
    case Decision(pm, cnf) =>
      List(
        Decision(updateParModel(pm, c), simplifyCnf(cnf, c)),
        Decision(updateParModel(pm, Constraint(c.name, !c.value)), Symbol(False))
      )

  def pureLitEliminationDecision(d: Decision, c: Constraint): List[Decision] = d match
    case Decision(pm, cnf) =>
      List(
        Decision(updateParModel(pm, c), simplifyCnf(cnf, c)),
        Decision(updateParModel(pm, Constraint(c.name, !c.value)), simplifyCnf(cnf, Constraint(c.name, !c.value)))
      )

  def dpll(dec: Decision): DecisionTree =

    case class Frame(d: Decision, done: List[DecisionTree], todos: List[Decision])

    def step(stack: List[Frame]): DecisionTree = stack match
      case Frame(d, done, Nil) :: tail =>
        val ret =
          done match
            case ::(head, next) => Branch(d, next.head, head)
            case Nil => Leaf(d)
        tail match
          case Nil => ret
          case Frame(tn, td, tt) :: more =>
            step(Frame(tn, ret :: td, tt) :: more)

      case Frame(d, done, x :: xs) :: tail =>
        if isUnsat(d.cnf) || d.cnf == Symbol(True) then step(Frame(d, Nil, Nil) :: tail)
        else step(Frame(x, Nil, decide(x)) :: Frame(d, done, xs) :: tail)
      case Nil => throw new Error("Shouldn't happen")

    def decide(d: Decision): List[Decision] = d match
      case Decision(_, cnf) =>
        unitPropagation(cnf) match
          case Some(c) =>
            println(s"unit prop choosing variable $c")
            unitPropDecision(d, c)
          case _ => randomDecision(d)
        /*case _ => pureLiteralElimination(d) match
            case Some(c) =>
              println(s"pure lit el variable $c")
              pureLitEliminationDecision(d, c)
            case _ => randomDecision(d)
         */

    step(List(Frame(dec, Nil, decide(dec))))

  def extractSolutions(cnf: CNF): Set[PartialModel] =
    val s =
      for pmSet <- extractSolutionsFromDT(dpll(Decision(extractModelFromCnf(cnf), cnf)))
      yield explodeSolutions(pmSet)
    s.flatten

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
