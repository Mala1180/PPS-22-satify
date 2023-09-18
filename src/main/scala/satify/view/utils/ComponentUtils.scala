package satify.view.utils

import satify.model.State
import satify.view.Constants.*
import satify.view.GUI
import satify.view.GUI.{enableInteractions, problemParameterPanel}
import satify.view.Reactions.nextSolutionReaction
import satify.view.utils.TextPaneUtils.{textPaneText, updateStyle}

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
    textPane.text = txt
    textPane.font = Font(fontFamily, Font.PLAIN, 18)
    updateStyle(textPane)
    textPane.reactions += { case ValueChanged(t: TextPane) =>
      val s = t.text
      if s != textPaneText then updateStyle(t)
    }
    textPane

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
    new ComboBox(List("No selection", "N-Queens", "Graph Coloring", "Nurse Scheduling")):
      listenTo(selection)
      maximumSize = new Dimension(200, 30)
      reactions += { case SelectionChanged(_) =>
        val parameters: Set[Component] = this.item match
          case "N-Queens" => Set(createLabelledTextArea("N. queens", nqQueens, 1, 10))
          case "Graph Coloring" =>
            Set(
              createLabelledTextArea("Nodes: n1, n2, n3, ...", gcNodes, 1, 15),
              createLabelledTextArea("Edges: n1-n2, n2-n3, ...", gcEdges, 1, 10),
              createLabelledTextArea("N. colors", gcColors, 1, 10)
            )
          case "Nurse Scheduling" =>
            Set(
              createLabelledTextArea("N. nurses", nsNurses, 1, 10),
              createLabelledTextArea("Days", nsDays, 1, 10),
              createLabelledTextArea("Shifts", nsShifts, 1, 10)
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
    font = Font(fontFamily, Font.ITALIC, 20)
    preferredSize = new Dimension(width, height)
    foreground = color

  def createNextSection(model: State): BoxPanel =
    val nextSolutionButton = createButton("Next", 100, 40)
    nextSolutionButton.reactions += { case ButtonClicked(_) =>
      Swing.onEDT(enableInteractions())
      Executors.newSingleThreadExecutor().execute(() => nextSolutionReaction(model))
    }
    new BoxPanel(Orientation.Horizontal):
      contents += Swing.HGlue
      contents += nextSolutionButton
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
  def createOutputTextArea(txt: String, r: Int, c: Int): TextArea =
    new TextArea:
      text = txt
      rows = r
      columns = c
      border = Swing.EmptyBorder(margin)
      editable = false
      font = Font(fontFamily, Font.ITALIC, 18)

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
      title = "Help"
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
    createDialog("Error", description)

  /** Creates a label with the given text and font size
    * @param txt text to show in the label
    * @param fontSize font size
    * @return the label
    */
  def createLabel(txt: String, fontSize: Int, fontStyle: Int = Font.PLAIN): Label =
    new Label(txt):
      font = Font(fontFamily, fontStyle, fontSize)
