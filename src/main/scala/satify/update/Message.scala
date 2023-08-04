package satify.update

import satify.model.Expression

/** Message is a sum type that collects all possible messages taken in input by the Update function. */
enum Message:
  case Input(char: Char)
  case Solve(expression: Expression)
  case Convert(expression: Expression)
