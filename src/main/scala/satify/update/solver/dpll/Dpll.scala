package satify.update.solver.dpll

import satify.model.Bool.False
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

  private val rnd = Random(42)

  def decide(d: Decision): List[Decision] = d match
    case Decision(pm, cnf) =>
      val fv = filterUnconstrVars(pm)
      if fv.nonEmpty then
        List(
          Decision(updateParModel(pm, Constraint(fv.head.name, true)), simplifyCnf(cnf, Constraint(fv.head.name, true))),
          Decision(updateParModel(pm, Constraint(fv.head.name, false)), simplifyCnf(cnf, Constraint(fv.head.name, false)))
        )
      else List.empty

  def dpll(dec: Decision): DecisionTree =

    case class Frame(d: Decision, done: List[DecisionTree], todos: List[Decision])

    def step(stack: List[Frame]): DecisionTree = stack match
      case Frame(d, done, Nil) :: tail =>
        val ret =
          done match
            case ::(head, next) => Branch(d, head, next.head)
            case Nil => Leaf(d)
        tail match
          case Nil => ret
          case Frame(tn, td, tt) :: more =>
            step(Frame(tn, ret :: td, tt) :: more)

      case Frame(d, done, x :: xs) :: tail =>
        step(Frame(x, Nil, decide(x)) :: Frame(d, done, xs) :: tail)

    step(List(Frame(dec, Nil, decide(dec))))

  def extractSolutions(cnf: CNF): Set[PartialModel] =
    val s =
      for pmSet <- extractSolutionsFromDT(dpll(Decision(extractModelFromCnf(cnf), cnf)))
      yield explodeSolutions(pmSet)
    s.flatten
