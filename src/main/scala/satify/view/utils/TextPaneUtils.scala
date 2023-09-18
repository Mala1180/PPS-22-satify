package satify.view.utils

import satify.dsl.Reflection.getDSLKeywords
import satify.view.Constants.fontFamily

import scala.swing.{Swing, TextPane}
import java.awt.{Color, Font}
import javax.swing.text.{MutableAttributeSet, SimpleAttributeSet, StyleConstants}

object TextPaneUtils:

  var textPaneText: String = ""
  private val opAttr: SimpleAttributeSet = createKeywordsAttribute()

  given Ordering[Int] = (x, y) => {
    if x > y then 1
    else if x < y then -1
    else 0
  }

  /** Update the style of a text pane
    *
    * @param t a text pane
    */
  def updateStyle(t: TextPane): Unit =
    val text = t.text
    textPaneText = text

    /** Util function to find all starting indexes where a specific substring appears inside a String.
      *
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
      *
      * @param l      list of offsets
      * @param length length of each String
      */
    def setKeywordsAttribute(l: List[Int], length: Int): Unit = Swing.onEDT {
      for off <- l
      do t.styledDocument.setCharacterAttributes(off, length, opAttr, true)
    }

    val keywords = getDSLKeywords
    val lengths = keywords.map(_.length).distinct
    // Update all keyword style
    for len <- lengths
    do
      setKeywordsAttribute(
        occurrencesIndex(
          i => {
            val l: List[Int] =
              for
                keyword <- keywords
                if keyword.length == len
                idx = text.indexOf(keyword, i)
                if idx != -1
              yield idx
            l match
              case Nil => -1
              case _ => l.min
          },
          0,
          len
        ),
        len
      )

  /** Creates a simple attribute set to set a specific font style or input operators.
    * @return a [[SimpleAttributeSet]]
    */
  private def createKeywordsAttribute(): SimpleAttributeSet =
    val attribute = new SimpleAttributeSet()
    StyleConstants.setFontSize(attribute, 16)
    StyleConstants.setBold(attribute, true)
    StyleConstants.setForeground(attribute, Color(50, 50, 150))
    attribute

  /** Creates a simple attribute set to set the default font style or input operators.
    *
    * @return a [[SimpleAttributeSet]]
    */
  private def createDefaultKeywordsAttribute(): SimpleAttributeSet =
    val attribute = new SimpleAttributeSet()
    StyleConstants.setFontFamily(attribute, fontFamily)
    StyleConstants.setFontSize(attribute, 18)
    attribute
