package satify.update.parser

import satify.model.cnf.CNF.*
import satify.model.cnf.{CNF, Literal}

import java.io.{File, PrintWriter}
import scala.annotation.tailrec
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

  protected def writeSource(path: String, obj: T): Unit =
    val fileName: String = "output.txt"
    val separator = System.getProperty("file.separator")
    val writer = new PrintWriter(new File(path + separator + fileName))
    try dump(obj).foreach(writer.println)
    finally writer.close()

  protected def stripComments(lines: Seq[String]): Seq[String] =
    lines.filterNot(_.startsWith("c"))

object DimacsCNF extends Dimacs[CNF]:

  private val Header = "p cnf (\\d+) (\\d+)".r

  def write(path: String, cnf: CNF): Unit = writeSource(path, cnf)

  def parse(lines: Seq[String]): Option[CNF] =
    stripComments(lines) match
      case Nil => None
      case Header(_, _) +: clauses =>
        val or = clauses.map(parseOr)
        if or.nonEmpty then Some(buildAndCNF(or))
        else None
      case _ => None

  private def parseOr(clause: String): Or | Literal =
    buildOrCNF(
      clause
        .split(" ")
        .dropRight(1)
        .map(s =>
          val flatIdx = math.abs(s.toInt)
          val literal: Literal = s.toInt match
            case _ if s.toInt > 0 => Symbol(f"x_$flatIdx")
            case _ => Not(Symbol(f"x_$flatIdx"))
          literal
        )
        .toSeq
    )

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

  @tailrec
  private def buildOrCNF(literals: Seq[Literal], p: Option[Or | Literal] = None): Or | Literal =
    p match
      case None => buildOrCNF(literals.tail, Some(literals.head))
      case Some(prev) =>
        literals match
          case head +: Seq() => Or(head, prev)
          case head +: tail => buildOrCNF(tail, Some(Or(head, prev)))

  @tailrec
  private def buildAndCNF(literals: Seq[Or | Literal], p: Option[And | Or | Literal] = None): And | Or | Literal =
    p match
      case None => buildAndCNF(literals.tail, Some(literals.head))
      case Some(prev) =>
        literals match
          case head +: Seq() => And(head, prev)
          case head +: tail => buildAndCNF(tail, Some(And(head, prev)))
