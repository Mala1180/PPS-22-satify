package satify.view

import satify.view.ComponentUtils.*
import satify.view.Constants.*

import java.awt.{Color, Dimension}
import javax.swing.border.Border
import javax.swing.event.{DocumentEvent, DocumentListener}
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.text.{DefaultStyledDocument, SimpleAttributeSet, StyleConstants}
import javax.swing.{ImageIcon, JFileChooser}
import scala.swing.{TextPane, *}
import scala.swing.TabbedPane.Page
import scala.swing.event.{EditDone, KeyTyped, ValueChanged}

object GUI:
  private val logoIcon: ImageIcon = createImage(logoPath, 5)
  private val logoLabel: Label = new Label:
    icon = logoIcon
    border = Swing.EmptyBorder(margin)

  val problemParameterPanel: FlowPanel = new FlowPanel()
  var textPaneText: String = ""
  val textPane: TextPane = createInputTextPane(textPaneText)
  val opAttr: SimpleAttributeSet = createOperatorsAttribute()

  val inputScrollPane = new ScrollPane(textPane)
  val problemComboBox: ComboBox[String] = createProblemComboBox()

  val solveAllButton: Button = createButton("Solve all", 200, 40, Color(170, 30, 60))
  val solveButton: Button = createButton("Solve", 100, 40, Color(170, 30, 60))
  val solveProblemButton: Button = createButton("Solve", 100, 40, Color(170, 30, 60))
  val cnfButton: Button = createButton("Convert to CNF", 170, 40, Color(50, 50, 150))
  val stopButton: Button = createButton("Stop", 100, 40, Color(170, 30, 60))
  stopButton.visible = false
  val cnfProblemButton: Button = createButton("Convert to CNF", 170, 40, Color(50, 50, 150))

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
        pages += new Page("Input", createInputComponent())
        pages += new Page("Problems", createProblemsComponent())

  private def createInputComponent(): Component =
    val gridPanel: BorderPanel = new BorderPanel():

      val inputGrid: GridPanel = new GridPanel(1, 1):
        contents += inputScrollPane
      inputGrid.border = Swing.EmptyBorder(0, 0, 0, 25)

      layout(inputGrid) = BorderPanel.Position.Center

      layout(
        new FlowPanel():
          contents += new GridPanel(5, 1):
            contents += solveAllButton
            contents += solveButton
            contents += cnfButton
            contents += stopButton
            contents += loadingLabel
      ) = BorderPanel.Position.East

    gridPanel.background = Color.BLUE
    val borderPanel = new BorderPanel():
      layout(gridPanel) = BorderPanel.Position.Center
      border = Swing.EmptyBorder(75, 100, 75, 100)
    borderPanel

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
    title = "Import DIMACS formula"
    fileSelectionMode = FileChooser.SelectionMode.FilesOnly
    fileFilter = new FileNameExtensionFilter("Text files", "txt")
    multiSelectionEnabled = false

  /**
   * Creates a [[SimpleAttributeSet]] to set a specific font style
   * for input operators.
   * @return
   */
  private def createOperatorsAttribute(): SimpleAttributeSet =
    val attribute = new SimpleAttributeSet()
    StyleConstants.setFontSize(attribute, 16)
    StyleConstants.setBold(attribute, true)
    StyleConstants.setForeground(attribute, Color(50, 50, 150))
    attribute

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
    stopButton.visible = true
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
    stopButton.visible = false
    inputTextPane.enabled = true
    solveAllButton.enabled = true
    solveButton.enabled = true
    solveProblemButton.enabled = true
    cnfButton.enabled = true
    cnfProblemButton.enabled = true
    importMenuItem.enabled = true
