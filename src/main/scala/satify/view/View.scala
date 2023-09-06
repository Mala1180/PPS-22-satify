package satify.view

import satify.model.errors.Error
import satify.model.errors.Error.*
import satify.model.{CNF, Solution, State}
import satify.view.ComponentUtils.{createErrorDialog, createInputTextArea, createNextSection, createOutputTextArea}
import satify.view.Constants.{cnfOutputDialogName, solOutputDialogName}

import scala.swing.*

object View:
  def view(model: State): Set[Component] =
    import model.*
    if error.isDefined then updateError(error.get, input.getOrElse(""))
    else updateExpression(input.getOrElse("")) ++ updateCnf(cnf) ++ updateSolution(model, solution)

  /** Update the solution components
    * @param model the current state
    * @param sol the solution to show
    * @return a set of components to add to the GUI
    */
  private def updateSolution(model: State, sol: Option[Solution]): Set[Component] =
    if sol.isDefined then
      val fp: FlowPanel = new FlowPanel():
        name = solOutputDialogName
        contents += new BoxPanel(Orientation.Vertical):
          contents += new ScrollPane(createOutputTextArea(sol.get.print, 30, 35))
          contents += createNextSection(model)
      Set(fp)
    else Set()

  /** Update the CNF text area
    * @param cnf the CNF to show in new components
    * @return a set of components to add to the GUI
    */
  private def updateCnf(cnf: Option[CNF]): Set[Component] =
    if cnf.isDefined then
      val fp: FlowPanel = new FlowPanel():
        name = cnfOutputDialogName
        contents += new ScrollPane(createOutputTextArea(cnf.get.printAsFormal(), 30, 35))
      Set(fp)
    else Set()

  /** Update the expression text area
    * @param exp the exp to show in new component
    * @return a set of components to add to the GUI
    */
  private def updateExpression(exp: String): Set[Component] = Set(createInputTextArea(exp))

  /** Update the error and show it in a dialog
    *  @param error the error to show in a popup dialog
    * @param input the input to show in new component
    * @return a set of components to add to the GUI
    */
  private def updateError(error: Error, input: String): Set[Component] =
    var errorDialog: Dialog = null
    error match
      case InvalidInput => errorDialog = createErrorDialog("Invalid input")
      case Unknown => errorDialog = createErrorDialog("Unknown error")
    errorDialog.open()
    Set(createInputTextArea(input))
