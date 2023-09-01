package satify.dsl

object Reflection:

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
  def processInput(input: String): String =
    // TODO: link these operators to the ones in the DSL
    val excludedWords = getDSLKeywords.mkString("|")
    // regExp to match all words that are not operators
    val regexPattern = s"""((?!$excludedWords\\b)\\b[A-Z|a-z]+)"""
    input.replaceAll(regexPattern, "\"$1\"")

  /** Reflects the input to the REPL returning an Expression
    * @param input the input to evaluate
    * @return the [[Expression]]
    */
  def reflect(input: String): Expression =
    if input.matches("""\b[A-Z|a-z]+\b""") then Symbol(input)
    else
      val code: String = input match
        case "" => throw new IllegalArgumentException("Empty input")
        case i => processInput(i)
      val imports =
        """import satify.model.expression.Expression
          |import satify.dsl.DSL.{*, given}
          |""".stripMargin
      println(code)
      dotty.tools.repl.ScriptEngine().eval(imports + code).asInstanceOf[Expression]
