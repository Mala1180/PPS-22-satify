package tseitin

enum Operator:
  case And(exp1: Expression, exp2: Expression)
  case Or(exp1: Expression, exp2: Expression)
  case Xor(exp1: Expression, exp2: Expression)
  case Not(exp: Expression)

enum Expression:
  case Literal(name: String)
  case Clause(op: Operator)

object Expression:
  import CNFConverter.*
  import Operator.*
  import Expression.*

  def contains(exp1: Expression, exp2: Expression) : Boolean =
    subexpressions(exp1).contains(exp2)

  def replace(exp1: Expression, exp2: Expression, l: Literal): Expression = exp1 match
    case Clause(op) if Clause(op) == exp2 => l
    case Clause(And(e1, e2)) =>
      if Clause(And(e1, e2)) == exp2 then Clause(And(replace(e1, exp2, l), replace(e1, exp2, l))) else replaceOp(And(e1,e2), exp2, l)
    case Clause(Or(e1, e2)) =>
      if Clause(Or(e1, e2)) == exp2 then Clause(Or(replace(e1, exp2, l), replace(e1, exp2, l))) else replaceOp(Or(e1,e2), exp2, l)
    case Clause(Not(e)) =>
      if Clause(Not(e)) == exp2 then Clause(Not(replace(e, exp2, l))) else replaceOp(Not(e), exp2, l)
    case _ => l
    private def replaceOp(op: Operator, exp2: Expression, l: Literal): Expression = op match
      case And(Clause(op), c) => if Clause(op) == exp2 then Clause(And(l, c)) else Clause(And(replace(Clause(op), exp2, l), replace(c, exp2, l)))
      case And(c, Clause(op)) => if Clause(op) == exp2 then Clause(And(c, l)) else Clause(And(replace(c, exp2, l), replace(Clause(op), exp2, l)))
      case Or(Clause(op), c2) => if Clause(op) == exp2 then Clause(Or(l, c2)) else Clause(Or(replace(Clause(op), exp2, l), c2))
      case Or(c1, Clause(op)) => if Clause(op) == exp2 then Clause(Or(c1, l)) else Clause(Or(c1, replace(Clause(op), exp2, l)))
      case Not(Clause(op)) => if Clause(op) == exp2 then Clause(Not(l)) else Clause(Not(replace(Clause(op), exp2, l)))



 /* def subexpressions(exp: Expression): List[Expression] = exp match
    case Literal(x) => List(exp)
    case Clause(Not(Literal(x))) => List(exp)
    case _ => subExp(exp, List())

  private def subExp(exp: Expression, list: List[Expression]): List[Expression] = exp match
    case Clause(op) => exp :: list ::: subOp(op, list)
    case _ => List()

  private def subOp(op: Operator, list: List[Expression]): List[Expression] = op match
    case And(exp1, exp2) => subExp(exp1, list) ::: subExp(exp2, list)
    case Or(exp1, exp2) => subExp(exp1, list) ::: subExp(exp2, list)
    case Xor(exp1, exp2) => subExp(exp1, list) ::: subExp(exp2, list)
    case Not(exp) => subExp(exp, list) */


 //FIRST TEST REPLACE

/*def replace(exp1: Expression, exp2: Expression, l: Literal) : Expression =
  exp1 match
    case Clause(And(e1, e2)) => Clause(And(replace(e1, exp2, l), replace(e1, exp2, l)))
    case Clause(Or(e1, e2)) => Clause(Or(replace(e1, exp2, l), replace(e1, exp2, l)))
    case Clause(Xor(e1, e2)) => Clause(Xor(replace(e1, exp2, l), replace(e1, exp2, l)))
    case Clause(Not(e)) => Clause(Not(replace(e, exp2, l)))
    case _ => exp1
    //case Clause(op) != exp2 => subOp1(op, exp2, l)

    def replaceOp(op: Operator, exp2: Expression, l: Literal) : Expression = op match
      case And(Clause(op), c) if Clause(op) == exp2 => Clause(And(l, c))
      case And(c, Clause(op)) if Clause(op) == exp2 => Clause(And(c, l))
      case Or(Clause(op), c) if Clause(op) == exp2 => Clause(Or(l, c))
      case Or(c, Clause(op)) if Clause(op) == exp2 => Clause(Or(c, l))
      case Xor(Clause(op), c) if Clause(op) == exp2 => Clause(Xor(l, c))
      case Xor(c, Clause(op)) if Clause(op) == exp2 => Clause(Xor(c, l))
      case Not(Clause(op)) if Clause(op) == exp2 => Clause(Not(l))
      case _ => exp2*/



trait variableGenerator:
  import Expression.*
  import Operator.*

  def generateVariables(list: List[(Literal, Expression)]): List[(Literal, String)] =
    def substitute(exp: Expression, clause: Expression, literal: Literal): String =
      /*val prova = exp.replace(clause, literal)*/
      val s = exp.toString.replace(clause.toString, literal.toString)
      s

    def tryes(list: List[(Literal, Expression)], res: List[(Literal, String)]): List[(Literal, String)] = list match
      case Nil => res
      case (literal, clause) :: tail =>
        //println("CI ENTRO CON PRIMA ENTRY : " + literal)
        var result: List[(Literal, String)] = List()
        tail.foreach(subexpression => {
          if (contains(subexpression._2, clause)) {
            val subb = substitute(subexpression._2, clause, literal)
            result = (subexpression._1, subb) :: res ::: result
          }
        })

        /*        var tes: List[(Literal, Formula)] = List()
                result.foreach(res => {
                  tes = tail.updated(tail.indexOf((res._1, clause)), (res._1, res._2))
                })*/

        result //::: tryes(tes, result)

    //tryes(tail, res ::: tryes(sub(op, List()), List()))
    val test = list.reverse
    println("List : " + test)
    tryes(test, List())

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
      case Xor(exp1, exp2) => subExp(exp1, list) ::: subExp(exp2, list)
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
      case Xor(exp1, exp2) => subExp1(exp1, list) ::: subExp1(exp2, list)
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