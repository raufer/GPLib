package models

case class Function(f: AnyRef, name: String)

case class FunctionSet(functions: List[Function]) {

  val names: List[String] = functions.map(f => f.name)

}

object FunctionSet {

  def apply(args: AnyRef*) = {

    val functions = List(args: _*).zipWithIndex map { x =>
        x._1 match {
          case Tuple2(f: AnyRef, s: String) => Function(f, s)
          case f => Function(f, "function" + x._2.toString)
        }
    }

    new FunctionSet(functions)

  }
}
