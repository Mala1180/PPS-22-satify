package satify.dsl

import satify.model.expression.Expression
import satify.model.expression.Expression.Symbol
import satify.model.expression.SymbolGeneration.{converterVarPrefix, encodingVarPrefix}

import java.util.concurrent.Executors
import scala.util.matching.Regex

object Reflection:

  private val excludedWords = getDSLKeywords
    .mkString("|")
    .replace("|^|", "|\\^|")
    .replace("|\\/|", "|\\\\/|")
    .replace("|/\\|", "|/\\\\|")
  private val symbolsRegexPattern: Regex = s"""((?!$excludedWords\\b)\\b(?![0-9]+\\b)\\w+)""".r
  private val commentsRegexPattern: Regex = """(//.*)|(/\*[^*]*\*+(?:[^/*][^*]*\*+)*/)""".r
  private val privateVariablesPattern: Regex = s"\\b($encodingVarPrefix|$converterVarPrefix)\\w*".r

  def getDSLKeywords: List[String] =
    val operators = classOf[Operators.type].getMethods.map(_.getName).toList.map {
      case "andSymbol" => "/\\"
      case "orSymbol" => "\\/"
      case "notSymbol" => "!"
      case "xorSymbol" => "^"
      case "impliesSymbol" => "->"
      case "iffSymbol" => "<->"
      case op => op
    }
    val encodings = classOf[Encodings.type].getMethods.map(_.getName).toList
    val numbers = classOf[Numbers.type].getMethods.map(_.getName).toList
    val objectMethods = classOf[Object].getMethods.map(_.getName).toList
    (operators ::: encodings ::: numbers).filterNot(objectMethods.contains(_))

  /** Prepares the input for the reflection, adding quotes to all words that are not keywords and removing comments and new lines.
    * @param input the input to process
    * @return the processed input
    */
  def processInput(input: String): String = input
    .replaceAll(commentsRegexPattern.toString(), "")
    .replaceAll(symbolsRegexPattern.toString(), "\"$1\"")
    .replaceAll("\n", " ")
    .trim

  /** Reflects the input to the REPL returning an Expression.
    * If the REPL is not started yet, waits until it is started.
    * @param input the input to evaluate
    * @return the [[Expression]]
    * @throws IllegalArgumentException if the input is malformed
    * @see [[startRepl]]
    */
  def reflect(input: String): Expression =
    if privateVariablesPattern.findFirstIn(input).isDefined then
      throw IllegalArgumentException("Cannot use variables that start with ENC or TSTN")
    val code = processInput(input)
    if code.matches("\"" + symbolsRegexPattern.toString() + "\"") then Symbol(code.substring(1, code.length - 1))
    else if code.isBlank || code.isEmpty then throw new IllegalArgumentException("Empty input")
    else
      val imports =
        """import satify.model.expression.Expression
          |import satify.dsl.DSL.{*, given}
          |""".stripMargin
      println(code)
      try dotty.tools.repl.ScriptEngine().eval(imports + code).asInstanceOf[Expression]
      catch case e: Exception => throw new IllegalArgumentException(e.getMessage)

  /** Starts the REPL in a separate thread
    * When the REPL is started, the a promise is completed permitting to call [[reflect]] method.
    */
  def startRepl(): Unit = Executors
    .newSingleThreadExecutor()
    .execute(() => dotty.tools.repl.ScriptEngine().eval("println()"))
