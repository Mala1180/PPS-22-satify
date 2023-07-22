package TestTseitin

import org.junit.Assert.{assertEquals, assertFalse, assertNotEquals}
import org.junit.Test

class SubexpressionTest:
  var list: List[Expression] = List()

  @Test
  def testSimpleLiteral(): Unit =
    // p
    val exp1: Expression = Literal("p")
    list = subexpressions(exp1)

    assertTrue(list.contains(exp1))
    assertEquals(1, list.size)

    list = List()

    // !p
    val exp2: Expression = Clause(Not(Literal("p")))
    list = subexpressions(exp2)

    println("\n ------------------")
    list.foreach(println)
    println("\n ------------------")

    assertTrue(list.contains(exp2))
    assertEquals(1, list.size)

  @Test
  def testComplexSubexpression(): Unit =
    //((p ∧ q) ∨ (r ∧ ¬s)) ∧ (¬(t ∨ u) ∧ v) ∨ (w ∧ x) ∨ (a ∨ b)
    val exp = Clause(Or(Clause(Or(Clause(And(Clause(Or(Clause(And(Literal("p"), Literal("q"))), Clause(And(Literal("r"), Clause(Not(Literal("s"))))))), Clause(And(Clause(Not(Clause(Or(Literal("t"), Literal("u"))))), Literal("v"))))), Clause(Or(Literal("w"), Literal("x"))))), Clause(Or(Literal("a"), Literal("b")))))
    list = subexpressions(exp)

    val x1 = Clause(Or(Clause(And(Clause(Or(Clause(And(Literal("p"), Literal("q"))), Clause(And(Literal("r"), Clause(Not(Literal("s"))))))), Clause(And(Clause(Not(Clause(Or(Literal("t"), Literal("u"))))), Literal("v"))))), Clause(Or(Literal("w"), Literal("x"))))) //OK
    val x2 = Clause(And(Clause(Or(Clause(And(Literal("p"), Literal("q"))), Clause(And(Literal("r"), Clause(Not(Literal("s"))))))), Clause(And(Clause(Not(Clause(Or(Literal("t"), Literal("u"))))), Literal("v")))))
    val x3 = Clause(Or(Clause(And(Literal("p"), Literal("q"))), Clause(And(Literal("r"), Clause(Not(Literal("s")))))))
    val x4 = Clause(And(Literal("p"), Literal("q")))
    val x5 = Clause(And(Literal("r"), Clause(Not(Literal("s")))))
    val x6 = Clause(Not(Literal("s")))
    val x7 = Clause(And(Clause(Not(Clause(Or(Literal("t"), Literal("u"))))), Literal("v")))
    val x8 = Clause(Not(Clause(Or(Literal("t"), Literal("u")))))
    val x9 = Clause(Or(Literal("t"), Literal("u")))
    val x10 = Clause(Or(Literal("w"), Literal("x")))
    val x11 = Clause(Or(Literal("a"), Literal("b")))

    assertTrue(list.contains(x1))
    assertTrue(list.contains(x2))
    assertTrue(list.contains(x3))
    assertTrue(list.contains(x4))
    assertTrue(list.contains(x5))
    assertTrue(list.contains(x6))
    assertTrue(list.contains(x7))
    assertTrue(list.contains(x8))
    assertTrue(list.contains(x9))
    assertTrue(list.contains(x10))
    assertTrue(list.contains(x11))
    assertTrue(list.contains(exp))

    assertEquals(12, list.size)

  @Test
  def testSimpleSubexpression4(): Unit =
    //(c ∧ a)
    var list: List[Expression] = List()
    val exp: Expression = Clause(And(Literal("c"), Literal("a")))
    list = subexpressions(exp)

    assertTrue(list.contains(exp))
    assertEquals(1, list.size)

  @Test
  def testSimpleSubexpression3(): Unit =
    //(c ∧ (a ∧ ¬b))
    val exp: Expression = Clause(And(Literal("c"), Clause(And(Literal("a"), Clause(Not(Literal("b")))))))
    list = subexpressions(exp)

    val x1 = Clause(And(Literal("a"), Clause(Not(Literal("b")))))
    val x2 = Clause(Not(Literal("b")))

    assertTrue(list.contains(x1))
    assertTrue(list.contains(x2))
    assertTrue(list.contains(exp))
    assertEquals(3, list.size)

  @Test
  def testSimpleSubexpression2(): Unit =
    //((a ∧ ¬b) ∨ c)
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    list = subexpressions(exp)

    val x1 = Clause(And(Literal("a"), Clause(Not(Literal("b")))))
    val x2 = Clause(Not(Literal("b")))

    assertTrue(list.contains(x1))
    assertTrue(list.contains(x2))
    assertTrue(list.contains(exp))
    assertEquals(3, list.size)


  @Test
  def testSimpleSubexpressionWithNot(): Unit =
    //(¬(p ∧ q) ∨ (r ∨ s)) => (p ∧ q) | ¬(p ∧ q) | (r ∨ s)
    val exp: Expression = Clause(Or(Clause(Not(Clause(And(Literal("p"), Literal("q"))))), Clause(Or(Literal("r"), Literal("s")))))
    list = subexpressions(exp)

    val x1 = Clause(Not(Clause(And(Literal("p"), Literal("q")))))
    val x2 = Clause(And(Literal("p"), Literal("q")))
    val x3 = Clause(Or(Literal("r"), Literal("s")))

    assertTrue(list.contains(x1))
    assertTrue(list.contains(x2))
    assertTrue(list.contains(x3))
    assertTrue(list.contains(exp))
    assertEquals(4, list.size)


  @Test
  def testSimpleSubexpression(): Unit =
    //!(a and b) => (a and b) | !(a and b)
    val exp: Expression = Clause(Not(Clause(And(Literal("a"), Literal("b")))))
    list = subexpressions(exp)

    val x1 = Clause(And(Literal("a"), Literal("b")))

    assertTrue(list.contains(x1))
    assertTrue(list.contains(exp))
    assertEquals(2, list.size)


  @Test
  def testMultipleNestedSubexpressions(): Unit =
    //((a ∨ (b ∧ c)) ∧ d) ∧ (s ∨ t) => (b ∧ c) | (a ∨ (b ∧ c)) | ((a ∨ (b ∧ c)) ∧ d) | (s ∨ t)
    val exp: Expression = Clause(And(Clause(And(Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c"))))), Literal("d"))), Clause(Or(Literal("s"), Literal("t")))))
    list = subexpressions(exp)

    val x1 = Clause(And(Literal("b"), Literal("c")))
    val x2 = Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c")))))
    val x3 = Clause(And(Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c"))))), Literal("d")))
    val x4 = Clause(Or(Literal("s"), Literal("t")))

    assertTrue(list.contains(x1))
    assertTrue(list.contains(x2))
    assertTrue(list.contains(x3))
    assertTrue(list.contains(x4))
    assertTrue(list.contains(exp))
    assertEquals(5, list.size)


  @Test
  def testNestedSubexpressions(): Unit =
    //((p ∨ q) ∧ r) ∧ (s ∨ t)
    val exp: Expression = Clause(And(Clause(And(Clause(Or(Literal("p"), Literal("q"))), Literal("r"))), Clause(Or(Literal("s"), Literal("t")))))
    list = subexpressions(exp)

    val x1 = Clause(Or(Literal("p"), Literal("q")))
    val x2 = Clause(And(Clause(Or(Literal("p"), Literal("q"))), Literal("r")))
    val x3 = Clause(Or(Literal("s"), Literal("t")))

    assertTrue(list.contains(x1))
    assertTrue(list.contains(x2))
    assertTrue(list.contains(x3))
    assertTrue(list.contains(exp))
    assertEquals(4, list.size)


  @Test
  def testSubExpressions(): Unit =

    // ((a and !b) or !(c and d)) => (a and !b) | !(c and d) | (c and d) | ((a and !b) or !(c and d))
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))),
      Clause(Not(Clause(And(Literal("c"), Literal("d")))))))
    list = subexpressions(exp)

    val x1 = Clause(And(Literal("a"), Clause(Not(Literal("b")))))
    val x2 = Clause(Not(Literal("b")))
    val x3 = Clause(Not(Clause(And(Literal("c"), Literal("d")))))
    val x4 = Clause(And(Literal("c"), Literal("d")))

    assertTrue(list.contains(x1))
    assertTrue(list.contains(x2))
    assertTrue(list.contains(x3))
    assertTrue(list.contains(x4))
    assertTrue(list.contains(exp))
    assertEquals(5, list.size)

    list = List()

    val exp2: Expression = Clause(And(Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Clause(And(Literal("b"), Clause(Not(Literal("c"))))))), Clause(Or(Literal("d"), Literal("c")))))
    list = subexpressions(exp2)

    val y1 = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Clause(And(Literal("b"), Clause(Not(Literal("c")))))))
    val y2 = Clause(And(Literal("a"), Clause(Not(Literal("b")))))
    val y3 = Clause(Not(Literal("b")))
    val y4 = Clause(And(Literal("b"), Clause(Not(Literal("c")))))
    val y5 = Clause(Not(Literal("c")))
    val y6 = Clause(Or(Literal("d"), Literal("c")))

    assertTrue(list.contains(y1))
    assertTrue(list.contains(y2))
    assertTrue(list.contains(y3))
    assertTrue(list.contains(y4))
    assertTrue(list.contains(y5))
    assertTrue(list.contains(y6))
    assertTrue(list.contains(exp2))
    assertEquals(list.size, 7)