package satify.view

import java.awt.{Font, Toolkit}
import scala.swing.Dimension

object Constants:
  val margin = 10
  val inputPadding = 5
  val windowSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  val logoPath = "/img/Satify_logo-colored.png"
  val fontFamily = "Arial"
  val headingFont: Font = Font(fontFamily, Font.BOLD, 16)
  val expTextPaneName = "expTextPane"
  val helpFontSize = 16
  val helpDialogMargin = 50
  val parameterInputName = "parTextField"
  val cnfOutputDialogName = "cnfOutputDialog"
  val solOutputDialogName = "solOutputDialog"
  val problemOutputDialogName = "problemOutputDialog"
  val nextBtnName = "nextButton"
  val nQueens = "nQueens"
  val gcNodes = "nodes"
  val gcEdges = "edges"
  val gcColors = "colors"
  val nsNurses = "nurses"
  val nsDays = "days"
  val nsShifts = "shifts"
