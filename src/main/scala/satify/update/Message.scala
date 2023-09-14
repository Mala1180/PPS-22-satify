package satify.update

import satify.model.problems.Problem

import java.io.File

/** Message is a sum type that collects all possible messages taken in input by the Update function. */
enum Message:
  case Input(char: Char)
  case Solve(input: String)
  case SolveProblem(problem: Problem)
  case Convert(input: String)
  case ConvertProblem(problem: Problem)
  case Import(file: File)
  case NextSolution()
