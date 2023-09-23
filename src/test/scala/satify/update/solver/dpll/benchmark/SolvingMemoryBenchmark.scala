package satify.update.solver.dpll.benchmark

import org.scalameter.api.*
import satify.model.problems.{NQueens, NurseScheduling, GraphColoring}
import satify.update.solver.Solver
import satify.update.solver.SolverType.*
import satify.update.converters.ConverterType.Tseitin

object SolvingMemoryBenchmark extends Bench.OfflineReport:
  override def measurer = new Measurer.MemoryFootprint

  performance of "N-Queens solving memory" in {
    val sizes: Gen[Int] = Gen.range("size")(2, 5, 1)
    measure method "N-Queens Solving" in {
      using(sizes) in { size =>
        val s = Solver(DPLL, Tseitin).solveAll(NQueens(size).exp, false)
      }
    }
  }

  performance of "Graph-Coloring solving memory" in {
    val nodes = List("n1", "n2", "n3")
    val edges = List(("n1", "n2"), ("n2", "n3"))
    val colors = 2
    val sizes: Gen[Int] = Gen.range("size")(0, 1, 1)
    measure method "Graph-Coloring Solving" in {
      using(sizes) in { size =>
        val s = Solver(DPLL, Tseitin).solveAll(GraphColoring(edges, nodes, colors).exp, false)
      }
    }
  }

  performance of "Nurse-Scheduling solving memory" in {
    val nurses = 3
    val days = 1
    val shifts = 3
    val sizes: Gen[Int] = Gen.range("size")(0, 1, 1)
    measure method "Nurse-Scheduling Solving" in {
      using(sizes) in { size =>
        val s = Solver(DPLL, Tseitin).solveAll(NurseScheduling(nurses, days, shifts).exp, false)
      }
    }
  }
