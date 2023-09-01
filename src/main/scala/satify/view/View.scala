package satify.view

import satify.model.expression.Expression
import satify.model.{CNF, Solution, State}
import satify.view.ComponentUtils.{createButton, createInputTextArea, createNextSection, createOutputTextArea}
import satify.view.Constants.{cnfOutputDialogName, expTextAreaName, solOutputDialogName}
import satify.view.GUI.{inputTextArea, loadingLabel}

import scala.swing.*

object View:
  def view(model: State): Set[Component] =
    import model.*
    if expression.isEmpty && cnf.isDefined && solution.isEmpty then Set(updateExpression(cnf.get.printAsDSL()))
    else updateExpression(expression) ++ updateCnf(cnf) ++ updateSolution(model, solution)

  /** Update the solution components
    * @param model the current state
    * @param sol the solution to show
    * @return
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
    * @param exp the expression to show in new components
    * @return a set of components to add to the GUI
    */
  private def updateExpression(exp: Option[Expression]): Set[Component] =
    Set(createInputTextArea(s"${if exp.isDefined then exp.get.printAsFormal(false) else ""}"))

  /** Update the expression text area
    * @param exp the component to show
    * @return
    */
  private def updateExpression(exp: String): Component =
    createInputTextArea(exp)
