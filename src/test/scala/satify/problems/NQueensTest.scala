package satify.problems

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.update.converters.TseitinTransformation.tseitin
import satify.model.{CNF, Expression, Solution}
import satify.model.Result.*
import satify.model.Expression.*
import satify.model.dpll.PartialModel
import satify.update.Solver
import satify.update.converters.CNFConverter
import satify.update.parser.DimacsCNF

import scala.annotation.tailrec

class NQueensTest extends AnyFlatSpec with Matchers:

  given CNFConverter with
    def convert(exp: Expression): CNF = tseitin(exp)

  @tailrec
  final def printNQueens(n: Int, pm: PartialModel): Unit = {
    val firstN = pm.take(n)
    if firstN.nonEmpty then
      println(
        firstN.foldLeft("")((p, c) =>
          p + (if c.value.isDefined then if c.value.get then s" ♕ " else " · "
               else " ")
        )
      )
      printNQueens(n, pm.drop(n))
  }

  "NQueens 3x3" should "be UNSAT" in {
    val oCnf = DimacsCNF.read("src/main/resources/cnf/nqueens3.txt")
    oCnf should matchPattern { case Some(_) => }
    Solver().dpll(oCnf.get) should matchPattern { case Solution(UNSAT, _) => }
  }

  "NQueens 4x4" should "be SAT" in {
    val n = 4
    // TO FIX
    val sol1 = Solver().solve(NQueens(n).exp)
    val oCnf = DimacsCNF.read("src/main/resources/cnf/nqueens4.txt")
    oCnf should matchPattern { case Some(_) => }
    // println(oCnf.get)
    val sol = Solver().dpll(oCnf.get)
    sol should matchPattern { case Solution(SAT, _) => }
    println("NQueens 4x4")
    printNQueens(n, sol.assignment.get.parModel)
  }
