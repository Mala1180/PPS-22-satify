package view

import java.awt.{Font, Toolkit}
import scala.swing.Dimension

object Utils:
  val margin = 10
  val windowSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  val logoPath = "src/main/resources/img/Satify_logo-colored.png"
  val fontFamily = "Arial"
  val headingFont = new Font(fontFamily, Font.BOLD, 16)

