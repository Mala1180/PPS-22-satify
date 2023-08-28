package satify.problems

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.problems.NQueens
import satify.problems.NQueens.*
import satify.update.converters.TseitinTransformation.tseitin
import satify.model.{CNF, Expression, Solution, Variable}
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
  
  "NQueens 3x3" should "be UNSAT" in {
    val n = 3
    val sol = Solver().solve(NQueens(n).exp)
    sol should matchPattern { case Solution(UNSAT, _) => }
  }

  "NQueens 4x4" should "be SAT" in {
    val n = 4
    val sol = Solver().solve(NQueens(n).exp)
    sol should matchPattern { case Solution(SAT, _) => }
    println("NQueens 4x4")
    printNqueens(n, sol.assignment.get.parModel)
  }

  "NQueens 10x10" should "be SAT" in {
    val oCnf = DimacsCNF.read("src/main/resources/cnf/nqueens10.txt")
    oCnf should matchPattern { case Some(_) => }
    val sol = Solver().dpll(oCnf.get)
    sol should matchPattern { case Solution(SAT, _) => }
    println("NQueens 10x10")
    printNQueensFromDimacs(10, sol.assignment.get.parModel)
  }
