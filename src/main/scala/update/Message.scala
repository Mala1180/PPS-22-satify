package update

import model.Expression

enum Message:
  case Input(char: Char)
  case Solve(expression: Expression)
  