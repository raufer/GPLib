package models

/**
The terminal set holds the set of all terminals involved in the genetic program
 */

case class TerminalSet[T](terminals: List[Terminal[T]]) {

  val size = terminals.size

  val names: List[String] = terminals.map {t => t.name}

}

object TerminalSet {
  def apply[T](args: Terminal[T]*) = new TerminalSet(args.toList)
}