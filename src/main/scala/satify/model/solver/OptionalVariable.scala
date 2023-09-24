package satify.model.solver

import satify.model.{Assignment, Variable}

/** Represents a variable which could be constrained or not yet constrained by the DPLL algorithm.
  * @param name of the variable.
  * @param value eventual boolean value constraint.
  */
case class OptionalVariable(name: String, value: Option[Boolean] = None):
  lazy val toVariable: Variable = Variable(name, value.get)

object OrderedList:

  /** [[OptionalVariable]] lexicographic ordering. */
  given Ordering[OptionalVariable] with
    def compare(x: OptionalVariable, y: OptionalVariable): Int = (x, y) match
      case (OptionalVariable(xName, _), OptionalVariable(yName, _)) => xName.compareTo(yName)

  /** Ordered list using a [[ordering]].
    * @param elems of the list.
    * @param ordering ordering to be applied.
    * @tparam T type
    * @return ordered list.
    */
  def list[T](elems: T*)(using ordering: Ordering[T]): List[T] = elems.distinct.sorted(ordering).toList
