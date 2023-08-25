package satify.view

import satify.view.ComponentUtils.*
import satify.view.Constants.{headingFont, logoPath, margin}

import javax.swing.{ImageIcon, JFileChooser}
import javax.swing.filechooser.FileNameExtensionFilter
import scala.swing.*

object GUI:
  // logo and logo label
  private val logoIcon: ImageIcon = createImage(logoPath, 5)
  private val logoLabel: Label = new Label:
    icon = logoIcon
    border = Swing.EmptyBorder(margin)

  // input text area and problem combo box

  val inputTextArea: TextArea = createInputTextArea()
  var inputScrollPane = new ScrollPane(inputTextArea)
  private val problemComboBox: ComboBox[String] = createProblemComboBox(inputTextArea)

  // solve and convert to cnf buttons
  val solveButton: Button = createButton("Solve", 100, 40)
  val cnfButton: Button = createButton("Convert to CNF", 170, 40)

  // output dialogs
  val solutionOutputDialog: Dialog = createOutputDialog("Solution")
  val cnfOutputDialog: Dialog = createOutputDialog("Converted formula")

  val fileChooser: FileChooser = createImportFileChooser
  val importMenuItem: MenuItem = new MenuItem("Import")

  // base gui definition and disposal
  def createBaseGUI(): BoxPanel =
    new BoxPanel(Orientation.Vertical):
      contents += new MenuBar():
        contents += importMenuItem
      contents += new FlowPanel():
        contents += logoLabel
      contents += new FlowPanel():
        contents += new BoxPanel(Orientation.Vertical):
          contents += new FlowPanel():
            contents += new Label("Input:"):
              font = headingFont
          contents += inputScrollPane
        contents += new BoxPanel(Orientation.Vertical):
          contents += new FlowPanel():
            contents += new Label("Fill with problem:"):
              font = headingFont
          contents += problemComboBox
      contents += new FlowPanel():
        contents += solveButton
        contents += cnfButton

  private def createImportFileChooser: FileChooser = new FileChooser:
    title = "Import DIMACS formula"
    fileSelectionMode = FileChooser.SelectionMode.FilesOnly
    fileFilter = new FileNameExtensionFilter("Text files", "txt")
    multiSelectionEnabled = false
