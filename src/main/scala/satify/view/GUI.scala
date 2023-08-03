package satify.view

import satify.Main.Model
import satify.model.State
import satify.update.Message
import satify.view.Constants.*

import javax.swing.ImageIcon
import scala.swing.*

/** The GUI for the game */
case class GUI(model: State, update: (Model, Message) => Model)

/** Companion object of the GUI */
object GUI:
  import ComponentUtils.*

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
      val solutionOutputDialog: Dialog = createOutputDialog("Solution")
      val solveButton: Button = createButton(gui, "Solve")
      solveButton.reactions += { case event.ButtonClicked(_) =>
        //gui.update(gui.model, Solve(gui.model.expression))
        solutionOutputDialog.open()
      }
      val cnfOutputDialog: Dialog = createOutputDialog("CNF")
      val cnfButton: Button = createButton(gui, "CNF")
      cnfButton.reactions += { case event.ButtonClicked(_) =>
        //gui.update(gui.model, CNF(gui.model.expression))
        cnfOutputDialog.open()
      }

      contents = new BoxPanel(Orientation.Vertical):
        contents += new FlowPanel():
          contents += logoLabel
        contents += new FlowPanel():
          contents += new BoxPanel(Orientation.Vertical):
            contents += new FlowPanel():
              contents += new Label("Input:"):
                font = headingFont
            contents += new ScrollPane(inputTextArea)
          contents += new BoxPanel(Orientation.Vertical):
            contents += new FlowPanel():
              contents += new Label("Fill with problem:"):
                font = headingFont
            contents += problemComboBox
        contents += new FlowPanel():
          contents += solveButton
          contents += cnfButton

      // size of the main frame based on the screen size
      size = new Dimension(windowSize.width / 2, windowSize.height / 3 * 2)
      centerOnScreen()
      open()
