package satify.model.dpll

import satify.model.Variable

object OrderedSeq:
  def seq[T](elems: T*)(using ordering: Ordering[T]): Seq[T] = elems.distinct.sorted(ordering)

given Ordering[Variable] with
  def compare(x: Variable, y: Variable): Int = (x, y) match
    case (Variable(xName, _), Variable(yName, _)) => xName.compareTo(yName)

type PartialModel = Seq[Variable]
