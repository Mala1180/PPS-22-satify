package view

import view.Constants.{fontFamily, headingFont, margin, windowSize}

import java.awt.{Color, Font, Image, Toolkit}
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
    val image = ImageIcon(path)
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
  def createInputTextArea(): TextArea =
    new TextArea:
      border = Swing.EmptyBorder(margin)
      // scrollable
      lineWrap = true
      wordWrap = true
      preferredSize = new Dimension(500, 300)

  /** Creates a combo box for the problem selection
    *
    * @param inputTextArea the text area to fill with the problem selected
    * @return the combo box
    */
  def createProblemComboBox(inputTextArea: TextArea): ComboBox[String] =
    new ComboBox(List("No selection", "N-Queens", "Graph Coloring", "Nurse Scheduling")):
      selection.reactions += { case event.SelectionChanged(_) => inputTextArea.text = selection.item }

  /** Creates a button to solve the problem
    *
    * @param gui the gui
    * @return the button
    */
  def createSolveButton(gui: GUI): Button = new Button("Solve"):
    font = Font(fontFamily, Font.ITALIC, 20)
    preferredSize = new Dimension(100, 40)
    background = Color.green
    foreground = Color(200, 0, 0)

  /** Creates a dialog to show the output
    *
    * @return the dialog
    */
  def createOutputDialog(): Dialog =
    new Dialog:
      modal = true
      val outputTextArea: TextArea = new TextArea:
        editable = false
        border = Swing.EmptyBorder(margin)
        lineWrap = true
        wordWrap = true
        preferredSize = new Dimension((windowSize.width / 3) - margin, (windowSize.height / 4 * 2) - margin)
      contents = new BoxPanel(Orientation.Vertical):
        contents += new FlowPanel():
          contents += new Label("Output:"):
            font = headingFont
        contents += new ScrollPane:
          contents = outputTextArea
      // size of the main frame based on the screen size
      size = new Dimension(windowSize.width / 3, windowSize.height / 4 * 2)
      centerOnScreen()
