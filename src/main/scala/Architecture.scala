import model.Model
import update.Message
import view.GUI

object Architecture:
  trait ModelComponent:
    var model: Model

  trait ViewComponent:
    dependency: ModelComponent =>
    type View = Model => GUI
    val view: View

  trait UpdateComponent:
    dependency: ModelComponent =>
    type Update = (Model, Message) => Model
    val update: Update

  trait MVU extends ModelComponent with ViewComponent with UpdateComponent:
    var model: Model = new Model {}
    override val view: View = model => GUI(model)
    override val update: Update = (model, message) =>
      println("update triggered")
      model
