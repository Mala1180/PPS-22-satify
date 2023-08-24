package satify.view

import satify.Main.{Model, view}
import satify.update.Message.{Convert, Import, Solve}
import satify.update.Update.update
import satify.view.Constants.{cnfOutputDialogName, expTextAreaName, solOutputDialogName}
import satify.view.GUI.{cnfOutputDialog, fileChooser, inputScrollPane, inputTextArea, solutionOutputDialog}

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
    val file : File = fileChooser.selectedFile
    val newComponents: Set[Component] = view(update(model, Import(file)))
    //solutionOutputDialog.contents = newComponents.filter(_.name == solOutputDialogName).head
    cnfOutputDialog.contents = newComponents.filter(_.name == cnfOutputDialogName).head
    inputScrollPane.contents = newComponents.filter(_.name == expTextAreaName).head
    //solutionOutputDialog.open()
    cnfOutputDialog.open()

