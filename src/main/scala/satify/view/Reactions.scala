package satify.view

import satify.Main.{Model, view}
import satify.model.problems.{GraphColoring, NQueens, NurseScheduling, Problem}
import satify.update.Message.*
import satify.update.Update.update
import satify.view.ComponentUtils.createErrorDialog
import satify.view.Constants.{
  cnfOutputDialogName,
  expTextAreaName,
  gcColors,
  gcEdges,
  gcNodes,
  nqQueens,
  nsDays,
  nsNurses,
  nsShifts,
  solOutputDialogName
}
import satify.view.GUI.*

import java.io.File
import scala.swing.{Component, Swing, TextArea}

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
    if !problemComboBox.item.equals("No selection") then
      val p: Problem = readProblemSelection()
      updateComponents(view(update(model, SolveProblem(p))))
    else createErrorDialog("Not a valid problem selection").open()

  /** Reaction to the next solution button
    * @param model the current model to update to display next assignment
    */
  def nextSolutionReaction(model: Model): Unit =
    updateComponents(view(update(model, NextSolution())))

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

  /** Read the problem selection from the GUI, checking also the parameters.
    *
    * @return the problem selected
    */
  private def readProblemSelection(): Problem =
    var p: Problem = null
    def checkInput(input: String): Int =
      if !input.equals("") && input.forall(_.isDigit) && input.toInt > 0 then input.toInt
      else throw new IllegalArgumentException("Parameter value is not valid")
    def getInput(name: String): TextArea = problemParameterPanel.contents
      .filter(c => c.isInstanceOf[TextArea] && c.name.equals(name))
      .head
      .asInstanceOf[TextArea]
    // TODO: introduce a new def to parse the graph coloring parameters.
    // maybe it is better to introduce one util def in problem trait to parse the parameters
    // following this way every new introduced problem should implement the parse method to parse its own type of parameters
    // from the input to their format. eg. NurseParse -> Int | GCParse -> List[(string, string)], String, Int | NSParse -> Int, Int, Int
    try
      p = problemComboBox.item match
        case "N-Queens" => NQueens(checkInput(getInput(nqQueens).text))
        case "Graph Coloring" =>
          GraphColoring(
            List((getInput(gcNodes).text, getInput(gcNodes).text)),
            List(getInput(gcEdges).text),
            checkInput(getInput(gcColors).text)
          )
        case "Nurse Scheduling" =>
          NurseScheduling(
            checkInput(getInput(nsNurses).text),
            checkInput(getInput(nsDays).text),
            checkInput(getInput(nsShifts).text)
          )
    catch
      case e: Exception =>
        e.printStackTrace()
        createErrorDialog("Input not valid").open()
    p
