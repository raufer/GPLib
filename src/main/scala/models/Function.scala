package models

import java.util.concurrent.atomic.AtomicInteger

/**
 The variables and constants in the program (x, y and 3) are leaves of the tree.
 In GP they are called terminals, whilst the arithmetic operations (+, * and max) are internal nodes called functions.
 The sets of allowed functions and terminals together form the primitive set of a GP system.
*/

case class Function(f: AnyRef, name: String)

object Function {

  val counter = new AtomicInteger(0)

  def apply(f: AnyRef) = new Function(f, "function" + Node.counter.incrementAndGet().toString)
}
