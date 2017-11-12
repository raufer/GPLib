package models

import java.util.concurrent.atomic.AtomicInteger

import utils.ProgramUtils.prettyPrint

/**
In GP, programs are usually expressed as syntax trees rather than as lines of code.
In some cases, it may be desirable to use GP primitives which accept a variable number of arguments (a quantity we will call arity).

ADT to model an GP individual

This type can either be a 'Leaf' which just holds data (an A)
or a 'Node' that holds behavior and and a variable number of arguments.

All of the programs involved on a GP run should operate over the same type A.
Every program define a computable result, i.e it can be resolved to a single A

Note also that any 'Program' just holds a description of the execution. It doesn't actually know how to evaluate itself
 */
sealed trait Program[+A] {
  override def toString() = prettyPrint(this)
}

/**
A zygote (from Greek ζυγωτός zygōtos "joined" or "yoked", from ζυγοῦν zygoun "to join" or "to yoke")
is a cell formed by a fertilization event between two gametes. A single cell in day 0
 */
case object Zygote extends Program[Nothing]

case class Leaf[A](id: String, terminal: Terminal[A]) extends Program[A]

case class Node[A](id: String, f: Function, branches: Program[A]*) extends Program[A]


object Leaf {
  val counter = new AtomicInteger(0)

  def apply[A](value: Terminal[A]) = {
    val id = "Leaf [%d]".format(Leaf.counter.incrementAndGet())
    new Leaf(id, value)
  }
}

object Node {
  val counter = new AtomicInteger(0)

  def apply[A](f: Function, branches: Program[A]*) = {
    val id = "Node [%d]".format(Node.counter.incrementAndGet())
    new Node(id, f, branches:_*)
  }

}
