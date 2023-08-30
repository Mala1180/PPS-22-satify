package satify.view

import java.awt.{Font, Toolkit}
import scala.swing.Dimension

object Constants:
  val margin = 10
  val windowSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  val logoPath = "/img/Satify_logo-colored.png"
  val fontFamily = "Arial"
  val headingFont: Font = Font(fontFamily, Font.BOLD, 16)
  val expTextAreaName = "expTextArea"
  val parameterInputName = "parTextField"
  val cnfOutputDialogName = "cnfOutputDialog"
  val solOutputDialogName = "solOutputDialog"
