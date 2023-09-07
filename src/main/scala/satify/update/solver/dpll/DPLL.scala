package satify.update.solver.dpll

import satify.model
import satify.model.Bool.False
import satify.model.CNF.*
import satify.model.dpll.DecisionTree.*
import satify.model.dpll.{Constraint, Decision, DecisionTree, PartialModel}
import satify.model.{CNF, Variable}
import CNFSimplification.*
import ConflictIdentification.isUnsat
import PartialModelUtils.*

import java.util.concurrent.Executors
import scala.concurrent.Future
import scala.annotation.tailrec
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.language.postfixOps
import scala.util.Random

private[solver] object DPLL:

  private val rnd = Random(42)
  private val executorService = Executors.newFixedThreadPool(100000)
  private val ec = ExecutionContext.fromExecutor(executorService)

  /** DPLL (Davis-Putnam-Logemann-Loveland) algorithm.
    * @param dec Decision to apply
    * @return built DecisionTree from dec
    */
  def dpll(dec: Decision): DecisionTree =

    /** Recursively call dpll applying the given constraint.
      * @param dec the previous Decision
      * @param constr Constraint to apply
      * @return updated DecisionTree with the new Decision
      */
    def decide(dec: Decision, constr: Constraint): Future[DecisionTree] =
      dec match
        case Decision(parModel, cnf) =>
          Future {
            println(Thread.currentThread().getName)
            dpll(Decision(updateParModel(parModel, constr), simplifyCnf(cnf, constr)))
          }(ec)

    /** Branch on a unit literal.
      * @param dec the previous Decision
      * @return an Option containing a Branch if there's a unit literal, None otherwise.
      */
    def unitPropDecision(dec: Decision): Option[Branch] = dec match
      case Decision(parModel, cnf) =>
        unitPropagation(cnf) match
          case Some(c @ Constraint(name, value)) =>
            Some(
              Branch(
                dec,
                Await.result(decide(dec, c), Duration.Inf),
                Leaf(Decision(updateParModel(parModel, Constraint(name, !value)), Symbol(False)))
              )
            )
          case None => None

    /** Branch on the pure literal branch first.
      * @param dec the previous Decision.
      * @return an Option containing a Branch if there's a pure literal, None otherwise.
      */
    def pureLiteralEliminationDecision(dec: Decision): Option[Branch] =
      pureLiteralElimination(dec) match
        case Some(c @ Constraint(name, value)) =>
          Some(
            Branch(
              dec,
              Await.result(decide(dec, c), Duration.Inf),
              Await.result(decide(dec, Constraint(name, !value)), Duration.Inf)
            )
          )
        case None => None

    /** Branch the decision tree by choosing a random variable among the available ones and by
      * applying a random constraint.
      * @param dec the previous Decision
      * @return updated DecisionTree with the random Decision.
      */
    def randomDecision(dec: Decision): DecisionTree =
      dec match
        case Decision(_, cnf) =>
          val unVars = filterUnconstrVars(extractModelFromCnf(cnf))
          if unVars.nonEmpty then
            val v = rnd.nextBoolean()
            unVars(rnd.between(0, unVars.size)) match
              case Variable(name, _) =>
                Branch(
                  dec,
                  Await.result(decide(dec, Constraint(name, v)), Duration.Inf),
                  Await.result(decide(dec, Constraint(name, !v)), Duration.Inf)
                )
          else Leaf(dec)

    dec match
      case Decision(_, cnf) =>
        if isUnsat(cnf) then Leaf(dec)
        else
          unitPropDecision(dec) match
            case Some(branch) => branch
            case None =>
              pureLiteralEliminationDecision(dec) match
                case Some(branch) => branch
                case None => randomDecision(dec)

  /** Apply unit propagation.
    * @param cnf where to search for a unit literal
    * @return an Option containing a Constraint if a unit literal is present, None otherwise.
    */
  private def unitPropagation(cnf: CNF): Option[Constraint] =
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

  /** Eliminate a pure literal.
    * @param dec previous Decision
    * @return an Option containing a Constraint if a pure literal is present, None otherwise.
    */
  @tailrec
  private def pureLiteralElimination(dec: Decision): Option[Constraint] =

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

  def extractSolutions(cnf: CNF): Set[PartialModel] =
    val s =
      for pmSet <- extractSolutionsFromDT(dpll(Decision(extractModelFromCnf(cnf), cnf)))
      yield explodeSolutions(pmSet)
    s.flatten
