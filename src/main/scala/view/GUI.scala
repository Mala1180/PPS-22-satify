package view
import model.State

import scala.swing.*

case class GUI(model: State):

  new Frame:
    title = "Hello world"

    contents = new FlowPanel:
      contents += new Label("Hello World")
      contents += new Button("Click me"):
        reactions += { case event.ButtonClicked(_) => println("All the colours!") }

    pack()
    centerOnScreen()
    open()
