package satify.update.parser

import satify.model.CNF.*
import satify.model.expression.Expression
import satify.model.{CNF, Literal, Variable}

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
        if or.nonEmpty then Some(buildAndCNF(or))
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

  private def parseOr(clause: String): Or | Literal =
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

  /*private def buildOrCNF(cnf: Seq[Literal]): Or | Literal =
    cnf.tail.foldLeft[Or | Literal](cnf.head)((p, c) => Or(c, p))

  private def buildAndCNF(cnf: Seq[Or | Literal]): And | Or | Literal =
    cnf.tail.foldLeft[And | Or | Literal](cnf.head)((p, c) => And(c, p))
   */

  private def buildOrCNF(literals: Seq[Literal], p: Option[Or | Literal] = None): Or | Literal =
    p match
      case None => buildOrCNF(literals.tail, Some(literals.head))
      case Some(prev) =>
        literals match
          case head +: Seq() => Or(prev, head)
          case head +: tail => buildOrCNF(tail, Some(Or(prev, head)))

  private def buildAndCNF(literals: Seq[Or | Literal], p: Option[And | Or | Literal] = None): And | Or | Literal =
    p match
      case None => buildAndCNF(literals.tail, Some(literals.head))
      case Some(prev) =>
        literals match
          case head +: Seq() => And(head, prev)
          case head +: tail => buildAndCNF(tail, Some(And(head, prev)))
