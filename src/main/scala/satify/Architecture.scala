package satify

import satify.model.{CNF, Expression, State, Variable}
import satify.update.Message.{Convert, Input, Solve}
import satify.update.converters.CNFConverter
import satify.update.converters.TseitinTransformation.tseitin
import satify.update.{Message, Solver}
import satify.view.GUI

/** Object containing the necessary components for the Model-View-Update architecture. */
object Architecture:

  trait ModelComponent:
    /** The Model type is an immutable entity which represents the state of the application. */
    type Model = State
    var model: Model

  trait ViewComponent:
    dependency: ModelComponent =>

    /** The View type is a function which takes a Model and returns a GUI. */
    type View = Model => GUI
    val view: View

  trait UpdateComponent:
    dependency: ModelComponent =>

    /** The Update type is a function which takes a Model and a Message and returns a new Model. */
    type Update = (Model, Message) => Model
    val update: Update

  /** Represents the Model-View-Update architecture. */
  trait MVU extends ModelComponent with ViewComponent with UpdateComponent:
    var model: Model = State()
    override val view: View = model => GUI(model, update)
    override val update: Update = (model, message) =>
      message match
        case Input(char) => model
        case Solve(exp) =>
          given CNFConverter with
            def convert(exp: Expression): CNF = tseitin(exp)

          Solver().solve(exp)
          State()
        case Convert(exp) =>
          tseitin(exp)
          State()
