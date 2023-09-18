package satify.view

import satify.view.utils.ComponentUtils.*
import satify.view.Constants.*
import satify.view.utils.Title.*

import java.awt.{Color, Dimension}
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.text.{DefaultStyledDocument, SimpleAttributeSet, StyleConstants}
import javax.swing.{ImageIcon, JFileChooser}
import scala.swing.*
import scala.swing.TabbedPane.Page

object GUI:
  private val logoIcon: ImageIcon = createImage(logoPath, 5)
  private val logoLabel: Label = new Label:
    icon = logoIcon
    border = Swing.EmptyBorder(margin)

  val problemParameterPanel: FlowPanel = new FlowPanel()
  val textPane: TextPane = createInputTextPane("")
  val inputScrollPane = new ScrollPane(textPane)
  val problemComboBox: ComboBox[String] = createProblemComboBox()

  val solveAllButton: Button = createButton(SolveAll.title, 200, 40, Color(170, 30, 60))
  val solveButton: Button = createButton(Solve.title, 100, 40, Color(170, 30, 60))
  val solveProblemButton: Button = createButton(Solve.title, 100, 40, Color(170, 30, 60))
  val cnfButton: Button = createButton(Convert.title, 170, 40, Color(50, 50, 150))
  val cnfProblemButton: Button = createButton(Convert.title, 170, 40, Color(50, 50, 150))

  val solutionOutputDialog: Dialog = createOutputDialog(SolutionDialog.title)
  val cnfOutputDialog: Dialog = createOutputDialog(ConversionDialog.title)
  val problemOutputDialog: Dialog = createOutputDialog(ProblemDialog.title)
  val helpDialog: Dialog = createHelpDialog()
  val loadingLabel: Label = createLabel(LoadingLabel.title, 16)
  loadingLabel.visible = false

  val importFileChooser: FileChooser = createImportFileChooser
  val exportFileChooser: FileChooser = createExportFileChooser

  val helpMenuItem: MenuItem = new MenuItem(Help.title):
    maximumSize = new Dimension(50, 200)
  val importMenuItem: MenuItem = new MenuItem(Import.title):
    maximumSize = new Dimension(1000, 200)

  def inputTextPane: TextPane = inputScrollPane.contents
    .filter(c => c.isInstanceOf[TextPane] && c.name == expTextPaneName)
    .head
    .asInstanceOf[TextPane]

  def createBaseGUI(): BoxPanel =
    new BoxPanel(Orientation.Vertical):
      contents += new MenuBar():
        contents += helpMenuItem
        contents += importMenuItem
      contents += new BoxPanel(Orientation.Horizontal):
        contents += logoLabel
      contents += new TabbedPane:
        pages += new Page(InputTab.title, createInputComponent())
        pages += new Page(ProblemTab.title, createProblemsComponent())

  private def createInputComponent(): Component =
    val borderPanel: BorderPanel = new BorderPanel():
      val label = new Label("Input:")
      label.border = Swing.EmptyBorder(0, 0, 2, 0)
      label.horizontalAlignment = Alignment.Left
      layout(label) = BorderPanel.Position.North
      layout(new BorderPanel():
        val inputGrid: GridPanel = new GridPanel(1, 1):
          contents += inputScrollPane
        inputGrid.border = Swing.EmptyBorder(0, 0, 0, 25)
        layout(inputGrid) = BorderPanel.Position.Center
        layout(
          new GridPanel(8, 1):
            contents += solveAllButton
            contents += solveButton
            contents += cnfButton
            contents += loadingLabel
        ) = BorderPanel.Position.East
      ) = BorderPanel.Position.Center

    val mainBorderPanel = new BorderPanel():
      layout(borderPanel) = BorderPanel.Position.Center
      border = Swing.EmptyBorder(75, 100, 75, 100)
    mainBorderPanel

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
          contents += cnfProblemButton
        contents += new FlowPanel():
          contents += loadingLabel

  /** Creates a file chooser for the import menu item.
    * @return the file chooser
    */
  private def createImportFileChooser: FileChooser = new FileChooser:
    title = ImportDialog.title
    fileSelectionMode = FileChooser.SelectionMode.FilesOnly
    fileFilter = new FileNameExtensionFilter("Text files", "txt")
    multiSelectionEnabled = false

  /** Creates a file chooser for the export item in CNF output dialog.
    *
    * @return the file chooser
    */
  def createExportFileChooser: FileChooser = new FileChooser:
    title = ExportDialog.title
    fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
    multiSelectionEnabled = false

  /** Disable all GUI interactions when the solving or converting process starts. */
  def disableInteractions(): Unit =
    loadingLabel.visible = true
    inputTextPane.enabled = false
    solveAllButton.enabled = false
    solveButton.enabled = false
    solveProblemButton.enabled = false
    cnfButton.enabled = false
    cnfProblemButton.enabled = false
    importMenuItem.enabled = false

  /** Disable all GUI interactions when the solving or converting process finish or crash. */
  def enableInteractions(): Unit =
    loadingLabel.visible = false
    inputTextPane.enabled = true
    solveAllButton.enabled = true
    solveButton.enabled = true
    solveProblemButton.enabled = true
    cnfButton.enabled = true
    cnfProblemButton.enabled = true
    importMenuItem.enabled = true
