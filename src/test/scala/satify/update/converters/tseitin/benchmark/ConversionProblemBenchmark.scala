package satify.update.converters.tseitin.benchmark

import org.scalameter.api.*
import satify.model.problems.{GraphColoring, NQueens, NurseScheduling}
import satify.update.converters.Converter
import satify.update.converters.ConverterType.Tseitin

object ConversionProblemBenchmark extends Bench.OfflineReport:
  import satify.update.converters.ConverterType.Tseitin

  /** Benchmark for NQueens creation and conversion time */
  performance of "N-Queens conversion time" in {
    val sizes: Gen[Int] = Gen.range("size")(1, 10, 1)
    measure method "N-Queens Conversion" in {
      using(sizes) in { size =>
        val cnf = Converter(Tseitin).convert(NQueens(size).exp, false)
      }
    }
  }

  /** Benchmark for GraphColoring creation and conversion time */
  performance of "Graph-Coloring conversion time" in {
    val nodes = List("n1", "n2", "n3")
    val edges = List(("n1", "n2"), ("n2", "n3"))
    val colors = 2
    val sizes: Gen[Int] = Gen.range("size")(0, 1, 1)
    measure method "Graph-Coloring Conversion" in {
      using(sizes) in { size =>
        val cnf = Converter(Tseitin).convert(GraphColoring(edges, nodes, colors).exp, false)
      }
    }
  }

  /** Benchmark for NurseScheduling creation and conversion time */
  performance of "Nurse-Scheduling conversion time" in {
    val nurses = 3
    val days = 1
    val shifts = 3
    val sizes: Gen[Int] = Gen.range("size")(0, 1, 1)
    measure method "Nurse-Scheduling Conversion" in {
      using(sizes) in { size =>
        val cnf = Converter(Tseitin).convert(NurseScheduling(nurses, days, shifts).exp, false)
      }
    }
  }
