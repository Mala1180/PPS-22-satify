package satify.view.utils

import scala.swing.{Swing, TextPane}

import java.awt.Color
import javax.swing.text.{SimpleAttributeSet, StyleConstants}

object TextPaneUtils:

  var textPaneText: String = ""
  private val opAttr: SimpleAttributeSet = createOperatorsAttribute()

  /** Update the style of a text pane
    * @param t a text pane
    */
  def updateStyle(t: TextPane): Unit =
    val text = t.text
    textPaneText = text

    /** Util function to find all starting indexes where a specific substring appears inside a String.
      * @param findIndex function which takes an Int, representing the index position where start searching for
      *                  occurrences and returns an Int, that is equal -1 if no occurrences are found, n otherwise.
      * @param from      starting index to search for occurrences
      * @param length    length of the searching element
      * @return list of all indexes where an occurrence has been found
      */
    def occurrencesIndex(findIndex: Int => Int, from: Int, length: Int): List[Int] =
      val start = findIndex(from)
      if start != -1 then occurrencesIndex(findIndex, from + length, length) :+ start
      else Nil

    /** Sets character attribute foreach offset inside the input list
      * @param l      list of offsets
      * @param length length of each String
      */
    def setOperatorsAttribute(l: List[Int], length: Int): Unit = Swing.onEDT {
      l.foreach(i => t.styledDocument.setCharacterAttributes(i, length, opAttr, true))
    }

    // Update all operators style
    setOperatorsAttribute(occurrencesIndex(i => text.indexOf("!", i), 0, 1), 1)
    setOperatorsAttribute(occurrencesIndex(i => text.indexOf("or", i), 0, 2), 2)
    setOperatorsAttribute(
      occurrencesIndex(
        i => {
          val l: List[Int] = (text.indexOf("and", i) :: text.indexOf("not", i) ::
            text.indexOf("xor", i) :: Nil)
            .filter(i => i != -1)
          l match
            case Nil => -1
            case _ =>
              l.min((x, y) =>
                if x > y then 1
                else if x < y then -1
                else 0
              )
        },
        0,
        3
      ),
      3
    )

  /** Creates a simple attribute set to set a specific font style or input operators.
    * @return a [[SimpleAttributeSet]]
    */
  private def createOperatorsAttribute(): SimpleAttributeSet =
    val attribute = new SimpleAttributeSet()
    StyleConstants.setBold(attribute, true)
    StyleConstants.setForeground(attribute, Color(50, 50, 150))
    attribute
