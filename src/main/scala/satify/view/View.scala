package satify.view

import satify.model.{CNF, State}
import satify.view.ComponentUtils.{createButton, createInputTextArea, createNextSection, createOutputTextArea}
import satify.view.Constants.{cnfOutputDialogName, solOutputDialogName}
import satify.view.GUI.{inputTextArea, loadingLabel}

import scala.swing.*

object View:
  def view(model: State): Set[Component] =
    val cnfComponent: FlowPanel = new FlowPanel():
      name = cnfOutputDialogName
      var result: String = "No CNF"
      if model.cnf.isDefined then result = model.cnf.get.printAsFormal()
      contents += new ScrollPane(createOutputTextArea(result, 30, 35))

    val solutionComponent: FlowPanel = new FlowPanel():
      name = solOutputDialogName
      var result: String = "No Solution"
      if model.solution.isDefined then result = model.solution.get.print
      contents += new BoxPanel(Orientation.Vertical):
        contents += new ScrollPane(createOutputTextArea(result, 30, 35))
        contents += createNextSection()

    val cnf: Option[CNF] = model.cnf
    //val exp: String = if cnf.isDefined then cnf.get.printAsDSL() else ""
    val expComponent: TextArea = createInputTextArea(s"${if model.expression.isDefined then model.expression.get.printAsFormal(false) else ""}")

    Set(expComponent, cnfComponent, solutionComponent)
