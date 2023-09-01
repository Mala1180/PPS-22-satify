package satify.view

import satify.view.ComponentUtils.*
import satify.view.Constants.{headingFont, logoPath, margin, windowSize}

import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.{ImageIcon, JFileChooser}
import scala.swing.*
import scala.swing.TabbedPane.Page

object GUI:
  // logo and logo label
  private val logoIcon: ImageIcon = createImage(logoPath, 5)
  private val logoLabel: Label = new Label:
    icon = logoIcon
    border = Swing.EmptyBorder(margin)

  var inputTextArea: TextArea = createInputTextArea()
  val parameterInputText: TextField = createParameterInputText()
  var inputScrollPane = new ScrollPane(inputTextArea)
  val problemComboBox: ComboBox[String] = createProblemComboBox()

  // solve and convert to cnf buttons
  val solveButton: Button = createButton("Solve", 100, 40)
  val solveProblemButton: Button = createButton("Solve", 100, 40)
  val cnfButton: Button = createButton("Convert to CNF", 170, 40)
  val nextSolutionButton: Button = createButton("Next", 100, 40)

  // output dialogs
  val solutionOutputDialog: Dialog = createOutputDialog("Solution")
  val cnfOutputDialog: Dialog = createOutputDialog("Converted formula")
  val helpDialog: Dialog = createHelpDialog()
  val loadingLabel: Label = createLabel("Loading...", 16)
  loadingLabel.visible = false

  val fileChooser: FileChooser = createImportFileChooser
  val helpMenuItem: MenuItem = new MenuItem("Help"):
    maximumSize = new Dimension(50, 200)
  val importMenuItem: MenuItem = new MenuItem("Import"):
    maximumSize = new Dimension(1000, 200)

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
      contents += new FlowPanel():
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
        contents += parameterInputText
        contents += new FlowPanel():
          contents += solveProblemButton

  private def createImportFileChooser: FileChooser = new FileChooser:
    title = "Import DIMACS formula"
    fileSelectionMode = FileChooser.SelectionMode.FilesOnly
    fileFilter = new FileNameExtensionFilter("Text files", "txt")
    multiSelectionEnabled = false
