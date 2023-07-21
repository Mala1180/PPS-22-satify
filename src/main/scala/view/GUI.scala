package view
import scala.swing._
import model.Model

case class GUI(model: Model):
  new Frame {
    title = "Hello world"

    contents = new FlowPanel {
      contents += new Label("Hello World")
      contents += new Button("Click me") {
        reactions += { case event.ButtonClicked(_) =>
          println("All the colours!")
        }
      }
    }

    pack()
    centerOnScreen()
    open()
  }
