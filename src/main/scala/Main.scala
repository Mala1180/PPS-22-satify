import Architecture.MVU
import view.GUI.render

/** Entry point of the application. */
object Main extends App with MVU:
  render(view(model))
