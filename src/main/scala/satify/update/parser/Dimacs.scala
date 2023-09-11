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
    try dump(obj).foreach(writer.println)
    finally writer.close()

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

  def dump(cnf: CNF): Seq[String] =
    var cnfSeq: Seq[String] = Seq.empty
    var cnt = 0
    val memo: mutable.HashMap[CNF, String] = new mutable.HashMap[CNF, String]():
      def nextVarName(): String =
        cnt += 1
        cnt.toString

      override def apply(key: CNF): String =
        getOrElseUpdate(key, nextVarName())

    import CNF.*
    def dimacs(cnf: CNF): String = cnf match
      case Symbol(value) =>
        memo(Symbol(value))
      case And(left, right) =>
        dimacs(left) + "\n" + dimacs(right)
      case Or(left, right) => dimacs(left) + " " + dimacs(right)
      case Not(branch) =>
        "-" + memo(branch)

    val dimacsString = dimacs(cnf)
    val numClauses = dimacsString.split("\n").length
    // header
    cnfSeq :+= s"p cnf $cnt $numClauses"
    cnfSeq ++= dimacsString.split("\n").map(_ + " 0")
    cnfSeq
