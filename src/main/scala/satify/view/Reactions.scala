package satify.view

import satify.app.Main.{Model, model, update, view}
import satify.model.errors.Error.InvalidInput
import satify.model.problems.{GraphColoring, NQueens, NurseScheduling, Problem}
import satify.update.Message
import satify.update.Message.*
import satify.view.components.Components.*
import satify.view.utils.ComponentUtils.createErrorDialog
import satify.view.utils.Constants.*
import satify.view.utils.ProblemTitle.{
  GraphColoring as GUIGraphColoring,
  NQueens as GUINQueens,
  NurseScheduling as GUINurseScheduling
}

import java.io.File
import scala.swing.{Component, Swing, TextArea}

object Reactions:

  private def updateModel(model: Model, message: Message): Model = update(model, message)

  /** Reaction to the solve all button */
  def allSolutionsReaction(): Unit =
    model = updateModel(model, SolveAll(inputTextPane.text))
    updateComponents(view(model))

  /** Reaction to the solve button */
  def solutionReaction(): Unit =
    model = updateModel(model, Solve(inputTextPane.text))
    updateComponents(view(model))

  /** Reaction to the convert button */
  def cnfReaction(): Unit =
    model = updateModel(model, Convert(inputTextPane.text))
    updateComponents(view(model))

  /** Reaction to the import button */
  def importReaction(): Unit =
    val file: File = importFileChooser.selectedFile
    model = updateModel(model, Import(file))
    updateComponents(view(model))

  /** Reaction to the problem selection, checking also parameter and selection */
  def problemSolutionReaction(): Unit =
    val p: Problem = readProblemSelection()
    model = updateModel(model, SolveProblem(p))
    updateComponents(view(model))

  /** Reaction to the problem selection, checking also parameter and selection */
  def problemCnfReaction(): Unit =
    val p: Problem = readProblemSelection()
    model = updateModel(model, ConvertProblem(p))
    updateComponents(view(model))

  /** Reaction to the next solution button */
  def nextSolutionReaction(): Unit =
    model = updateModel(model, NextSolution())
    updateComponents(view(model))

  /** Reaction to the help button to show the help dialog */
  def helpReaction(): Unit = helpDialog.open()

  /** Update the GUI only with the specific components
    * @param newComponents the new components to display
    */
  private def updateComponents(newComponents: Set[Component]): Unit = Swing.onEDT {
    newComponents.foreach(c => {
      c.name match
        case n if n == solOutputDialogName =>
          solutionOutputDialog.contents = c
          solutionOutputDialog.open()
        case n if n == cnfOutputDialogName =>
          cnfOutputDialog.contents = c
          cnfOutputDialog.open()
        case n if n == expTextPaneName =>
          inputScrollPane.contents = c
    })
    enableInteractions()
  }

  /** Read the problem selection from the GUI, checking also the parameters.
    * @return the problem selected
    */
  private def readProblemSelection(): Problem =
    var p: Problem = null
    def checkInt(input: String): Int =
      if !input.equals("") && input.forall(_.isDigit) && input.toInt > 0 then input.toInt
      else throw new IllegalArgumentException(InvalidInput().description)
    def getInput(name: String): TextArea = problemParameterPanel.contents
      .filter(c => c.isInstanceOf[TextArea] && c.name.equals(name))
      .head
      .asInstanceOf[TextArea]
    def checkNodes(input: String): List[String] = input.split(",").map(_.trim).toList
    def checkEdges(input: String): List[(String, String)] =
      input.split(",").map(_.trim).toList.map(_.split("-").map(_.trim).toList).map(l => (l.head, l.last))
    try
      p = problemComboBox.item match
        case GUINQueens.title => NQueens(checkInt(getInput(nQueens).text))
        case GUIGraphColoring.title =>
          GraphColoring(
            checkEdges(getInput(gcEdges).text),
            checkNodes(getInput(gcNodes).text),
            checkInt(getInput(gcColors).text)
          )
        case GUINurseScheduling.title =>
          NurseScheduling(
            checkInt(getInput(nsNurses).text),
            checkInt(getInput(nsDays).text),
            checkInt(getInput(nsShifts).text)
          )
    catch
      case e: Exception =>
        e.printStackTrace()
        createErrorDialog(InvalidInput().description).open()
    p
