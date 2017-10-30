package ops

import models.Program
import utils.ProgramUtils.call

import scala.annotation.tailrec
import scala.util.Try

object ProgramOps {

  /**
  Auxiliary facility for manually keeping track of the stack when executing a Program in a tail recursive way
   */
  private type EvalResult[T] = (List[AnyRef], List[List[T]], List[Int])

  @tailrec
  private def evaluateWhile[C](l: List[AnyRef], arguments: List[List[C]], n_args: List[Int], f: Int => Boolean, acc: C): EvalResult[C] =
    n_args match {
      case h :: t if f(h) =>
        evaluateWhile(l.tail, arguments.tail, n_args.tail, f, call(l.head, arguments.head ::: List(acc)))
      case h :: t  =>
        (l, (List(acc) ::: arguments.head) :: arguments.tail,  List(n_args.head - 1) ::: n_args.tail)
      case _ =>
        (l, List(acc) :: arguments, n_args)
    }

  /**
  Executes a 'Program' and returns the result
  The program is executed recursively starting from the Leafs all the way back to the root using DFS
  Every Program[A] should resolve to a single A

  Returns a Try indicate if the evaluation of the program was successful or if some exception occurred
  It may not be trivial to impose domain constraints when generating random individuals
  eg.: Division by 0
   */
  def eval[A](program: Program[A]): Try[A] = {

    @tailrec
    def loop(toVisit: List[Program[A]], visited: List[String], functions: List[AnyRef], arguments: List[List[A]], n_args: List[Int]): A =

      toVisit match {

        case Leaf(id, name, value) :: tail if !visited.contains(id) => {

          val (functions_in_stack, args_in_stack, n_args_in_stack) =
            evaluateWhile[A](functions, arguments, n_args, x => x == 1, value)

          loop(toVisit.tail, visited ::: List(id), functions_in_stack, args_in_stack, n_args_in_stack)
        }

        case Node(id, name, f, args @_*) :: tail if !visited.contains(id) => {
          loop(args.toList ::: toVisit.tail, visited ::: List(id), f :: functions, List(Nil) ::: arguments, List(args.length ) ::: n_args)
        }

        case _ => arguments.flatten.head
      }
    Try(loop(List(program), Nil, Nil, Nil, Nil))
  }

}