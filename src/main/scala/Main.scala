import Architecture.MVU
import model.Model
import view.GUI.render

object Main extends App with MVU:
  render(view(model))
