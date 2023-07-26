package view
import model.Model
import view.Utils.*

import java.awt.{Color, Font, Image, Toolkit}
import javax.swing.ImageIcon
import scala.swing.*

/** The GUI for the game */
case class GUI(model: Model)

/** Object containing the functions related to the GUI */
object GUI:

  /** Renders the GUI
    * @param gui the GUI to render
    */
  def render(gui: GUI): Unit =
    new MainFrame:
      title = "Satify SAT Solver"
      // Create a new ImageIcon
      val logoIcon: ImageIcon = createImage(logoPath, 5)
      // Create a Label and set its icon with margin
      val logoLabel: Label = new Label:
        icon = logoIcon
        border = Swing.EmptyBorder(margin)

      val inputTextArea: TextArea = createInputTextArea()
      val problemComboBox: ComboBox[String] = createProblemComboBox(inputTextArea)
      val outputDialog: Dialog = createOutputDialog()
      val solveButton: Button = createSolveButton(outputDialog)

      contents = new BoxPanel(Orientation.Vertical):
        contents += new FlowPanel():
          contents += logoLabel
        contents += new FlowPanel():
          contents += new BoxPanel(Orientation.Vertical):
            contents += new FlowPanel():
              contents += new Label("Input:"):
                font = headingFont
            contents += new ScrollPane:
              contents = inputTextArea
          contents += new BoxPanel(Orientation.Vertical):
            contents += new FlowPanel():
              contents += new Label("Fill with problem:"):
                font = headingFont
            contents += problemComboBox
        contents += new FlowPanel():
          contents += solveButton

      // size of the main frame based on the screen size
      size = new Dimension(windowSize.width / 2, windowSize.height / 3 * 2)
      centerOnScreen()
      open()

  private def createImage(path: String, scaledBy: Int): ImageIcon =
    val image = ImageIcon(path)
    // resize the image maintaining the aspect ratio
    val screenDimension: Dimension = Toolkit.getDefaultToolkit.getScreenSize
    val width: Int = screenDimension.getWidth.toInt
    val ratio: Double = image.getIconWidth.toDouble / image.getIconHeight.toDouble
    val newWidth: Int = width / scaledBy
    val newHeight: Int = (newWidth / ratio).toInt
    ImageIcon(image.getImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH))

  private def createInputTextArea(): TextArea =
    new TextArea:
      border = Swing.EmptyBorder(margin)
      // scrollable
      lineWrap = true
      wordWrap = true
      preferredSize = new Dimension(500, 300)

  private def createProblemComboBox(inputTextArea: TextArea) =
    new ComboBox(List("No selection", "N-Queens", "Graph Coloring", "Nurse Scheduling")):
      selection.reactions += { case event.SelectionChanged(_) => inputTextArea.text = selection.item }

  private def createSolveButton(outputDialog: Dialog): Button = new Button("Solve"):
    font = Font(fontFamily, Font.ITALIC, 20)
    preferredSize = new Dimension(100, 40)
    background = Color.green
    foreground = Color(200, 0, 0)
    reactions += { case event.ButtonClicked(_) => outputDialog.open() }

  private def createOutputDialog(): Dialog =
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
