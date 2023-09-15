package satify.view

import satify.model.State
import satify.view.Constants.*
import satify.view.GUI.{
  disableInteractions,
  enableInteractions,
  opAttr,
  problemComboBox,
  problemParameterPanel,
  textPane
}
import satify.view.Reactions.nextSolutionReaction

import java.awt.{Color, Font, Image, Toolkit}
import java.net.URL
import java.util.concurrent.Executors
import javax.swing.{ImageIcon, JTextArea}
import javax.swing.plaf.basic.ComboPopup
import scala.swing.*
import scala.swing.event.{ButtonClicked, FocusGained, FocusLost, SelectionChanged, ValueChanged}

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
    var textPaneText: String = ""

    /** Update the style of a text pane
      * @param t a text pane
      */
    def updateStyle(t: TextPane): Unit =
      val text = t.text
      textPaneText = text

      // Util function to minimize repetitions
      def f(h: Int => Int, from: Int, length: Int): List[Int] =
        val start = h(from)
        if start != -1 then f(h, from + length, length) :+ start
        else Nil

      // Set character attribute foreach offset inside the input list
      def g(l: List[Int], length: Int): Unit = Swing.onEDT {
        l.foreach(i => t.styledDocument.setCharacterAttributes(i, length, opAttr, true))
      }

      // Update all operators style
      g(f(i => text.indexOf("!", i), 0, 1), 1)
      g(f(i => text.indexOf("or", i), 0, 2), 2)
      g(
        f(
          i => {
            val l: List[Int] = (text.indexOf("and", i) :: text.indexOf("not", i) ::
              text.indexOf("xor", i) :: Nil)
              .filter(i => i != -1)
            l match
              case Nil => -1
              case _ =>
                l.min((x, y) =>
                  if x > y then 1
                  else if x < y then -1
                  else 0
                )
          },
          0,
          3
        ),
        3
      )

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
    val tf = new TextField:
      name = parameterInputName
      text = ""
      columns = 5
      border = Swing.EmptyBorder(margin)
      maximumSize = new Dimension(100, 30)
    tf

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
      contents += Swing.HGlue
      contents += new BoxPanel(Orientation.Vertical):
        contents += createLabel("Operators:", 20, Font.BOLD)
        contents += Swing.VStrut(10)
        contents += createLabel("  - AND:                and | /\\ ", 18)
        contents += createLabel("  - OR:                     or | \\/ ", 18)
        contents += createLabel("  - NOT:                 not | ! ", 18)
        contents += createLabel("  - XOR:                 xor | ^ ", 18)
        contents += createLabel("  - IMPLICATION:      implies | -> ", 18)
        contents += createLabel("  - DOUBLE IMPL.:     iff | <-> ", 18)
        contents += Swing.VStrut(20)
        contents += createLabel("Encodings:", 20, Font.BOLD)
        contents += Swing.VStrut(10)
        contents += createLabel("  - AT LEAST ONE:  (X1, X2, ...) atLeast 1", 18)
        contents += createLabel("  - AT LEAST K:        (X1, X2, ...) atLeast three", 18)
        contents += createLabel("  - AT MOST ONE:   (X1, X2, ...) atMost one", 18)
        contents += createLabel("  - AT MOST K:         (X1, X2, ...) atMost 3", 18)
        contents += createLabel("  - EXACTLY ONE:   (X1, X2, ...) exactlyOne", 18)
      contents += Swing.HGlue
    new Dialog:
      contents = helpBox
      modal = true
      title = "Help"
      size = new Dimension(windowSize.width / 3, windowSize.height / 4 * 2)
      centerOnScreen()

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
