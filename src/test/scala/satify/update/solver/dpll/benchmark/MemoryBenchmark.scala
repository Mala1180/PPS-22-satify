package satify.update.solver.dpll.benchmark

import org.scalameter.api.*
import satify.model.problems.NQueens
import satify.update.solver.Solver
import satify.update.solver.SolverType.*

object MemoryBenchmark extends Bench.OfflineReport:
  val sizes: Gen[Int] = Gen.range("size")(2, 5, 1)

  override def measurer = new Measurer.MemoryFootprint

  /** Benchmark for NQueens creation and solving memory footprint */
  performance of "NQueens memory" in {
    measure method "NQueens" in {
      using(sizes) in { size =>
        Solver(DPLL).solve(NQueens(size).exp)
      }
    }
  }
