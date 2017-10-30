package utils

import gplib.{Leaf, Node, Program}

import scala.annotation.tailrec
import Recipes.dropWhile

import scala.util.Try

object ProgramUtils {

  /**
  "Abstract" over arity
  Scala does not provide a base `function` type. Each arity results in an independent type

  Pattern matches on the type and calls with the respective number of arguments
  caution: the type system is lost here, returns a 'Any'
   */
  def call[T](function: AnyRef, args: Seq[T]): T = {
    function match {
      case f: Function1[Any, Any] => f(args(0)).asInstanceOf[T]
      case f: Function2[Any, Any, Any] => f(args(0), args(1)).asInstanceOf[T]
      case f: Function3[Any, Any, Any, Any] => f(args(0), args(1), args(2)).asInstanceOf[T]
      case f: Function4[Any, Any, Any, Any, Any] => f(args(0), args(1), args(2), args(3)).asInstanceOf[T]
      case f: Function5[Any, Any, Any, Any, Any, Any] => f(args(0), args(1), args(2), args(3), args(4)).asInstanceOf[T]
      case _ => args(0)
    }
  }

  /**
  Implicit conversion from Boolean to Int
  Useful in string operations, eg: "string" * Boolean => {"string" if True else "}
   */
  implicit def bool2int(b:Boolean): Int = if (b) 1 else 0

  /**
  Traverse a program as a graph using DFS and gradually construct a String that represents a the Program
  eg:
    val program = Node(add, "add", Node(mul, "mul", a, Node(ln, "ln", b)), c)
    prettyPrint(program) => ' add ( mul ( 5.0 , ln ( 7.0 ) ) , 10.0 ) '
   */
  def prettyPrint[A](program: Program[A]): String = {

    @tailrec
    def loop(toVisit: List[Program[A]], visited: List[String] = Nil, acc: String = "", n_args: List[Int] = Nil): String = toVisit match {

      case Leaf(id, name, value) :: tail if !visited.contains(id) =>
        val n_args_filtered = dropWhile[Int](n_args, x => x == 1)
        val accumulator = "%s %s%s%s".format(acc, name, " )" * (n_args.length - n_args_filtered.length), " ," * n_args_filtered.nonEmpty)
        val arguments_to_pass = if (n_args_filtered.nonEmpty) List(n_args_filtered.head - 1) ::: n_args_filtered.tail else Nil

        loop(toVisit.tail, visited ::: List(id), accumulator, arguments_to_pass)


      case Node(id, name, f, args@_*) :: tail if !visited.contains(id) =>
        loop(args.toList ::: toVisit.tail, visited ::: List(id), acc + " " + name + " (", List(args.length) ::: n_args)

      case _ =>
        acc.trim
    }

    loop(List(program))
  }

}
