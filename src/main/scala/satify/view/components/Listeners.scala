package satify.view.components

import satify.view.Reactions.*
import satify.view.components.Components.*

import java.util.concurrent.Executors
import scala.swing.event.ButtonClicked
import scala.swing.{FileChooser, Swing}

object Listeners:

  def createListeners(): Unit =
    solveAllButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(disableInteractions())
      Executors.newSingleThreadExecutor().execute(() => allSolutionsReaction())
    }

    solveButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(disableInteractions())
      Executors.newSingleThreadExecutor().execute(() => solutionReaction())
    }

    solveProblemButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(disableInteractions())
      Executors.newSingleThreadExecutor().execute(() => problemSolutionReaction())
    }

    cnfButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(disableInteractions())
      Executors.newSingleThreadExecutor().execute(() => cnfReaction())
    }

    cnfProblemButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(disableInteractions())
      Executors.newSingleThreadExecutor().execute(() => problemCnfReaction())
    }

    helpMenuItem.reactions += { case ButtonClicked(_) => helpReaction() }

    importMenuItem.reactions += { case ButtonClicked(_) =>
      val result = importFileChooser.showOpenDialog(null)
      if result == FileChooser.Result.Approve then
        Swing.onEDT(disableInteractions())
        Executors.newSingleThreadExecutor().execute(() => importReaction())
    }
