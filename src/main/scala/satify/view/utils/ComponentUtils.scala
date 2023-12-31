package satify.view.utils

import satify.model.problems.Problem
import satify.model.{Assignment, State}
import satify.view.Reactions.nextSolutionReaction
import satify.view.components.Components.{enableInteractions, problemOutputDialog, problemParameterPanel}
import satify.view.utils.Constants.*
import satify.view.utils.TextPaneUtils.{textPaneText, updateStyle}
import satify.view.utils.Title.*

import java.awt.{Color, Font, Image, Toolkit}
import java.net.URL
import java.util.concurrent.Executors
import javax.swing.ImageIcon
import scala.swing.*
import scala.swing.event.*

object ComponentUtils:

  /** Creates an image icon from the given path and scales it by the given factor
    * @param path the path of the image
    * @param scaledBy the factor to scale the image by
    * @return the image icon
    */
  def createImage(path: String, scaledBy: Int): ImageIcon =
    val url: URL = getClass.getResource(path)
    val image = ImageIcon(url)
    val screenDimension: Dimension = Toolkit.getDefaultToolkit.getScreenSize
    val width: Int = screenDimension.getWidth.toInt
    val ratio: Double = image.getIconWidth.toDouble / image.getIconHeight.toDouble
    val newWidth: Int = width / scaledBy
    val newHeight: Int = (newWidth / ratio).toInt
    ImageIcon(image.getImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH))

  /** Creates a text pane for the input
    * @return the text pane
    */
  def createInputTextPane(txt: String = ""): TextPane =
    val textPane = new TextPane():
      name = expTextPaneName
      border = Swing.EmptyBorder(inputPadding, inputPadding, inputPadding, inputPadding)
    textPane.text = txt
    textPane.font = Font(fontFamily, Font.PLAIN, 18)
    updateStyle(textPane)
    textPane.reactions += { case ValueChanged(t: TextPane) =>
      val s = t.text
      if s != textPaneText then updateStyle(t)
    }
    textPane

  /** Creates a text field for the input parameter
    * @return the text field
    */
  def createParameterInputText(): TextField =
    new TextField:
      name = parameterInputName
      text = ""
      columns = 5
      border = Swing.EmptyBorder(margin)
      maximumSize = new Dimension(100, 30)

  /** Creates a combo box for the problem selection
    * @return the combo box
    */
  def createProblemComboBox(): ComboBox[String] =
    import Placeholders.*
    import ProblemTitle.*
    new ComboBox(List("No selection", NQueens.title, GraphColoring.title, NurseScheduling.title)):
      listenTo(selection)
      maximumSize = new Dimension(200, 30)
      reactions += { case SelectionChanged(_) =>
        val parameters: Set[Component] = this.item match
          case NQueens.title => Set(createLabelledTextArea(QueensNumbers.placeholder, nQueens, 1, 10))
          case GraphColoring.title =>
            Set(
              createLabelledTextArea(GraphColoringNodes.placeholder, gcNodes, 1, 15),
              createLabelledTextArea(GraphColoringEdges.placeholder, gcEdges, 1, 10),
              createLabelledTextArea(GraphColoringColors.placeholder, gcColors, 1, 10)
            )
          case NurseScheduling.title =>
            Set(
              createLabelledTextArea(NurseSchedulingNurses.placeholder, nsNurses, 1, 10),
              createLabelledTextArea(NurseSchedulingDays.placeholder, nsDays, 1, 10),
              createLabelledTextArea(NurseSchedulingShifts.placeholder, nsShifts, 1, 10)
            )
          case _ => Set()
        problemParameterPanel.contents.clear()
        problemParameterPanel.contents.appendAll(parameters)
        problemParameterPanel.revalidate()
      }

  /** Creates a text area with a label, name, rows and columns
    * @param placeholder the text of the label
    * @param parameter the name of the parameter
    * @param r rows of the text area
    * @param c columns of the text area
    * @return the text area
    */
  def createLabelledTextArea(placeholder: String, parameter: String, r: Int, c: Int): TextArea =
    new TextArea:
      listenTo(this)
      name = parameter
      text = placeholder
      rows = r
      columns = c
      maximumSize = new Dimension(200, 30)
      border = Swing.EmptyBorder(margin)
      foreground = Color.GRAY
      font = Font(fontFamily, Font.ITALIC, 15)
      reactions += {
        case FocusGained(_, _, _) =>
          if text.equals(placeholder) then
            foreground = Color.BLACK
            font = Font(fontFamily, Font.PLAIN, 15)
            text = ""
        case FocusLost(_, _, _) =>
          if text.isEmpty then
            foreground = Color.GRAY
            font = Font(fontFamily, Font.ITALIC, 15)
            text = placeholder
      }

  /** Creates a button with the given text
    * @return the button
    */
  def createButton(text: String, width: Int, height: Int, color: Color = Color.black): Button = new Button(text):
    name = this.text
    font = Font(fontFamily, Font.ITALIC, 20)
    preferredSize = new Dimension(width, height)
    foreground = color

  /** Creates a centered section with a Show button. Used to show a graphical representation for the problem.
    * @param problem the problem
    * @param assignment the assignment to show graphically
    * @return the BoxPanel containing the section
    */
  def createShowSection(problem: Problem, assignment: Assignment): BoxPanel =
    val showProblemButton = createButton(Show.title, 100, 40)
    showProblemButton.reactions += { case ButtonClicked(_) =>
      problemOutputDialog.contents =
        createOutputTextArea(problem.toString(assignment), 20, 50, Font(fontFamily, Font.ITALIC, 22))
      problemOutputDialog.centerOnScreen()
      problemOutputDialog.open()
    }
    createCenteredBox(showProblemButton)

  /** Creates a centered section with a Next button. Used to show the next solution.
    * @param model the model
    * @return the BoxPanel containing the section
    */
  def createNextSection(model: State): BoxPanel =
    val nextSolutionButton = createButton(Next.title, 100, 40)
    nextSolutionButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(enableInteractions())
      Executors.newSingleThreadExecutor().execute(() => nextSolutionReaction())
    }
    createCenteredBox(nextSolutionButton)

  /** Creates a centered section with a component inside.
    * @param component the component to center
    * @return the BoxPanel containing the centered component
    */
  private def createCenteredBox(component: Component): BoxPanel =
    new BoxPanel(Orientation.Horizontal):
      contents += Swing.HGlue
      contents += component
      contents += Swing.HGlue

  /** Creates a dialog to show the output
    * @return the dialog
    */
  def createOutputDialog(dialogTitle: String): Dialog =
    new Dialog:
      modal = true
      title = dialogTitle
      size = new Dimension(windowSize.width / 3, windowSize.height / 3 * 2)
      centerOnScreen()

  /** Creates a text area for the output
    * @param txt text to show in the text area
    * @param r rows
    * @param c columns
    * @return the text area
    */
  def createOutputTextArea(txt: String, r: Int, c: Int, textFont: Font = Font(fontFamily, Font.ITALIC, 18)): TextArea =
    new TextArea:
      text = txt
      rows = r
      columns = c
      border = Swing.EmptyBorder(margin)
      editable = false
      font = textFont

  /** Creates a dialog to show the help
    * @return the dialog
    */
  def createHelpDialog(): Dialog =
    val helpBox = new BoxPanel(Orientation.Horizontal):
      border = Swing.EmptyBorder(helpDialogMargin, helpDialogMargin, helpDialogMargin, helpDialogMargin)
      xLayoutAlignment = 0.5
      yLayoutAlignment = 0.5
      contents += new BoxPanel(Orientation.Horizontal):
        contents += new BoxPanel(Orientation.Vertical):
          contents += createLabel("Binary Operators:", 20, Font.BOLD)
          contents += Swing.VStrut(10)
          contents += createLabel("  - AND:                and | /\\ ", helpFontSize)
          contents += createLabel("  - OR:                     or | \\/ ", helpFontSize)
          contents += createLabel("  - XOR:                 xor | ^ ", helpFontSize)
          contents += createLabel("  - IMPLICATION:      implies | -> ", helpFontSize)
          contents += createLabel("  - DOUBLE IMPL.:     iff | <-> ", helpFontSize)
          contents += Swing.VStrut(20)
          contents += createLabel("Unary Operators:", 20, Font.BOLD)
          contents += Swing.VStrut(10)
          contents += createLabel("  - NOT:                 not | ! ", helpFontSize)
          contents += Swing.VStrut(20)
          contents += createLabel("Encodings:", 20, Font.BOLD)
          contents += Swing.VStrut(10)
          contents += createLabel("  - AT LEAST ONE:  (X1, X2, ...) atLeast 1", helpFontSize)
          contents += createLabel("  - AT LEAST K:        (X1, X2, ...) atLeast three", helpFontSize)
          contents += createLabel("  - AT MOST ONE:   (X1, X2, ...) atMost one", helpFontSize)
          contents += createLabel("  - AT MOST K:         (X1, X2, ...) atMost 3", helpFontSize)
          contents += createLabel("  - EXACTLY K:   (X1, X2, ...) exactly two", helpFontSize)
        contents += Swing.HStrut(50)
        contents += new BoxPanel(Orientation.Vertical):
          contents += createLabel("Grammar:", 20, Font.BOLD)
          contents += Swing.VStrut(10)
          contents += createLabel("  - EXP → EXP BINARY_OP EXP | (EXP BINARY_OP EXP)", helpFontSize)
          contents += createLabel("  - EXP → not(EXP)", helpFontSize)
          contents += createLabel("  - EXP → !(EXP) | !EXP", helpFontSize)
          contents += createLabel("  - EXP → (EXP)", helpFontSize)
          contents += createLabel("  - EXP → var", helpFontSize)
          contents += createLabel(
            "  - BINARY_OP → and | or | xor | implies | iff | /\\ | \\/ | ^ | -> | <-> ",
            helpFontSize
          )
          contents += createLabel("  - EXP → (VARS) ENCODING NUM", helpFontSize)
          contents += createLabel("  - ENCODING → atLeast | atMost | exactly", helpFontSize)
          contents += createLabel("  - VARS → var, VARS | var", helpFontSize)
          contents += createLabel(
            "  - NUM → one | two | three | four | five | six | seven | eight | nine | ten",
            helpFontSize
          )
          contents += createLabel("  - NUM → 1 | 2 | 3 ... (no limit)", helpFontSize)
          contents += Swing.VStrut(100)

    new Dialog:
      contents = helpBox
      modal = true
      title = Help.title
      centerOnScreen()
      pack()

  /** Creates a dialog to show a message
    * @return the dialog
    */
  def createDialog(dialogTitle: String, description: String): Dialog =
    val box = new BoxPanel(Orientation.Vertical):
      contents += Swing.VStrut(30)
      contents += new BoxPanel(Orientation.Horizontal):
        contents += Swing.HGlue
        contents += createLabel(dialogTitle.toUpperCase(), 20)
        contents += Swing.HGlue
      contents += Swing.VStrut(30)
      contents += new BoxPanel(Orientation.Horizontal):
        contents += Swing.HGlue
        contents += createLabel(description, 18)
        contents += Swing.HGlue
      contents += Swing.VGlue
    new Dialog:
      contents = box
      modal = true
      title = dialogTitle
      size = new Dimension(windowSize.width / 4, windowSize.height / 4)
      centerOnScreen()

  /** Creates a dialog to show the error
    * @return the dialog
    */
  def createErrorDialog(description: String): Dialog =
    createDialog(ErrorDialog.title, description)

  /** Creates a label with the given text and font size
    * @param txt text to show in the label
    * @param fontSize font size
    * @return the label
    */
  def createLabel(txt: String, fontSize: Int, fontStyle: Int = Font.PLAIN): Label =
    new Label(txt):
      font = Font(fontFamily, fontStyle, fontSize)
