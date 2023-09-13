package satify.view

import satify.view.ComponentUtils.*
import satify.view.Constants.*

import java.awt.Color
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.{ImageIcon, JFileChooser}
import scala.swing.*
import scala.swing.TabbedPane.Page

object GUI:
  private val logoIcon: ImageIcon = createImage(logoPath, 5)
  private val logoLabel: Label = new Label:
    icon = logoIcon
    border = Swing.EmptyBorder(margin)

  val problemParameterPanel: FlowPanel = new FlowPanel()
  val inputScrollPane = new ScrollPane(createInputTextArea())
  val problemComboBox: ComboBox[String] = createProblemComboBox()

  val solveAllButton: Button = createButton("Solve all", 200, 40, Color(170, 30, 60))
  val solveButton: Button = createButton("Solve", 100, 40, Color(170, 30, 60))
  val solveProblemButton: Button = createButton("Solve", 100, 40, Color(170, 30, 60))
  val cnfButton: Button = createButton("Convert to CNF", 170, 40, Color(50, 50, 150))

  val solutionOutputDialog: Dialog = createOutputDialog("Solution")
  val cnfOutputDialog: Dialog = createOutputDialog("Converted formula")
  val helpDialog: Dialog = createHelpDialog()
  val loadingLabel: Label = createLabel("Loading...", 16)
  loadingLabel.visible = false

  val importFileChooser: FileChooser = createImportFileChooser
  val exportFileChooser: FileChooser = createExportFileChooser

  val helpMenuItem: MenuItem = new MenuItem("Help"):
    maximumSize = new Dimension(50, 200)
  val importMenuItem: MenuItem = new MenuItem("Import"):
    maximumSize = new Dimension(1000, 200)

  def inputTextArea: TextArea = inputScrollPane.contents
    .filter(c => c.isInstanceOf[TextArea] && c.name == expTextAreaName)
    .head
    .asInstanceOf[TextArea]

  def createBaseGUI(): BoxPanel =
    new BoxPanel(Orientation.Vertical):
      contents += new MenuBar():
        contents += helpMenuItem
        contents += importMenuItem
      contents += new BoxPanel(Orientation.Horizontal):
        contents += logoLabel
      contents += new TabbedPane:
        pages += new Page("Input", createInputComponent())
        pages += new Page("Problems", createProblemsComponent())

  private def createInputComponent(): Component =
    new FlowPanel():
      contents += new BoxPanel(Orientation.Vertical):
        contents += new FlowPanel():
          contents += new Label("Input:"):
            font = headingFont
        contents += inputScrollPane
      contents += new GridPanel(3, 1):
        contents += solveAllButton
        contents += solveButton
        contents += cnfButton
      contents += new FlowPanel():
        contents += loadingLabel

  private def createProblemsComponent(): Component =
    new FlowPanel():
      contents += new BoxPanel(Orientation.Vertical):
        contents += new FlowPanel():
          contents += new Label("Choose problem:"):
            font = headingFont
        contents += problemComboBox
        contents += new FlowPanel():
          contents += new Label("Parameter:"):
            font = headingFont
        contents += new BoxPanel(Orientation.Vertical):
          contents += problemParameterPanel
          font = headingFont
        contents += new FlowPanel():
          contents += solveProblemButton
        contents += new FlowPanel():
          contents += loadingLabel

  /** Creates a file chooser for the import menu item.
    * @return the file chooser
    */
  private def createImportFileChooser: FileChooser = new FileChooser:
    title = "Import DIMACS formula"
    fileSelectionMode = FileChooser.SelectionMode.FilesOnly
    fileFilter = new FileNameExtensionFilter("Text files", "txt")
    multiSelectionEnabled = false

  /** Creates a file chooser for the export item in CNF output dialog.
    *
    * @return the file chooser
    */
  def createExportFileChooser: FileChooser = new FileChooser:
    title = "Export DIMACS formula"
    fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
    multiSelectionEnabled = false

  /** Disable all GUI interactions when the solving or converting process starts. */
  def disableInteractions(): Unit =
    loadingLabel.visible = true
    inputTextArea.enabled = false
    solveAllButton.enabled = false
    solveButton.enabled = false
    solveProblemButton.enabled = false
    cnfButton.enabled = false
    importMenuItem.enabled = false

  /** Disable all GUI interactions when the solving or converting process finish or crash. */
  def enableInteractions(): Unit =
    loadingLabel.visible = false
    inputTextArea.enabled = true
    solveAllButton.enabled = true
    solveButton.enabled = true
    solveProblemButton.enabled = true
    cnfButton.enabled = true
    importMenuItem.enabled = true
