package satify.model.tree

import Value.*

import scala.annotation.tailrec

// Tree
trait Tree[+V]

case class BinaryBranch[V](value: V, left: Tree[V], right: Tree[V]) extends Tree[V]

case class UnaryBranch[V](value: V, branch: Tree[V]) extends Tree[V]

case class Leaf[V](value: Option[V] = None) extends Tree[V]

// Tree node's value
enum Value:
  case and
  case or
  case not
  case symbol(value: String)

trait Traversable[F[_]]:

  extension [A](t: F[A]) def foreach[U](f: F[A] => U): Unit

object TraversableOps:

  extension [A, F[_]: Traversable](t: F[A])

    def foldLeft[U](z: U)(op: (U, F[A]) => U): U =
      var res = z
      t.foreach(a => res = op(res, a))
      res

    /*def foldRight[U](z: U)(op: (F[A], U) => U): U =
      var res = z
      t.foreach(a => res = op(a, res))
      res
     */

    def forall(p: F[A] => Boolean): Boolean = t.foldLeft(true)((a, b: F[A]) => a && p(b))

    def exists(p: F[A] => Boolean): Boolean = !t.forall((b: F[A]) => !p(b))

    def contains(a: F[A]): Boolean = t.exists(_ == a)

object TreeTraversableGiven:

  import TraversableOps.*

  given Traversable[Tree] with

    extension [A](t: Tree[A])
      def foreach[U](f: Tree[A] => U): Unit =
        f(t)
        t match
          case BinaryBranch(_, left, right) =>
            left.foreach(f)
            right.foreach(f)
          case UnaryBranch(_, branch) => branch.foreach(f)
          case Leaf(_) =>

      /*def map(f: Tree[A] => Tree[A]): Tree[A] =
        val ft = f(t)
        t match
          case BinaryBranch(value, left, right) =>
            if ft != t then ft else BinaryBranch(value, left.map(f), right.map(f))
          case Leaf(_) => ft
          case UnaryBranch(value, branch) =>
            if ft != t then ft else UnaryBranch(value, branch.map(f))
       */

      def map[B](f: Tree[A] => Tree[B]): Tree[B] =

        case class Frame(value: Option[B], mapped: List[Tree[B]], todos: List[Tree[A]])

        @tailrec
        def step(stack: List[Frame]): Tree[B] = stack match
          // "return / pop a stack-frame"
          case Frame(value, done, Nil) :: tail =>
            val rev = done.reverse
            val ret =
              if rev.length == 1 then
                if value.isDefined then UnaryBranch(value.get, rev.head)
                else
                  rev.head
              else if rev.length == 2 then BinaryBranch(value.get, rev.head, rev(1))
              else Leaf(Some(value.get))
            tail match
              case Nil => ret
              case Frame(tn, td, tt) :: more =>
                step(Frame(tn, ret :: td, tt) :: more)
          case Frame(value, done, x :: xs) :: tail =>
            x match
              // "recursion base"
              case l @ Leaf(_) => step(Frame(value, f(l) :: done, xs) :: tail)
              // "recursive call"
              case b @ BinaryBranch(n, left, right) =>
                val fb = f(b)
                fb match
                  case BinaryBranch(nb, _, _) if fb == b =>
                    step(Frame(Some(nb), Nil, List(left, right)) :: Frame(value, done, xs) :: tail)
                  case _ => step(Frame(value, fb :: done, xs) :: tail)
              case u @ UnaryBranch(_, branch) =>
                val fu = f(u)
                fu match
                  case UnaryBranch(nu, _) if fu == u =>
                    step(Frame(Some(nu), Nil, List(branch)) :: Frame(value, done, xs) :: tail)
                  case _ => step(Frame(value, fu :: done, xs) :: tail)
          case Nil => throw new Error("shouldn't happen")

        t match
          case l @ Leaf(_) => f(l)
          case b @ BinaryBranch(v, _, _) => step(List(Frame(None, Nil, List(b))))
          case u @ UnaryBranch(_, _) => step(List(Frame(None, Nil, List(u))))

      def withFilter(f: Tree[A] => Boolean): Tree[A] =
        t.map(tree => if f(tree) then tree else Leaf())

      def zipWith(f: () => Leaf[A]): List[(Leaf[A], Tree[A])] =

        def subTree(tree: Tree[A], list: List[(Leaf[A], Tree[A])])(f: () => Leaf[A]): List[(Leaf[A], Tree[A])] = tree match
          case Leaf(_) => List()
          case e => (f(), tree) :: list ::: subBranch(e, list)(f)

        def subBranch(tree: Tree[A], list: List[(Leaf[A], Tree[A])])(f: () => Leaf[A]): List[(Leaf[A], Tree[A])] = tree match
          case BinaryBranch(_, left, right) => subTree(left, list)(f) ::: subTree(right, list)(f)
          case UnaryBranch(_, branch) => subTree(branch, list)(f)
          case _ => subTree(tree, list)(f)

        t match
          case Leaf(_) => List((f(), t))
          case UnaryBranch(not, Leaf(Some(Symbol(_)))) => List((f(), t))
          case _ => subTree(t, List())(f)
