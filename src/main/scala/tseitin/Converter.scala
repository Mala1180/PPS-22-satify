package tseitin

enum Operator:
  case And(exp1: Expression, exp2: Expression)
  case Or(exp1: Expression, exp2: Expression)
  case Not(exp: Expression)

enum Expression:
  case Literal(name: String)
  case Clause(op: Operator)

object Expression:
  import CNFConverter.*
  import Operator.*
  import Expression.*

  def contains(exp1: Expression, exp2: Expression): Boolean = subexpressions(exp1).contains(exp2)

  def replace(exp1: Expression, exp2: Expression, l: Literal): Expression = exp1 match
    case Clause(op) if Clause(op) == exp2 => l
    case Clause(And(e1, e2)) => replaceOp(And(e1, e2), exp2, l)
    case Clause(Or(e1, e2)) => replaceOp(Or(e1, e2), exp2, l)
    case Clause(Not(e)) => replaceOp(Not(e), exp2, l)
    case _ => exp1
  private def replaceOp(op: Operator, exp2: Expression, l: Literal): Expression = op match
    case And(e1, e2) => Clause(And(replace(e1, exp2, l), replace(e2, exp2, l)))
    case Or(e1, e2) => Clause(Or(replace(e1, exp2, l), replace(e2, exp2, l)))
    case Not(e) => Clause(Not(replace(e, exp2, l)))

trait variableGenerator:
  import Expression.*
  import Operator.*

  def generateVariables(list: List[(Literal, Expression)]): List[(Literal, String)] = ???
  def printExpressions(list: List[Expression]): List[Clause] =
    list.foreach(clause => println(clause))
    List()

trait subexpressionsCalculator:
  import Expression.*
  import Operator.*

  def subexpressions(exp: Expression): List[Expression] = exp match
    case Literal(x) => List(exp)
    case Clause(Not(Literal(x))) => List(exp)
    case _ => subExp(exp, List())
  private def subExp(exp: Expression, list: List[Expression]): List[Expression] = exp match
    case Clause(op) => exp :: list ::: subOp(op, list)
    case _ => List()
  private def subOp(op: Operator, list: List[Expression]): List[Expression] = op match
    case And(exp1, exp2) => subExp(exp1, list) ::: subExp(exp2, list)
    case Or(exp1, exp2) => subExp(exp1, list) ::: subExp(exp2, list)
    case Not(exp) => subExp(exp, list)

  def subexpressionsWithLabel(exp: Expression): List[(Literal, Expression)] = exp match
    case Literal(x) => List((freshLabel1(), exp))
    case Clause(Not(Literal(x))) => List((freshLabel1(), exp))
    case _ => subExp1(exp, List())
  private def subExp1(exp: Expression, list: List[(Literal, Expression)]): List[(Literal, Expression)] = exp match
    case Clause(op) => (freshLabel1(), exp) :: list ::: subOp1(op, list)
    case _ => List()
  private def subOp1(op: Operator, list: List[(Literal, Expression)]): List[(Literal, Expression)] = op match
    case And(exp1, exp2) => subExp1(exp1, list) ::: subExp1(exp2, list)
    case Or(exp1, exp2) => subExp1(exp1, list) ::: subExp1(exp2, list)
    case Not(exp) => subExp1(exp, list)
    case _ => List()
  private def freshLabel1(): Literal =
    val l: Literal = Literal("X" + c1)
    c1 = c1 + 1
    l
  private var c1 = 0

trait tseitinTransformation:
  import Expression.*
  def tseitin(expression: Expression): List[Expression] = ???

object CNFConverter extends tseitinTransformation, subexpressionsCalculator, variableGenerator:
  @main def main(): Unit =
    println("Hello from the CNFConverter!")
