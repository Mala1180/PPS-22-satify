package satify.update

import java.io.File

/** Message is a sum type that collects all possible messages taken in input by the Update function. */
enum Message:
  case Input(char: Char)
  case Solve(input: String)
  case Convert(input: String)
  case Import(file: File)
