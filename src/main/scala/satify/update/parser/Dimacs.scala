package satify.update.parser

import satify.model.cnf.{CNF, Literal, Variable}
import satify.model.cnf.CNF.*

import java.io.PrintWriter
import java.io.File
import scala.collection.mutable
import scala.io.Source

/** Read/write objects in DIMACS format. */
trait Dimacs[T]:

  def parse(lines: Seq[String]): Option[T]
  def dump(obj: T): Seq[String]

  def read(path: String): Option[T] = readSource(Source.fromFile(path))

  def read: Option[T] = readSource(Source.stdin)

  private def readSource(source: Source) =
    try parse(source.getLines.toList)
    finally source.close()

  private def writeSource(path: String, obj: T): Unit =
    val writer = new PrintWriter(new File(path))
    try dump(obj).foreach(writer.println) finally writer.close()

  protected def stripComments(lines: Seq[String]): Seq[String] =
    lines.filterNot(_.startsWith("c"))

/** Read/write formulas in DIMACS format. */
object DimacsCNF extends Dimacs[CNF]:

  private val Header = "p cnf (\\d+) (\\d+)".r

  def parse(lines: Seq[String]): Option[CNF] =
    stripComments(lines) match
      case Nil => None
      case Header(_, _) +: clauses =>
        val or = clauses.map(parseOr)
        if or.forall(o => o.isDefined) then buildAndCNF(or.map(o => o.get))
        else None
      case _ => None

/*  def dump(cnf: CNF): Seq[String] =

    val c : CNF = And(Or(Symbol(Variable("x5")), Symbol(Variable("x6"))), And(Or(Symbol(Variable("x1")), Symbol(Variable("x2"))), Or(Symbol(Variable("x3")), Symbol(Variable("x4")))))


    val header = s"p cnf ${cnf.variableCount} ${cnf.clauseCount}"
    val clauses = cnf.clauses.map {
      clause => s"${clause.literals.mkString(" ")} 0"
    }
    header +: clauses*/

  def dump(cnf: CNF): Seq[String] =
    var varCount = 0
    var cnfSeq: Seq[String] = Seq.empty

    var ha = new mutable.HashMap[Expression, String]() {
      override def apply(key: CNF): String = getOrElseUpdate(key, nextVarName())
    }

    def nextVarName(): String =
      varCount += 1
      varCount.toString
    import CNF.*
    def test(cnf: CNF): String = cnf match
      case Symbol(value) => ???
      case And(left, right) => ???
      case Or(left, right) => ???
      case Not(branch) => ???


        def traverse(cnf: CNF): String = cnf match {
          case CNF.Symbol(Variable(name, Some(value))) =>
            s"${if (value) "" else "-"}$name"
          case CNF.Symbol(Variable(name, None)) =>
            nextVarName()
          case CNF.And(left, right) =>
            val leftStr = traverse(left)
            val rightStr = traverse(right)
            s"$leftStr $rightStr"
          case CNF.Or(left, right) =>
            val leftStr = traverse(left)
            val rightStr = traverse(right)
            s"$leftStr $rightStr"
          case CNF.Not(branch) =>
            val branchStr = traverse(branch)
            s"-$branchStr"
        }

        val dimacsString = traverse(cnf)

        // Conta il numero di variabili
        val numVariables = varCount

        // Conta il numero di clausole
        val numClauses = dimacsString.split(" 0").length

        // Genera l'intestazione DIMACS
        cnfSeq :+= s"p $numVariables $numClauses"

        // Aggiungi la rappresentazione DIMACS
        cnfSeq ++= dimacsString.split(" 0").map(_ + " 0")

        cnfSeq


        private def parseOr(clause: String): Option[Or | Literal] =
          buildOrCNF(
            clause
              .split(" ")
              .dropRight(1)
              .map(s =>
                val flatIdx = math.abs(s.toInt)
                val literal: Literal = s.toInt match
                  case _ if s.toInt > 0 => Symbol(Variable(f"x_$flatIdx"))
                  case _ => Not(Symbol(Variable(f"x_$flatIdx")))
                literal
              )
              .toSeq
          )

        private def buildOrCNF(cnf: Seq[Literal]): Option[Or | Literal] = cnf match
          case Nil => None
          case head +: Nil => Some(head)
          case head +: tail =>
            buildOrCNF(tail) match
              case Some(value) => Some(Or(head, value))
              case None => Some(head)

        private def buildAndCNF(cnf: Seq[Or | Literal]): Option[And | Or | Literal] = cnf match
          case Nil => None
          case head +: Nil => Some(head)
          case head +: tail =>
            buildAndCNF(tail) match
              case Some(value) => Some(And(head, value))
              case None => Some(head)
