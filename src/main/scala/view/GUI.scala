package view
import model.State

import scala.swing.*

/** The GUI for the game */
case class GUI(model: State)

/** Object containing the functions related to the GUI */
object GUI:
  /** Renders the GUI
    * @param gui the GUI to render
    */
  def render(gui: GUI): Unit = new Frame:
    title = "Hello world"

    contents = new FlowPanel:
      contents += new Label("Hello World")
      contents += new Button("Click me"):
        reactions += { case event.ButtonClicked(_) => println("All the colours!") }

    pack()
    centerOnScreen()
    open()
