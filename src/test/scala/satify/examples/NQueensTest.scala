package satify.examples

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.update.converters.TseitinTransformation.tseitin
import satify.model.{CNF, Expression, Solution}
import satify.model.Result.*
import satify.model.Expression.*
import satify.model.dpll.PartialModel
import satify.update.Solver
import satify.update.converters.CNFConverter

import scala.annotation.tailrec

class NQueensTest extends AnyFlatSpec with Matchers:

  given CNFConverter with
    def convert(exp: Expression): CNF = tseitin(exp)

  @tailrec
  private def printNQueens(n: Int, pm: PartialModel): Unit = {
    val firstN = pm.take(n)
    if firstN.nonEmpty then
      println(
        firstN.foldLeft("")((p, c) =>
          p + (if c.value.isDefined then if c.value.get then s" x " else " · "
               else " ")
        )
      )
      printNQueens(n, pm.drop(n))
  }

  // TODO: tseitin generates wrong CNF
  "NQueens 5x5" should "be SAT" in {
    val n = 5
    val sol = Solver().solve(NQueens(n).exp)
    sol should matchPattern { case Solution(SAT, _) => }
    printNQueens(n, sol.assignment.get.parModel)
  }
