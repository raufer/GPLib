package utils

import gplib.{Leaf, Node, Program}

import scala.annotation.tailrec
import Recipes.dropWhile

object ProgramUtils {

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
  def prettyPrint[A](tree: Program[A]): String = {

    @tailrec
    def loop(toVisit: List[Program[A]], visited: List[String] = Nil, acc: String = "", n_args: List[Int] = Nil): String = toVisit match {

      case Leaf(id, name, value) :: tail if !visited.contains(id) =>
        val n_args_filtered = dropWhile[Int](n_args, x => x == 1)
        val comma = " ," * n_args_filtered.nonEmpty
        val acc_to_pass = acc + " " + name + " )" * (n_args.length - n_args_filtered.length) + comma
        val arguments_to_pass = if (n_args_filtered.nonEmpty) List(n_args_filtered.head - 1) ::: n_args_filtered.tail else Nil

        loop(toVisit.tail, visited ::: List(id), acc_to_pass, arguments_to_pass)


      case Node(id, name, f, args@_*) :: tail if !visited.contains(id) =>
        loop(args.toList ::: toVisit.tail, visited ::: List(id), acc + " " + name + " (", List(args.length) ::: n_args)

      case _ =>
        acc.trim
    }

    loop(List(tree))
  }

}
