package satify.update

import satify.model.problems.ProblemChoice

import java.io.File

/** Message is a sum type that collects all possible messages taken in input by the Update function. */
enum Message:
  case Input(char: Char)
  case Solve(input: String)
  case SolveProblem(problem: ProblemChoice, parameter: Int)
  case Convert(input: String)
  case Import(file: File)
  case NextSolution
