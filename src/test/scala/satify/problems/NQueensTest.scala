package satify.problems

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.Result.*
import satify.model.Solution
import satify.model.problems.NQueens
import satify.update.parser.DimacsCNF
import satify.update.solver.SolverType.DPLL
import satify.update.solver.{Solver, SolverType}

class NQueensTest extends AnyFlatSpec with Matchers:

  "NQueens 3x3" should "be UNSAT" in {
    val problem = NQueens(3)
    val sol = Solver(DPLL).solve(problem.exp)
    sol should matchPattern { case Solution(UNSAT, _, _) => }
  }

  "NQueens 4x4" should "be SAT" in {
    val problem = NQueens(4)
    val sol = Solver(DPLL).solve(problem.exp)
    sol should matchPattern { case Solution(SAT, _, _) => }
<<<<<<< HEAD
    println("NQueens 4x4")
    problem.printNqueens(sol.assignments.head)
  }

  "NQueens 10x10" should "be SAT" in {
    val oCnf = DimacsCNF.read("src/main/resources/cnf/nqueens10.txt")
    oCnf should matchPattern { case Some(_) => }
    val sol = Solver(DPLL).solve(oCnf.get)
    sol should matchPattern { case Solution(SAT, _, _) => }
    println("NQueens 10x10")
    printNQueensFromDimacs(10, sol.assignments.head)
  }

/*
  "NQueens 15x15" should "be SAT" in {
    val oCnf = DimacsCNF.read("src/main/resources/cnf/nqueens20.txt")
    oCnf should matchPattern { case Some(_) => }
    val sol = Solver(DPLL).solve(oCnf.get)
    sol should matchPattern { case Solution(SAT, _) => }
    println("NQueens 15x15")
    printNQueensFromDimacs(15, sol.assignment.head)
  }
 */
=======
    problem.toString(sol.assignment.head)
  }
>>>>>>> develop
