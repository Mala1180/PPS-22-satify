package satify

import satify.Architecture.MVU
import satify.Main.{model, view}
import satify.view.GUI.render

/** Entry point of the application. */
object Main extends App with MVU:
  render(view(model))
