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

  "GraphColoring with edges containing wrong nodes" should "throw IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      GraphColoring(("n1", "n2") :: Nil, "n3" :: "n4" :: Nil, 2)
    }
  }

  "GraphColoring with wrong number of colors" should "throw IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      GraphColoring(("n1", "n2") :: Nil, "n1" :: "n2" :: Nil, -1)
    }
  }

  "NurseScheduling with negative parameters" should "throw IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      NurseScheduling(-1, -2, -2)
    }
  }
