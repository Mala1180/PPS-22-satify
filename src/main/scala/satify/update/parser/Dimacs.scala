package satify.update.parser

import satify.model.{CNF, Variable, Literal}
import satify.model.CNF.*

import java.io.{File, PrintWriter}
import scala.io.Source

/** Read/write objects in DIMACS format. */
trait Dimacs[T] {

  def parse(lines: Seq[String]): Option[T]

  // TODO: after dpll conflict id merge
  // def dump(obj: T): Seq[String]

  /*def write(path: String, obj: T): Unit = {
    val writer = new PrintWriter(new File(path))
    try dump(obj).foreach(writer.println) finally writer.close()
  }*/

  def read(path: String): Option[T] = readSource(Source.fromFile(path))

  def read: Option[T] = readSource(Source.stdin)

  private def readSource(source: Source) =
    try parse(source.getLines.toList)
    finally source.close()

  protected def stripComments(lines: Seq[String]): Seq[String] =
    lines.filterNot(_.startsWith("c"))
}

/** Read/write formulas in DIMACS format. */
object DimacsCNF extends Dimacs[CNF]:

  private val Header = "p cnf (\\d+) (\\d+)".r

  def parse(lines: Seq[String]): Option[CNF] =
    stripComments(lines) match
      case Header(_, _) +: Seq() => None
      case Header(_, _) +: clauses =>
        Some(
          buildAndCNF(
            clauses map { clause =>
              buildOrCNF(
                clause
                  .split(" ")
                  .dropRight(1)
                  .map(x =>
                    (x.toInt match
                      case n if n >= 0 => Symbol(Variable(f"X_$n"))
                      case n if n < 0 => Not(Symbol(Variable(f"X_${math.abs(n)}")))
                    ).asInstanceOf[Literal]
                  )
                  .toSeq
              )
            }
          )
        )
      case _ => None

  // TODO: after dpll conflict id merge
  /*def dump(cnf: CNF): Seq[String] = {
    val header = s"p cnf ${cnf.variableCount} ${cnf.clauseCount}"
    val clauses = cnf.clauses.map {
      clause => s"${clause.literals.mkString(" ")} 0"
    }
    header +: clauses
  }*/

  private def buildOrCNF(cnf: Seq[Literal]): Or | Literal = cnf match
    case head +: Seq() => head
    case head +: tail => Or(head, buildOrCNF(tail))

  private def buildAndCNF(cnf: Seq[Or | Literal]): And | Or | Literal = cnf match
    case head +: Seq() => head
    case head +: tail => And(head, buildAndCNF(tail))
