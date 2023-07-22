package TestTseitin

import org.junit.Assert.{assertEquals, assertFalse, assertNotEquals}
import org.junit.Test

class VariablesTest:
  var inputList: List[(Literal, Expression)] = List()
  var outputList: List[(Literal, String)] = List()

  @Test
  def testSimpleVariables(): Unit =
    //exp = ((a ∧ ¬b) ∨ c)
    //list = [X1 = ¬b, X2 = (a ∧ X1), X3 = X2 ∨ c]
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    inputList = subexpressionsWithLabel(exp)
    inputList.foreach(println)
/*
    outputList = generateVariables(inputList)
    //val tryList: List[Formula] = subexpressions(exp)

    println("\n ------------------")
    inputList.foreach(println)
    println("\n ------------------")

    println("\n ------------------")
    outputList.foreach(elem => {
      println(elem._1.name + " => " + elem._2)
    })
    println("\n ------------------")
*/

    //assertEquals(3, outputList.size)
    val substitutions = calculateSubstitutions(inputList)

    val outputList = inputList.map { case (literal, expression) =>
      (literal, substituteLiterals(expression, substitutions)._1)
    }



    println("\n ------------------")
    outputList.foreach(println)
    println("\n ------------------")
    inputList = List()
    //outputList = List()
