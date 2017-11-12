package models

case class FunctionSet(functions: List[Function]) {

  val size = functions.size

  val names: List[String] = functions.map(f => f.name)

}

object FunctionSet {

  def apply[T](args: Function*) = new FunctionSet(args.toList)

}
