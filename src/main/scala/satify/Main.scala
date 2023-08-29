package satify

import satify.Architecture.MVU
import satify.Main.model
import satify.view.Constants.windowSize
import satify.view.GUI.*
import satify.view.Reactions.{cnfReaction, helpReaction, importReaction, solutionReaction}

import scala.swing.event.ButtonClicked
import scala.swing.{Dimension, FileChooser, MainFrame}

/** Entry point of the application. */
object Main extends App with MVU:
  new MainFrame:
    title = "Satify SAT Solver"

    solveButton.reactions += { case ButtonClicked(_) => solutionReaction(model) }
    cnfButton.reactions += { case ButtonClicked(_) => cnfReaction(model) }

    helpMenuItem.reactions += { case ButtonClicked(_) =>
      helpReaction(model)
    }

    importMenuItem.reactions += { case ButtonClicked(_) =>
      val result = fileChooser.showOpenDialog(null)
      if result == FileChooser.Result.Approve then importReaction(model)
    }

    contents = createBaseGUI()
    size = new Dimension(windowSize.width / 2, windowSize.height / 4 * 3)
    centerOnScreen()
    open()
