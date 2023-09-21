package satify.model.problems

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ProblemWrongUseTest extends AnyFlatSpec with Matchers:

  "NQueens(-2)" should "throw IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      NQueens(-2)
    }
  }

  "NQueens(0)" should "throw IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      NQueens(0)
    }
  }
