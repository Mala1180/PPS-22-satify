package satify.view

import satify.Main.{Model, view}
import satify.model.problems.ProblemChoice
import satify.model.problems.ProblemChoice.*
import satify.update.Message.*
import satify.update.Update.update
import satify.view.ComponentUtils.createErrorDialog
import satify.view.Constants.{cnfOutputDialogName, expTextAreaName, solOutputDialogName}
import satify.view.GUI.*

import java.io.File
import scala.swing.{Component, Swing}

object Reactions:

  /** Reaction to the solve button
    * @param model the current model to update
    */
  def solutionReaction(model: Model): Unit =
    updateComponents(view(update(model, Solve(inputTextArea.text))))

  /** Reaction to the convert button
    * @param model the current model to update
    */
  def cnfReaction(model: Model): Unit =
    updateComponents(view(update(model, Convert(inputTextArea.text))))

  /** Reaction to the import button
    * @param model the current model to update
    */
  def importReaction(model: Model): Unit =
    val file: File = fileChooser.selectedFile
    updateComponents(view(update(model, Import(file))))

  /** Reaction to the problem selection, checking also parameter and selection
    * @param model the current model to update
    */
  def problemSolutionReaction(model: Model): Unit =
    if !problemComboBox.item.equals("No selection") && !parameterInputText.text.equals("") && parameterInputText.text
        .forall(_.isDigit)
    then
      val parameter: Int = parameterInputText.text.toInt
      if parameter < 0 then createErrorDialog("Parameter value is not valid").open()
      else
        val p: ProblemChoice = problemComboBox.item match
          case "N-Queens" => NQueens
          case "Graph Coloring" => GraphColoring
          case "Nurse Scheduling" => NurseScheduling
        updateComponents(view(update(model, SolveProblem(p, parameter))))
    else createErrorDialog("Problem selection or parameter are not valid").open()

  /** Reaction to the next solution button
    * @param model the current model to update to display next assignment
    */
  def nextSolutionReaction(model: Model): Unit =
    updateComponents(view(update(model, NextSolution)))

  /** Reaction to the help button to show the help dialog */
  def helpReaction(): Unit =
    helpDialog.open()

  /** Update the GUI only with the specific components
    * @param newComponents the new components to display
    */
  private def updateComponents(newComponents: Set[Component]): Unit =
    Swing.onEDT {
      newComponents.foreach(c => {
        c.name match
          case n if n == solOutputDialogName =>
            solutionOutputDialog.contents = c
            solutionOutputDialog.open()
          case n if n == cnfOutputDialogName =>
            cnfOutputDialog.contents = c
            cnfOutputDialog.open()
          case n if n == expTextAreaName =>
            inputScrollPane.contents = c
      })
      enableInteractions()
    }
