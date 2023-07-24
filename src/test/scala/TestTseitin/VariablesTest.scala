package TestTseitin

import org.junit.Assert.{assertEquals, assertFalse, assertNotEquals}
import org.junit.Test
import tseitin.Expression
import tseitin.Expression.*
import tseitin.Operator.*
import tseitin.CNFConverter.*

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


    println("\n ------------------")
    outputList.foreach(println)
    println("\n ------------------")
    inputList = List()
    outputList = List()
