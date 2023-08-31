package satify.model.tree

import Value.*

// Tree
trait Tree[+V]

case class BinaryBranch[V](value: V, left: Tree[V], right: Tree[V]) extends Tree[V]

case class UnaryBranch[V](value: V, branch: Tree[V]) extends Tree[V]

case class Leaf[V](value: Option[V] = None) extends Tree[V]

// Tree node's value
enum Value:
  case And
  case Or
  case Not
  case Symbol(value: String)
