package satify.view

import satify.model.{State}
import satify.model.tree.cnf.CNF
import satify.view.ComponentUtils.{createInputTextArea, createOutputTextArea}
import satify.view.Constants.{cnfOutputDialogName, solOutputDialogName}

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
      contents += new ScrollPane(createOutputTextArea(result, 30, 35))

    val cnf: Option[CNF] = model.cnf
    val expComponent: TextArea = createInputTextArea(s"${if cnf.isDefined then cnf.get.printAsDSL() else ""}")

    Set(expComponent, cnfComponent, solutionComponent)
