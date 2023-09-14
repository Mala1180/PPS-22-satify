package satify.view

import satify.model.cnf.CNF
import satify.model.Status.*
import satify.model.errors.Error
import satify.model.errors.Error.*
import satify.model.{Solution, State}
import satify.update.parser.DimacsCNF
import satify.view.ComponentUtils.*
import satify.view.Constants.{cnfOutputDialogName, solOutputDialogName}
import satify.view.GUI.{cnfOutputDialog, createExportFileChooser, exportFileChooser, problemParameterPanel}

import java.awt.Color
import scala.swing.*

object View:
  def view(model: State): Set[Component] =
    import model.*
    if error.isDefined then updateError(error.get, input.getOrElse(""))
    else updateExpression(input.getOrElse("")) ++ updateCnf(cnf, time) ++ updateSolution(model, solution)

  /** Update the solution components
    * @param model the current state
    * @param oSol the (optional) solution to show
    * @return a set of components to add to the GUI
    */
  private def updateSolution(model: State, oSol: Option[Solution]): Set[Component] =
    oSol match
      case Some(sol) =>
        val fp: FlowPanel = new FlowPanel():
          name = solOutputDialogName
          contents += new BoxPanel(Orientation.Vertical):
            contents += new ScrollPane(createOutputTextArea(sol.print, 30, 35))
            sol.status match
              case PARTIAL => contents += createNextSection(model)
              case _ =>
            contents += new FlowPanel():
              contents += createLabel(model.time.get.toString + "ms", 15)
              contents += Swing.VStrut(5)
        Set(fp)
      case None => Set()

  /** Update the CNF text area
    *
    * @param cnf the CNF to show in new components
    * @return a set of components to add to the GUI
    */
  private def updateCnf(cnf: Option[CNF], time: Option[Long]): Set[Component] =
    if cnf.isDefined && time.isDefined then
      val exportButton = createButton("Export CNF", 130, 50, Color.BLUE)
      exportButton.reactions += { case _: event.ButtonClicked =>
        exportFileChooser.showOpenDialog(cnfOutputDialog) match
          case FileChooser.Result.Approve =>
            DimacsCNF.write(exportFileChooser.selectedFile.getPath, cnf.get)
            exportButton.text = "Exported"
            exportButton.enabled = false
          case _ => createErrorDialog("Export error, select a txt file to export the CNF").open()
      }
      val fp: BoxPanel = new BoxPanel(Orientation.Vertical):
        name = cnfOutputDialogName
        contents += new ScrollPane(createOutputTextArea(cnf.get.printAsFormal(), 30, 35))
        contents += new FlowPanel():
          contents += exportButton
        contents += new FlowPanel():
          contents += createLabel(time.get.toString + "ms", 15)
          contents += Swing.VStrut(5)
      Set(fp)
    else Set()

  /** Update the expression text area
    *
    * @param exp the exp to show in new component
    * @return a set of components to add to the GUI
    */
  private def updateExpression(exp: String): Set[Component] = Set(createInputTextArea(exp))

  /** Update the error and show it in a dialog
    *
    * @param error the error to show in a popup dialog
    * @param input the input to show in new component
    * @return a set of components to add to the GUI
    */
  private def updateError(error: Error, input: String): Set[Component] =
    var errorDialog: Dialog = null
    error match
      case InvalidInput => errorDialog = createErrorDialog("Invalid input")
      case InvalidImport =>
        errorDialog = createErrorDialog("Import error, select a txt file containing a valid DIMACS CNF")
      case EmptySolution => errorDialog = createErrorDialog("Empty solution, no next assignment to show")
      case Unknown => errorDialog = createErrorDialog("Unknown error occurred")
    errorDialog.open()
    Set(createInputTextArea(input))
