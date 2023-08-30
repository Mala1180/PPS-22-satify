package satify.view

import satify.view.Constants.*

import java.awt.{Color, Font, Image, Toolkit}
import java.net.{URI, URL}
import javax.swing.ImageIcon
import scala.swing.*

object ComponentUtils:

  /** Creates an image icon from the given path and scales it by the given factor
    * @param path the path of the image
    * @param scaledBy the factor to scale the image by
    * @return the image icon
    */
  def createImage(path: String, scaledBy: Int): ImageIcon =
    val url: URL = getClass.getResource(path)
    val image = ImageIcon(url)
    // resize the image maintaining the aspect ratio
    val screenDimension: Dimension = Toolkit.getDefaultToolkit.getScreenSize
    val width: Int = screenDimension.getWidth.toInt
    val ratio: Double = image.getIconWidth.toDouble / image.getIconHeight.toDouble
    val newWidth: Int = width / scaledBy
    val newHeight: Int = (newWidth / ratio).toInt
    ImageIcon(image.getImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH))

  /** Creates a text area for the input
    * @return the text area
    */
  def createInputTextArea(txt: String = ""): TextArea =
    new TextArea:
      name = expTextAreaName
      text = txt
      rows = 22
      columns = 45
      border = Swing.EmptyBorder(margin)

  def createParameterInputText(): TextField =
    new TextField:
      name = parameterInputName
      text = ""
      columns = 5
      border = Swing.EmptyBorder(margin)
      maximumSize = new Dimension(100, 30)

  /** Creates a combo box for the problem selection
    * @param inputTextArea the text area to fill with the problem selected
    * @return the combo box
    */
  def createProblemComboBox(inputTextArea: TextArea): ComboBox[String] =
    new ComboBox(List("No selection", "N-Queens", "Graph Coloring", "Nurse Scheduling"))

  /** Creates a button with the given text
    * @return the button
    */
  def createButton(text: String, width: Int, height: Int): Button = new Button(text):
    font = Font(fontFamily, Font.ITALIC, 20)
    preferredSize = new Dimension(width, height)
    background = Color.green
    foreground = Color(200, 0, 0)

  /** Creates a dialog to show the output
    * @return the dialog
    */
  def createOutputDialog(dialogTitle: String): Dialog =
    new Dialog:
      modal = true
      title = dialogTitle
      // size of the main frame based on the screen size
      size = new Dimension(windowSize.width / 3, windowSize.height / 4 * 2)
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
        contents += createLabel("Operators:", 20)
        contents += createLabel("  - AND: and", 18)
        contents += createLabel("  - OR: or", 18)
        contents += createLabel("  - NOT: !", 18)
        contents += createLabel("  - XOR: xor", 18)
        contents += createLabel("  - IMPLICATION: ->", 18)
        contents += createLabel("  - EQUIVALENCE: <->", 18)
        contents += createLabel("  - PARENTHESIS: ()", 18)
        contents += createLabel("  - AT MOST ONE: atMostOne", 18)
        contents += createLabel("  - AT LEAST ONE: atLeastOne", 18)
        contents += createLabel("  - TRUE", 18)
        contents += createLabel("  - FALSE", 18)
      contents += Swing.HGlue
    new Dialog:
      contents = helpBox
      modal = true
      title = "Help"
      size = new Dimension(windowSize.width / 3, windowSize.height / 4 * 2)
      centerOnScreen()

  /** Creates a dialog to show the error
    * @return the dialog
    */
  def createErrorDialog(): Dialog =
    val errorBox = new BoxPanel(Orientation.Horizontal):
      contents += Swing.HGlue
      contents += new BoxPanel(Orientation.Vertical):
        contents += createLabel("ERROR", 20)
      contents += Swing.HGlue
    new Dialog:
      contents = errorBox
      modal = true
      title = "Error"
      size = new Dimension(windowSize.width / 6, windowSize.height / 4)
      centerOnScreen()

  /** Creates a label with the given text and font size
    * @param txt text to show in the label
    * @param fontSize font size
    * @return the label
    */
  def createLabel(txt: String, fontSize: Int): Label =
    new Label(txt):
      font = Font(fontFamily, Font.ITALIC, fontSize)
