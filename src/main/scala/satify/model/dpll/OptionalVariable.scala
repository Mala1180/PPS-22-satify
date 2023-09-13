package satify.model.dpll

import satify.model.{Assignment, Variable}

case class OptionalVariable(name: String, value: Option[Boolean] = None):
  lazy val toVariable: Variable = Variable(name, value.get)

object OrderedList:

  given Ordering[OptionalVariable] with
    def compare(x: OptionalVariable, y: OptionalVariable): Int = (x, y) match
      case (OptionalVariable(xName, _), OptionalVariable(yName, _)) => xName.compareTo(yName)

  def list[T](elems: T*)(using ordering: Ordering[T]): List[T] = elems.distinct.sorted(ordering).toList
