package ExpressionUtilsTest

import model.Expression
import model.Operator
import Expression.*
import model.Operator.*
import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class SubexpressionsTest extends AnyFlatSpec with Matchers:

  "In ((a ∧ ¬b) ∨ c) the subexp ¬b" should "be retrieved as (Literal('X2'), Clause(Not(Literal('b')))" in {
    val exp: Expression = Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c")))
    val list = zipWithLiteral(exp)
      list should contain only(
          (Literal("X2"), Clause(Not(Literal("b")))),
          (Literal("X1"), Clause(And(Literal("a"), Clause(Not(Literal("b")))))),
          (Literal("X0"), Clause(Or(Clause(And(Literal("a"), Clause(Not(Literal("b"))))), Literal("c"))))
      )
  }

  "In a the subexp a" should "be retrieved as (Literal('X0'), Literal('a')" in {
    val exp: Expression = Literal("a")
    val list = zipWithLiteral(exp)
    list should contain only (
      (Literal("X0"), Literal("a"))
      )
  }

  "In ¬a the subexp ¬a" should "be retrieved as (Literal('X0'), Clause(Not(Literal('a')))" in {
    val exp: Expression = Clause(Not(Literal("a")))
    val list = zipWithLiteral(exp)
    println(list)
    list should contain only (
      (Literal("X0"), Clause(Not(Literal("a"))))
      )
  }

  "In (c ∧ (a ∧ ¬b)) the subexp ¬b, (a ∧ ¬b) and (c ∧ (a ∧ ¬b))" should "be retrieved as " +
    "(Literal('X0'), Clause(And(Literal('c'), Clause(And(Literal('a'), Clause(Not(Literal('b'))))))))," +
    "(Literal('X1'), Clause(And(Literal('a'), Clause(Not(Literal('b'))))))," +
    "(Literal('X2'), Clause(Not(Literal('b'))))" in {
    val exp: Expression = Clause(And(Literal("c"), Clause(And(Literal("a"), Clause(Not(Literal("b")))))))
    val list = zipWithLiteral(exp)
    println(list)
    list should contain only (
      (Literal("X0"), Clause(And(Literal("c"), Clause(And(Literal("a"), Clause(Not(Literal("b")))))))),
      (Literal("X1"), Clause(And(Literal("a"), Clause(Not(Literal("b")))))),
      (Literal("X2"), Clause(Not(Literal("b"))))
      )
  }

  "In (c ∧ a) the subexp (c ∧ a)" should "be retrieved as (Literal('X0'), Clause(And(Literal('c'), Literal('a'))))" in {
    val exp: Expression = Clause(And(Literal("c"), Literal("a")))
    val list = zipWithLiteral(exp)
    println(list)
    list should contain only(
      (Literal("X0"), Clause(And(Literal("c"), Literal("a"))))
    )
  }

  "In (¬(p ∧ q) ∨ (r ∨ s)) the subexp (r ∨ s), (p ∧ q) and ¬(p ∧ q)" should "be retrieved as " +
    "(Literal('X0'), Clause(Or(Clause(Not(Clause(And(Literal('p'), Literal('q'))))), Clause(Or(Literal('r'), Literal('s'))))))," +
    "(Literal('X1'), Clause(Not(Clause(And(Literal('p'), Literal('q'))))))," +
    "(Literal('X2'), Clause(And(Literal('p'), Literal('q'))))," +
    "(Literal('X3'), Clause(Or(Literal('r'), Literal('s')))))" in {
    val exp: Expression = Clause(Or(Clause(Not(Clause(And(Literal("p"), Literal("q"))))), Clause(Or(Literal("r"), Literal("s")))))
    val list = zipWithLiteral(exp)
    println(list)
    list should contain only (
      (Literal("X0"), Clause(Or(Clause(Not(Clause(And(Literal("p"), Literal("q"))))), Clause(Or(Literal("r"), Literal("s")))))),
      (Literal("X1"), Clause(Not(Clause(And(Literal("p"), Literal("q")))))),
      (Literal("X2"), Clause(And(Literal("p"), Literal("q")))),
      (Literal("X3"), Clause(Or(Literal("r"), Literal("s"))))
      )
  }

  "In ¬(a ∧ b) the subexp (a ∧ b) and ¬(a ∧ b)" should "be retrieved as " +
    "(Literal('X0'), Clause(Not(Clause(And(Literal('a'), Literal('b'))))))," +
    "(Literal('X1'), Clause(And(Literal('a'), Literal('b'))))" in {
    val exp: Expression = Clause(Not(Clause(And(Literal("a"), Literal("b")))))
    val list = zipWithLiteral(exp)
    println(list)
    list should contain only(
      (Literal("X0"), Clause(Not(Clause(And(Literal("a"), Literal("b")))))),
      (Literal("X1"), Clause(And(Literal("a"), Literal("b"))))
    )
  }

  "In ((a ∨ (b ∧ c)) ∧ d) ∧ (s ∨ t) the subexp (b ∧ c), (a ∨ (b ∧ c))and ¬(a ∧ b)" should "be retrieved as " +
    "(Literal('X0'), Clause(Not(Clause(And(Literal('a'), Literal('b'))))))," +
    "(Literal('X1'), Clause(And(Literal('a'), Literal('b'))))," +
    "(Literal('X2'), Clause(Or(Literal('a'), Clause(And(Literal('b'), Literal('c'))))))," +
    "(Literal('X3'), Clause(And(Literal('b'), Literal('c'))))," +
    "(Literal('X4'), Clause(Or(Literal('s'), Literal('t'))))" in {
    val exp: Expression = Clause(And(Clause(And(Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c"))))), Literal("d"))), Clause(Or(Literal("s"), Literal("t")))))
    val list = zipWithLiteral(exp)
    println(list)
    list should contain only(
      (Literal("X0"), Clause(And(Clause(And(Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c"))))), Literal("d"))), Clause(Or(Literal("s"), Literal("t")))))),
      (Literal("X1"), Clause(And(Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c"))))), Literal("d")))),
      (Literal("X2"), Clause(Or(Literal("a"), Clause(And(Literal("b"), Literal("c")))))),
      (Literal("X3"), Clause(And(Literal("b"), Literal("c")))),
      (Literal("X4"), Clause(Or(Literal("s"), Literal("t"))))
    )
  }
