package model

import scala.collection.immutable.Queue

case class Bool(s: String, v: Option[Boolean] = Option.empty)

type PartialModel = Set[Bool]

object BacktrackingTree:

  def apply(): BacktrackingTree = {
    new BacktrackingTree(Queue.empty)
  }

  def decision(backtrackingTree: BacktrackingTree, s: String, v: Boolean): BacktrackingTree = {
    backtrackingTree match
      case BacktrackingTree(queue) =>
        new BacktrackingTree(
          queue :+ (s,
            queue.last match
              case (_, pm) =>
                pm.map {
                  case Bool(bs, _) if bs == s => Bool(bs, Option(v))
                  case pb@Bool(_, _) => pb
              })
    )
  }

case class BacktrackingTree(queue: Queue[(String, PartialModel)])

