package satify.dsl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.shouldBe

class ReflectionTest extends AnyFlatSpec:

  import satify.dsl.Reflection.*

  "processInput" should "add \"\" to all variables, excluding operators" in {
    processInput("a or not(B) and c") shouldBe """"a" or not("B") and "c""""
    processInput("xor(or(a, b), and(c, d))") shouldBe """xor(or("a", "b"), and("c", "d"))"""
  }

  "processInput" should "work also excluding math operators" in {
    processInput("""a \/ !B /\ c""") shouldBe """"a" \/ !"B" /\ "c""""
    processInput("""\/(/\(a, b), !(b, c))""") shouldBe """\/(/\("a", "b"), !("b", "c"))"""
  }

  "processInput" should "work also works with SAT encodings" in {
    processInput(
      """atLeast(1)(a, b, c) and atMostOne(a, b)"""
    ) shouldBe """atLeast(1)("a", "b", "c") and atMostOne("a", "b")"""
  }

  "processInput" should "ignore number constants" in {
    processInput("""atLeast(two)(a, b, c)""") shouldBe """atLeast(two)("a", "b", "c")"""
    processInput("""(a, b, c) atLeast three""") shouldBe """("a", "b", "c") atLeast three"""
  }

  "processInput" should "replace \\n with space" in {
    processInput("a\nand b") shouldBe """"a" and "b""""
  }

  "processInput" should "remove line comments" in {
    processInput("//use of AND operator\na and b") shouldBe """"a" and "b""""
    processInput("a and b\n// use of AND operator") shouldBe """"a" and "b""""
    processInput("// only a comment") shouldBe ""
  }

  "processInput" should "remove multi line comments" in {
    processInput("/*use of AND operator*/\na and b") shouldBe """"a" and "b""""
    processInput("/*use of\n AND\n operator*/\na and b") shouldBe """"a" and "b""""
    processInput("/* only a comment */") shouldBe ""
  }
