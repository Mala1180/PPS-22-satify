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
    //Qui ho il new model.
    //dobbiamo definire come presentare le cose e cosa far vedere e quando
    //Allora:
    // 1) Solution -> mostro exp e soluzione.
    // 2) CNF -> mostro exp e CNF
    // 3) Import -> mostro CNF e basta
    // 4) Problem -> mostro output solo su OutputDialog inerente al problema, quindi solution e basta.


    //print expression, cnf and solution
    println(s"Expression: ${expression.isDefined}")
    println(s"CNF: ${cnf.isDefined}")
    println(s"Solution: ${solution.isDefined}")

    var s : Set[Component] = Set()
    //Solve
    if expression.isDefined && cnf.isEmpty && solution.isDefined then
      s = Set(updateExpression(expression), updateSolution(solution))

    //CNF
    if expression.isDefined && cnf.isDefined && solution.isEmpty then
      s = Set(updateExpression(expression), updateCnf(cnf))

    //Import
    if expression.isEmpty && cnf.isDefined && solution.isEmpty then
      s = Set(updateExpression(cnf.get.printAsFormal()))

    s
    //Problem selection
    //if expression.isDefined && cnf.isDefined && solution.isDefined then ???
    //print in campo expressoin la print della cnf???


/*
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

    val expComponent: TextArea = createInputTextArea(s"${if model.expression.isDefined then model.expression.get.printAsFormal(false) else ""}")

    Set(expComponent, cnfComponent, solutionComponent)
*/

  private def updateSolution(sol : Option[Solution]) : Component =
    new FlowPanel():
      name = solOutputDialogName
      var result: String = "No Solution"
      if sol.isDefined then result = sol.get.print
      contents += new BoxPanel(Orientation.Vertical):
        contents += new ScrollPane(createOutputTextArea(result, 30, 35))
        contents += createNextSection()

  private def updateCnf(cnf: Option[CNF]) : Component =
    new FlowPanel():
      name = cnfOutputDialogName
      var result: String = "No CNF"
      if cnf.isDefined then result = cnf.get.printAsFormal()
      contents += new ScrollPane(createOutputTextArea(result, 30, 35))

  private def updateExpression(exp: Option[Expression]) : Component =
    createInputTextArea(s"${if exp.isDefined then exp.get.printAsFormal(false) else ""}")

  private def updateExpression(exp: String): Component =
    val txt = createInputTextArea(exp)
    txt.name = expTextAreaName
    txt
