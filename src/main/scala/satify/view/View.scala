package satify.view

import satify.model.Status.*
import satify.model.cnf.CNF
import satify.model.errors.Error
import satify.model.errors.Error.*
import satify.model.problems.NQueens
import satify.model.{Assignment, Result, Solution, State}
import satify.update.parser.DimacsCNF
import satify.view.Constants.{cnfOutputDialogName, problemOutputDialogName, solOutputDialogName}
import satify.view.GUI.{cnfOutputDialog, createExportFileChooser, exportFileChooser, problemParameterPanel}
import satify.view.utils.ComponentUtils.*
import satify.view.utils.Title.*

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
            if model.problem.isDefined && model.solution.get.result == Result.SAT then
              contents += createShowSection(model.problem.get, model.solution.get.assignments.head)
            sol.status match
              case PARTIAL => contents += createNextSection(model)
              case _ =>
            contents += new FlowPanel():
              if model.time.get > 1_000_000 then
                contents += createLabel((model.time.get / 1_000_000).toString + "ms", 15)
              else contents += createLabel(model.time.get.toString + "ns", 15)
              contents += Swing.VStrut(5)
        Set(fp)
      case None => Set()

  /** Update the CNF text area
    * @param cnf the CNF to show in new components
    * @return a set of components to add to the GUI
    */
  private def updateCnf(cnf: Option[CNF], time: Option[Long]): Set[Component] =
    if cnf.isDefined && time.isDefined then
      val exportButton = createButton(Export.title, 130, 50, Color.BLUE)
      exportButton.reactions += { case _: event.ButtonClicked =>
        exportFileChooser.showOpenDialog(cnfOutputDialog) match
          case FileChooser.Result.Approve =>
            DimacsCNF.write(exportFileChooser.selectedFile.getPath, cnf.get)
            exportButton.text = Exported.title
            exportButton.enabled = false
          case _ => createErrorDialog(InvalidExport.description).open()
      }
      val fp: BoxPanel = new BoxPanel(Orientation.Vertical):
        name = cnfOutputDialogName
        contents += new ScrollPane(createOutputTextArea(cnf.get.printAsFormal(), 30, 35))
        contents += new FlowPanel():
          contents += exportButton
        contents += new FlowPanel():
          if time.get > 1_000_000 then contents += createLabel((time.get / 1_000_000).toString + "ms", 15)
          else contents += createLabel(time.get.toString + "ns", 15)
          contents += Swing.VStrut(5)
      Set(fp)
    else Set()

  /** Update the expression text area
    * @param exp the exp to show in new component
    * @return a set of components to add to the GUI
    */
  private def updateExpression(exp: String): Set[Component] = Set(createInputTextPane(exp))

  /** Update the error and show it in a dialog
    * @param error the error to show in a popup dialog
    * @param input the input to show in new component
    * @return a set of components to add to the GUI
    */
  private def updateError(error: Error, input: String): Set[Component] =
    createErrorDialog(error.description).open()
    Set(createInputTextPane(input))
