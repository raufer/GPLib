package models

/**
A terminal can either be a Constant, holding a well defined value
or it can be a Variable, ie a placeholder to other values
  */

sealed trait Terminal[T]{
  val name: String
  val value: T
}

case class Constant[T](value: T, name: String) extends Terminal[T]
case class Variable[T](value: T, name: String) extends Terminal[T]

object Constant {
  def apply[T](value: T) = new Constant[T](value, value.toString)
}
