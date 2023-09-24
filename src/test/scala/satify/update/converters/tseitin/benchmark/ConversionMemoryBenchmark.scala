package satify.update.converters.tseitin.benchmark

import org.scalameter.api.*
import satify.model.problems.{NQueens, GraphColoring, NurseScheduling}
import satify.update.converters.Converter
import satify.update.converters.ConverterType.Tseitin

object ConversionMemoryBenchmark extends Bench.OfflineReport:
  override def measurer = new Measurer.MemoryFootprint

  performance of "N-Queens conversion memory" in {
    val sizes: Gen[Int] = Gen.range("size")(2, 10, 1)
    measure method "N-Queens Conversion" in {
      using(sizes) in { size =>
        val cnf = Converter(Tseitin).convert(NQueens(size).exp, false)
      }
    }
  }

  performance of "Graph-Coloring conversion memory" in {
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

  performance of "Nurse-Scheduling conversion memory" in {
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
