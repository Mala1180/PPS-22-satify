package satify.model.dsl

import satify.model.Expression

object Reflection:

  private def getDSLOperators: List[String] =
    def lookupSymbol(name: String): String = name match
      case "xorSymbol" => "^"
      case "andSymbol" => "/\\"
      case "orSymbol" => "\\/"
      case "notSymbol" => "!"
      case "impliesSymbol" => "->"
      case "iffSymbol" => "<->"
      case _ => name

    val operators = classOf[Operators.type].getMethods.map(_.getName).map(lookupSymbol).toList
    val encodings = classOf[SatEncodings.type].getMethods.map(_.getName).toList
    val objectMethods = classOf[Object].getMethods.map(_.getName).toList
    (operators ::: encodings).filterNot(objectMethods.contains(_))

  private def processInput(input: String): String =
    // TODO: link these operators to the ones in the DSL
    val operators = getDSLOperators
    input.trim
      .split("[ ()]")
      .filterNot(_.isBlank)
      .map(symbol => if !operators.contains(symbol) then s"\"$symbol\"" else symbol)
      .reduce((a, b) => s"$a $b")

  def reflect(input: String): Expression =
    val code = processInput(input)
    val imports =
      """import satify.model.Expression
        |import satify.dsl.DSL.{*, given}
        |""".stripMargin
    println(imports + code)
    // TODO: does not work with only one symbol
    dotty.tools.repl.ScriptEngine().eval(imports + code).asInstanceOf[Expression]
