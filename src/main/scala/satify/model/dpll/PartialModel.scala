package satify.model.dpll

case class OptionalVariable(name: String, value: Option[Boolean] = None)

type PartialAssignment = Seq[OptionalVariable]

object OrderedSeq:

  given Ordering[OptionalVariable] with
    def compare(x: OptionalVariable, y: OptionalVariable): Int = (x, y) match
      case (OptionalVariable(xName, _), OptionalVariable(yName, _)) => xName.compareTo(yName)

  def seq[T](elems: T*)(using ordering: Ordering[T]): Seq[T] = elems.distinct.sorted(ordering)
