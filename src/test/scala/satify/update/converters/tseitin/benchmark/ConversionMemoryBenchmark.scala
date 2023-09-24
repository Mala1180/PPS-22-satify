package satify.update.converters.tseitin.benchmark

import org.scalameter.api.*
import satify.model.problems.{GraphColoring, NQueens, NurseScheduling}
import satify.update.converters.Converter

object ConversionMemoryBenchmark extends Bench.OfflineReport:

  import satify.update.converters.ConverterType.Tseitin

  override def measurer = new Measurer.MemoryFootprint

  performance of "N-Queens conversion memory" in {
    val sizes: Gen[Int] = Gen.range("size")(2, 10, 1)
    measure method "N-Queens Conversion" in {
      using(sizes) in { size =>
        Converter(Tseitin).convert(NQueens(size).exp, false)
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
        Converter(Tseitin).convert(GraphColoring(edges, nodes, colors).exp, false)
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
        Converter(Tseitin).convert(NurseScheduling(nurses, days, shifts).exp, false)
      }
    }
  }
