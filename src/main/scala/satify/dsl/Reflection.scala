package satify.dsl

import satify.model.Expression

object Reflection:

  private def getDSLKeywords: List[String] =
    val operators = classOf[Operators.type].getMethods.map(_.getName).toList
    val encodings = classOf[SatEncodings.type].getMethods.map(_.getName).toList
    val numbers = classOf[Numbers.type].getMethods.map(_.getName).toList
    val objectMethods = classOf[Object].getMethods.map(_.getName).toList
    (operators ::: encodings ::: numbers).filterNot(objectMethods.contains(_))

  def processInput(input: String): String =
    // TODO: link these operators to the ones in the DSL
    val excludedWords = getDSLKeywords.mkString("|")
    println(excludedWords)
    // regExp to match all words that are not operators
    val regexPattern = s"""((?!$excludedWords\\b)\\b[A-Z|a-z]+)"""
    input.replaceAll(regexPattern, "\"$1\"")

  def reflect(input: String): Expression =
    val code = processInput(input)
    val imports =
      """import satify.model.Expression
        |import satify.dsl.DSL.{*, given}
        |""".stripMargin
    println(imports + code)
    // TODO: does not work with only one symbol
    dotty.tools.repl.ScriptEngine().eval(imports + code).asInstanceOf[Expression]
