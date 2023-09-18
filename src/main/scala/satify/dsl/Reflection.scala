package satify.dsl

import satify.model.expression.Expression
import satify.model.expression.Expression.Symbol

import java.util.concurrent.Executors

object Reflection:

  private val excludedWords = getDSLKeywords.mkString("|")
  private val regexPattern = s"""((?!$excludedWords\\b)\\b(?![0-9]+\\b)\\w+)"""

  private def getDSLKeywords: List[String] =
    val operators = classOf[Operators.type].getMethods.map(_.getName).toList
    val encodings = classOf[Encodings.type].getMethods.map(_.getName).toList
    val numbers = classOf[Numbers.type].getMethods.map(_.getName).toList
    val objectMethods = classOf[Object].getMethods.map(_.getName).toList
    (operators ::: encodings ::: numbers).filterNot(objectMethods.contains(_))

  /** Prepares the input for the reflection, adding quotes to all words that are not keywords
    * @param input the input to process
    * @return the processed input
    */
  def processInput(input: String): String = input
    .replaceAll(regexPattern, "\"$1\"")
    .replaceAll("\n", " ")

  /** Reflects the input to the REPL returning an Expression.
    * If the REPL is not started yet, waits until it is started.
    * @param input the input to evaluate
    * @return the [[Expression]]
    * @throws IllegalArgumentException if the input is malformed
    * @see [[startRepl]]
    */
  def reflect(input: String): Expression =
    if input.matches(regexPattern) then Symbol(input)
    else
      val code: String = input match
        case "" => throw new IllegalArgumentException("Empty input")
        case i => processInput(i)
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
