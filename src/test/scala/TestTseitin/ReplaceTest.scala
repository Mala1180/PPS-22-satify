package TestTseitin

import org.junit.Assert.{assertEquals, assertFalse, assertNotEquals}
import org.junit.Test
import tseitin.Expression
import tseitin.Expression.*
import tseitin.Operator.*
import tseitin.CNFConverter.*

class ReplaceTest:

  @Test
  def testEntireReplace(): Unit =
    //exp = ((a ∧ ¬b) ∨ c)
    //list = [X1 = ¬b, X2 = (a ∧ X1), X3 = X2 ∨ c]
    val expression: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val sub1: Expression = Clause(Not(Literal("b")))
    val exp1: Expression = Clause(Or(Clause(And(Literal("a"), Literal("X0"))), Literal("c")))
    val res1 = replace(expression, sub1, Literal("X0"))
    assertEquals(exp1, res1)
    val sub2: Expression = Clause(And(Literal("a"), Literal("X0")))
    val exp2: Expression = Clause(Or(Literal("X1"), Literal("c")))
    val res2 = replace(exp1, sub2, Literal("X1"))
    assertEquals(exp2, res2)
    println(res2)

  @Test
  def testCornerReplace(): Unit =
    // "((¬b ∧ ¬b) ∨ ¬b)" should "be replaced with ....."
    //exp = ((¬b ∧ ¬b) ∨ ¬b)
    //list = [X1 = ¬b, X2 = (X1 ∧ X1), X3 = X2 ∨ X1]
    val exp: Expression = Clause(Or(Clause(And(Clause(Not(Literal("b"))), Clause(Not(Literal("b"))))), Clause(Not(Literal("b")))))
    val substitution: Expression = Clause(And(Clause(Not(Literal("b"))), Clause(Not(Literal("b")))))
    val expected: Expression = Clause(Or(Literal("X0"), Clause(Not(Literal("b")))))
    val result = replace(exp, substitution, Literal("X0"))

    println(result)
    assertEquals(expected, result)

  @Test
  def testTripleReplace(): Unit =
    // "((¬b ∧ ¬b) ∨ ¬b)" should "be replaced with ....."
    //exp = ((¬b ∧ ¬b) ∨ ¬b)
    //list = [X1 = ¬b, X2 = (X1 ∧ X1), X3 = X2 ∨ X1]
    val exp: Expression = Clause(Or(Clause(And(Clause(Not(Literal("b"))), Clause(Not(Literal("b"))))), Clause(Not(Literal("b")))))
    val substitution: Expression = Clause(Not(Literal("b")))
    val expected: Expression = Clause(Or(Clause(And(Literal("X0"), Literal("X0"))), Literal("X0")))
    val result = replace(exp, substitution, Literal("X0"))

    println(result)
    assertEquals(expected, result)

  @Test
  def testMultipleReplace(): Unit =
    //exp = ((a ∧ ¬b) ∨ ¬b)
    //list = [X1 = ¬b, X2 = (a ∧ X1), X3 = X2 ∨ X1]
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Clause(Not(Literal("b")))))
    val substitution: Expression = Clause(Not(Literal("b")))
    val expected: Expression = Clause(Or(Clause(And(Literal("a"), Literal("X0"))), Literal("X0")))
    val result = replace(exp, substitution, Literal("X0"))

    println(result)
    assertEquals(expected, result)

  @Test
  def testSimpleReplace(): Unit =
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
