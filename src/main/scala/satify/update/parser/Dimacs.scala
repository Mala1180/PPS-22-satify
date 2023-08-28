package satify.update.parser

import satify.model.CNF.*
import satify.model.{CNF, Literal, Variable}

import java.io.{File, PrintWriter}
import scala.io.Source

/** Read/write objects in DIMACS format. */
trait Dimacs[T]:

  def parse(lines: Seq[String]): Option[T]

  // TODO
  // def dump(obj: T): Seq[String]

  /*
  def write(path: String, obj: T): Unit = {
    val writer = new PrintWriter(new File(path))
    try dump(obj).foreach(writer.println) finally writer.close()
  }
   */

  def read(path: String): Option[T] = readSource(Source.fromFile(path))

  def read: Option[T] = readSource(Source.stdin)

  private def readSource(source: Source) =
    try parse(source.getLines.toList)
    finally source.close()

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

  // TODO
  /*
  def dump(cnf: CNF): Seq[String] = {
    val header = s"p cnf ${cnf.variableCount} ${cnf.clauseCount}"
    val clauses = cnf.clauses.map {
      clause => s"${clause.literals.mkString(" ")} 0"
    }
    header +: clauses
  }
   */

  private def parseOr(clause: String): Option[Or | Literal] =
    buildOrCNF(
      clause
        .split(" ")
        .dropRight(1)
        .map(s =>
          /*val flatIdx = s.toInt
          val row: Int = math.abs(flatIdx) / n
          val col: Int =  math.abs(flatIdx) % n
          flatIdx match
            case _ if flatIdx >= 0 =>  Symbol(Variable(f"x_${row}_$col"))
            case _ => Not(Symbol(Variable(f"x_${row}_$col")))
           */
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
