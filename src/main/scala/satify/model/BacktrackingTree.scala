package satify.model

import satify.model.CNF
import satify.model.dpll.PartialModel

import scala.collection.immutable.Queue

object BacktrackingTree:
  def apply(emptyModel: CNF): BacktrackingTree =
    new BacktrackingTree(emptyModel, Queue.empty)

  // TODO
  def decision(backtrackingTree: BacktrackingTree, s: String, v: Boolean): BacktrackingTree = {
    backtrackingTree match
      case BacktrackingTree(exp, queue) =>
        new BacktrackingTree(
          exp,
          queue :+ (s,
          queue.last match
            case (_, pm) => pm
          )
        )
  }

case class BacktrackingTree(emptyModel: CNF, partialModels: Queue[(String, PartialModel)])
