package satify

import satify.Architecture.MVU
import satify.Main.model
import satify.view.ComponentUtils.createLabelledTextArea
import satify.view.Constants.windowSize
import satify.view.GUI.*
import satify.view.Reactions.*

import java.util.concurrent.Executors
import scala.swing.event.{ButtonClicked, SelectionChanged}
import scala.swing.{Component, Dimension, FileChooser, MainFrame, Swing}

/** Entry point of the application. */
object Main extends App with MVU:
  new MainFrame:
    title = "Satify SAT Solver"

    solveAllButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(disableInteractions())
      Executors.newSingleThreadExecutor().execute(() => allSolutionsReaction(model))
    }

    solveButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(disableInteractions())
      Executors.newSingleThreadExecutor().execute(() => solutionReaction(model))
    }
    cnfButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(disableInteractions())
      Executors.newSingleThreadExecutor().execute(() => cnfReaction(model))
    }

    solveProblemButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(disableInteractions())
      Executors.newSingleThreadExecutor().execute(() => problemSolutionReaction(model))
    }

    helpMenuItem.reactions += { case ButtonClicked(_) =>
      helpReaction()
    }

    importMenuItem.reactions += { case ButtonClicked(_) =>
      val result = importFileChooser.showOpenDialog(null)
      if result == FileChooser.Result.Approve then
        Swing.onEDT(disableInteractions())
        Executors.newSingleThreadExecutor().execute(() => importReaction(model))
    }

    contents = createBaseGUI()
    size = new Dimension(windowSize.width / 2, windowSize.height / 3 * 2)
    centerOnScreen()
    open()
