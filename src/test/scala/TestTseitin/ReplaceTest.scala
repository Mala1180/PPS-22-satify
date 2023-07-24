package TestTseitin

import org.junit.Assert.{assertEquals, assertFalse, assertNotEquals}
import org.junit.Test
import tseitin.Expression
import tseitin.Expression.*
import tseitin.Operator.*
import tseitin.CNFConverter.*

class ReplaceTest:

  @Test
  def testReplace(): Unit =
    //exp = ((a ∧ ¬b) ∨ c)
    //list = [X1 = ¬b, X2 = (a ∧ X1), X3 = X2 ∨ c]
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val substitution: Expression = Clause(Not(Literal("b")))
    val expected: Expression = Clause(Or(Clause(And(Literal("a"), Literal("X0"))), Literal("c")))
    val result = replace(exp, substitution, Literal("X0"))

    println(result)
    assertEquals(expected, result)


  @Test
  def testMiddleReplace(): Unit =
    //exp = ((a ∧ ¬b) ∨ c)
    //list = [X1 = ¬b, X2 = (a ∧ X1), X3 = X2 ∨ c]
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val substitution: Expression = Clause(And(Literal("a"), Clause(Not(Literal("b")))))
    val expected: Expression = Clause(Or(Literal("X0"), Literal("c")))
    val result = replace(exp, substitution, Literal("X0"))

    println(result)
    assertEquals(expected, result)

  @Test
  def testFullReplace(): Unit =
    //exp = ((a ∧ ¬b) ∨ c)
    //list = [X1 = ¬b, X2 = (a ∧ X1), X3 = X2 ∨ c]
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val substitution: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val expected: Expression = Literal("X0")
    val result = replace(exp, substitution, Literal("X0"))

    println(result)
    assertEquals(expected, result)
