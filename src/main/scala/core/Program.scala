package gplib

import java.util.concurrent.atomic.AtomicInteger
import com.github.dwickern.macros.NameOf._

/**
ADT to model an GP individual

This type can either be a 'Leaf' which just holds data (an A)
or a 'Node' that holds behavior and and a variable number of arguments.

All of the programs involved on a GP run should operate over the same type A.
Every program define a computable result, i.e it can be resolved to a single A

Note also that any 'Program' just holds a description of the execution. It doesn't actually know how to evaluate itself
 */
sealed trait Program[A] {
  override def toString() = ???
}

case object EmptyTree extends Program[Nothing]

case class Leaf[A](id: String, name: String, value: A) extends Program[A]

case class Node[A](id: String, name: String, f: AnyRef, branches: Program[A]*) extends Program[A]


object Leaf {
  val counter = new AtomicInteger(0)

  def apply[A](value: A) = {
    val id = "Leaf [%d]".format(Leaf.counter.incrementAndGet())
    new Leaf(id, value.toString, value)
  }

  def apply[A](value: A, name: String) = {
    val id = "Leaf [%d]".format(Leaf.counter.incrementAndGet())
    new Leaf(id, name, value)
  }
}

object Node {
  val counter = new AtomicInteger(0)

  def apply[A](f: AnyRef, branches: Program[A]*) = {
    val id = "Node [%d]".format(Node.counter.incrementAndGet())
    new Node(id, nameOf(f), f, branches:_*)
  }

  def apply[A](f: AnyRef, name: String, branches: Program[A]*) = {
    val id = "Node [%d]".format(Node.counter.incrementAndGet())
    new Node(id, name, f, branches: _*)
  }

  }
