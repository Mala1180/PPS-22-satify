package satify.update.solver.dpll.benchmark

import org.scalameter.api.*
import satify.model.problems.{GraphColoring, NQueens, NurseScheduling}
import satify.update.solver.Solver

object SolvingMemoryBenchmark extends Bench.OfflineReport:

  import satify.update.converters.ConverterType.Tseitin
  import satify.update.solver.SolverType.DPLL

  override def measurer = new Measurer.MemoryFootprint

  performance of "N-Queens solving memory" in {
    val sizes: Gen[Int] = Gen.range("size")(2, 5, 1)
    measure method "N-Queens Solving" in {
      using(sizes) in { size =>
        Solver(DPLL, Tseitin).solveAll(NQueens(size).exp, false)
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
        Solver(DPLL, Tseitin).solveAll(GraphColoring(edges, nodes, colors).exp, false)
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
        Solver(DPLL, Tseitin).solveAll(NurseScheduling(nurses, days, shifts).exp, false)
      }
    }
  }
