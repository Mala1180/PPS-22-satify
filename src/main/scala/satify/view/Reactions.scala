package satify.view

import satify.Main.{Model, view}
import satify.model.problems.ProblemChoice
import satify.model.problems.ProblemChoice.*
import satify.update.Message.{Convert, Import, Solve, SolveProblem}
import satify.update.Update.update
import satify.view.Constants.{cnfOutputDialogName, expTextAreaName, solOutputDialogName}
import satify.view.ComponentUtils.createErrorDialog
import satify.view.GUI.{
  cnfOutputDialog,
  fileChooser,
  helpDialog,
  inputScrollPane,
  inputTextArea,
  loadingLabel,
  parameterInputText,
  problemComboBox,
  solutionOutputDialog
}

import java.io.File
import scala.swing.{Component, Swing, TextArea}

object Reactions:
  def cnfReaction(model: Model): Unit =
    updateComponents(view(update(model, Convert(inputTextArea.text))))

  def solutionReaction(model: Model): Unit =
    val input: String = inputScrollPane.contents.head.asInstanceOf[TextArea].text
    println(view(update(model, Solve(input))))
    updateComponents(view(update(model, Solve(input))))

  def importReaction(model: Model): Unit =
    val file: File = fileChooser.selectedFile
    updateComponents(view(update(model, Import(file))))

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

  def helpReaction(model: Model): Unit =
    helpDialog.open()

  private def updateComponents(newComponents: Set[Component]): Unit =
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
    Swing.onEDT(loadingLabel.visible = false)
