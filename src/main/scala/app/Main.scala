package app

import app.Architecture.MVU
import view.GUI.render
import app.Main.{model, view}

/** Entry point of the application. */
object Main extends App with MVU:
  render(view(model))
