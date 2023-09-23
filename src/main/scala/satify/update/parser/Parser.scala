package satify.update.parser

import java.io.{File, PrintWriter}
import scala.io.Source

trait Parser[T]:

  def parse(lines: Seq[String]): Option[T]
  def dump(obj: T): Seq[String]
  def read(path: String): Option[T] = readSource(Source.fromFile(path))
  def read: Option[T] = readSource(Source.stdin)
  def write(path: String, obj: T): Unit =
    val fileName: String = "satify_export.txt"
    val separator = System.getProperty("file.separator")
    val writer = new PrintWriter(new File(path + separator + fileName))
    try dump(obj).foreach(writer.println)
    finally writer.close()
  private def readSource(source: Source) =
    try parse(source.getLines.toList)
    finally source.close()
