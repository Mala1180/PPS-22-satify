package satify.problems

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Result.*
import satify.model.Solution
import satify.model.problems.NQueens
import satify.update.solver.SolverType.DPLL
import satify.update.solver.{Solver, SolverType}

class NQueensTest extends AnyFlatSpec with Matchers:

  "NQueens 3x3" should "be UNSAT" in {
    val n = 3
    val sol = Solver(DPLL).solve(NQueens(n).exp)
    sol should matchPattern { case Solution(UNSAT, _) => }
  }

  "NQueens 4x4" should "be SAT" in {
    val n = 4
    val sol = Solver(DPLL).solve(NQueens(n).exp)
    sol should matchPattern { case Solution(SAT, _) => }
    println("NQueens 4x4")
    // printNqueens(n, sol.assignment.get.parModel)
  }

//  "NQueens 10x10" should "be SAT" in {
//    val oCnf = DimacsCNF.read("src/main/resources/cnf/nqueens10.txt")
//    oCnf should matchPattern { case Some(_) => }
//    val sol = Solver().dpll(oCnf.get)
//    sol should matchPattern { case Solution(SAT, _) => }
//    println("NQueens 10x10")
//    printNQueensFromDimacs(10, sol.assignment.get.parModel)
//  }
