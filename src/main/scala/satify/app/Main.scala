package satify.app

import satify.app.Architecture.MVU
import satify.dsl.Reflection.startRepl
import satify.view.components.Components.*
import satify.view.utils.Constants.windowSize
import satify.view.utils.Title.*

import scala.swing.{Dimension, MainFrame}

/** Entry point of the application. */
object Main extends App with MVU:
  startRepl()

  new MainFrame:
    title = App.title
    contents = createBaseGUI()
    size = new Dimension(windowSize.width / 2, windowSize.height / 3 * 2)
    centerOnScreen()
    open()
