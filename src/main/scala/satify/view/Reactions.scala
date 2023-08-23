package satify.view

import satify.Main.{Model, view}
import satify.update.Message.{Convert, Solve}
import satify.update.Update.update
import satify.view.Constants.{cnfOutputDialogName, solOutputDialogName}
import satify.view.GUI.{cnfOutputDialog, inputTextArea, solutionOutputDialog}

object Reactions:
  def cnfReaction(model: Model): Unit =
    val newModel: Model = update(model, Convert(inputTextArea.text))
    val newComponents = view(newModel)
    cnfOutputDialog.contents = newComponents.filter(_.name == cnfOutputDialogName).head
    cnfOutputDialog.open()

  def solutionReaction(model: Model): Unit =
    val newModel: Model = update(model, Solve(inputTextArea.text))
    val newComponents = view(newModel)
    solutionOutputDialog.contents = newComponents.filter(_.name == solOutputDialogName).head
    cnfOutputDialog.contents = newComponents.filter(_.name == cnfOutputDialogName).head
    solutionOutputDialog.open()
    cnfOutputDialog.open()
