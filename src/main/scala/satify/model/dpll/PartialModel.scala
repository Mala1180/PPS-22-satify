package satify.model.dpll

case class Variable(name: String, value: Option[Boolean] = None)

type PartialModel = Seq[Variable]

object OrderedSeq:
  given Ordering[Variable] with
    def compare(x: Variable, y: Variable): Int = (x, y) match
      case (Variable(xName, _), Variable(yName, _)) => xName.compareTo(yName)

  def seq[T](elems: T*)(using ordering: Ordering[T]): Seq[T] = elems.distinct.sorted(ordering)
