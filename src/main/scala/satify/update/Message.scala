package satify.update

import satify.model.problems.Problem

import java.io.File

/** Message is a sum type that collects all possible messages taken in input by the Update function. */
enum Message:
  case SolveAll(input: String)
  case Solve(input: String)
  case NextSolution()
  case SolveProblem(problem: Problem)
  case Convert(input: String)
  case ConvertProblem(problem: Problem)
  case Import(file: File)
