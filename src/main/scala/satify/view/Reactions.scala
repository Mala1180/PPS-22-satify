package satify.view

import satify.Main.{Model, view}
import satify.model.problems.ProblemChoice
import satify.model.problems.ProblemChoice.*
import satify.update.Message.{Convert, Import, Solve, SolveProblem}
import satify.update.Update.update
import satify.view.Constants.{cnfOutputDialogName, expTextAreaName, solOutputDialogName}
import satify.view.GUI.{
  cnfOutputDialog,
  fileChooser,
  helpDialog,
  inputScrollPane,
  inputTextArea,
  parameterInputText,
  problemComboBox,
  solutionOutputDialog
}

import java.io.File
import scala.swing.{Component, TextArea}

object Reactions:
  def cnfReaction(model: Model): Unit =
    val newComponents: Set[Component] = view(update(model, Convert(inputTextArea.text)))
    cnfOutputDialog.contents = newComponents.filter(_.name == cnfOutputDialogName).head
    cnfOutputDialog.open()

  def solutionReaction(model: Model): Unit =
    val newComponents: Set[Component] = view(update(model, Solve(inputTextArea.text)))
    solutionOutputDialog.contents = newComponents.filter(_.name == solOutputDialogName).head
    cnfOutputDialog.contents = newComponents.filter(_.name == cnfOutputDialogName).head
    solutionOutputDialog.open()
    cnfOutputDialog.open()

  def importReaction(model: Model): Unit =
    val file: File = fileChooser.selectedFile
    val newComponents: Set[Component] = view(update(model, Import(file)))
    // solutionOutputDialog.contents = newComponents.filter(_.name == solOutputDialogName).head
    cnfOutputDialog.contents = newComponents.filter(_.name == cnfOutputDialogName).head
    inputScrollPane.contents = newComponents.filter(_.name == expTextAreaName).head
    // solutionOutputDialog.open()
    cnfOutputDialog.open()

  def problemSolutionReaction(model: Model): Unit =
    val p : ProblemChoice = problemComboBox.item match
      case "N-Queens" => NQueens
      case "Graph Coloring" => GraphColoring
      case "Nurse Scheduling" => NurseScheduling

    val parameter: Int = parameterInputText.text.toInt
    if p.equals(Nil) || parameter < 0 then println("ERRORE")
    // TODO POPUP ERRORE

    val newComponents: Set[Component] = view(update(model, SolveProblem(p, parameter)))
    solutionOutputDialog.contents = newComponents.filter(_.name == solOutputDialogName).head
    cnfOutputDialog.contents = newComponents.filter(_.name == cnfOutputDialogName).head
    solutionOutputDialog.open()
    cnfOutputDialog.open()

  def helpReaction(model: Model): Unit =
    helpDialog.open()
