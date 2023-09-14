package satify.scalameter.problems.nqueens

import org.scalameter.api.*
import satify.model.problems.NQueens
import satify.update.solver.Solver
import satify.update.solver.SolverType.*

object SolvingBenchmark extends Bench.LocalTime:
  val sizes: Gen[Int] = Gen.range("size")(2, 5, 1)

  /** Benchmark for NQueens creation and solving time */
  performance of "NQueens time" in {
    measure method "NQueens" in {
      using(sizes) in { size =>
        val t0 = System.currentTimeMillis()
        val s = Solver(DPLL).solve(NQueens(size).exp)
        val t1 = System.currentTimeMillis()
        println("Elapsed time con size: " + size + " time: " + (t1 - t0) + "ms")
      }
    }
  }
