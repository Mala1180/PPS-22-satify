package satify

import satify.Architecture.MVU
import satify.Main.{model, view}
import satify.view.Constants.windowSize
import satify.view.GUI.{cnfButton, createBaseGUI, solveButton}
import satify.view.Reactions.{cnfReaction, solutionReaction}
import satify.view.View.*

import scala.swing.event.ButtonClicked
import scala.swing.{Dimension, MainFrame}

/** Entry point of the application. */
object Main extends App with MVU:
  new MainFrame:
    title = "Satify SAT Solver"

    solveButton.reactions += { case ButtonClicked(_) => solutionReaction(model) }
    cnfButton.reactions += { case ButtonClicked(_) => cnfReaction(model) }

    contents = createBaseGUI()
    size = new Dimension(windowSize.width / 2, windowSize.height / 3 * 2)
    centerOnScreen()
    open()
