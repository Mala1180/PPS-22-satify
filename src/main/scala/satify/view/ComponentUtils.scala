package satify.view

import satify.view.Constants.*

import java.awt.{Color, Font, Image, Toolkit}
import java.net.{URI, URL}
import javax.swing.ImageIcon
import scala.swing.*

object ComponentUtils:

  /** Creates an image icon from the given path and scales it by the given factor
    *
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
    *
    * @return the text area
    */
  def createInputTextArea(txt: String = ""): TextArea =
    new TextArea:
      name = expTextAreaName
      text = txt
      rows = 22
      columns = 45
      border = Swing.EmptyBorder(margin)

  /** Creates a combo box for the problem selection
    *
    * @param inputTextArea the text area to fill with the problem selected
    * @return the combo box
    */
  def createProblemComboBox(inputTextArea: TextArea): ComboBox[String] =
    new ComboBox(List("No selection", "N-Queens", "Graph Coloring", "Nurse Scheduling")):
      selection.reactions += { case event.SelectionChanged(_) => inputTextArea.text = selection.item }

  /** Creates a button with the given text
    *
    * @return the button
    */
  def createButton(text: String, width: Int, height: Int): Button = new Button(text):
    font = Font(fontFamily, Font.ITALIC, 20)
    preferredSize = new Dimension(width, height)
    background = Color.green
    foreground = Color(200, 0, 0)

  /** Creates a dialog to show the output
    *
    * @return the dialog
    */
  def createOutputDialog(dialogTitle: String): Dialog =
    new Dialog:
      modal = true
      title = dialogTitle
      // size of the main frame based on the screen size
      size = new Dimension(windowSize.width / 3, windowSize.height / 4 * 2)
      centerOnScreen()

  def createOutputTextArea(txt: String, r: Int, c: Int): TextArea =
    new TextArea:
      text = txt
      rows = r
      columns = c
      border = Swing.EmptyBorder(margin)
      editable = false
      font = Font(fontFamily, Font.ITALIC, 18)


  def createHelpDialog(): Dialog =
    new Dialog:
      modal = true
      title = "Help"
      size = new Dimension(windowSize.width / 3, windowSize.height / 4 * 2)
      centerOnScreen()
/*      contents = new BoxPanel(Orientation.Vertical):
        contents += new Label("DSL Explanation"):
          font = Font(fontFamily, Font.ITALIC, 20)
        contents += new Label("The DSL is composed of 3 main parts:")
          font = Font(fontFamily, Font.ITALIC, 18)*/

