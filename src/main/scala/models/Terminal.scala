package models

/*
The terminal set holds the set of all terminals involved in the genetic program
A terminal can either be a Constant, holding a well defined value
or it can be a Variable, ie a placeholder to other values
 */

sealed trait Terminal[T] {
  val name: String
}

case class Constant[T](value: T, name: String) extends Terminal[T]
case class Variable[T](name: String) extends Terminal[T]

object Constant {
  def apply[T](value: T) = new Constant[T](value, value.toString)
}