package satify

import satify.Architecture.MVU
import satify.Main.model
import satify.view.Constants.windowSize
import satify.view.GUI.*
import satify.view.Reactions.{cnfReaction, helpReaction, importReaction, problemSolutionReaction, solutionReaction}

import scala.swing.event.ButtonClicked
import scala.swing.{Dimension, FileChooser, MainFrame, Swing}

/** Entry point of the application. */
object Main extends App with MVU:
  new MainFrame:
    title = "Satify SAT Solver"

    solveButton.reactions += { case ButtonClicked(_) =>
      //loadingLabel.visible = true
      Swing.onEDT(loadingLabel.visible = true)
      new Thread(() => solutionReaction(model)).start()
    }
    cnfButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(loadingLabel.visible = true)
      new Thread(() => cnfReaction(model)).start()
    }

    solveProblemButton.reactions += { case ButtonClicked(_) => problemSolutionReaction(model) }

    helpMenuItem.reactions += { case ButtonClicked(_) =>
      helpReaction(model)
    }

    importMenuItem.reactions += { case ButtonClicked(_) =>
      val result = fileChooser.showOpenDialog(null)
      if result == FileChooser.Result.Approve then
        Swing.onEDT(loadingLabel.visible = true)
        new Thread(() => importReaction(model)).start()
    }

    contents = createBaseGUI()
    size = new Dimension(windowSize.width / 2, windowSize.height / 4 * 3)
    centerOnScreen()
    open()
