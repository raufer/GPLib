package core

import models._
import utils.ProgramUtils.arity_of

import scala.util.Random


/**
  Like in other evolutionary algorithms, in GP the individuals in the initial population are typically randomly generated.
  There are a number of different approaches to generating this random initial population.

  The most trivial methods strategies are 'grow', 'full' and 'ramped half and half'

  While these methods are easy to implement and use, they often make it difficult to control
  the statistical distributions of important properties such as the sizes and shapes of the generated trees.
  For example, the sizes and shapes of the trees generated via the grow method are highly sensitive to the sizes
  of the function and terminal sets.

  If, for example, one has significantly more terminals than functions, the grow method will almost always generate
  very short trees regardless of the depth limit. Similarly, if the number of functions is considerably greater than the number of terminals,
  then the grow method will behave quite similarly to the full method.

  The initial population need not be entirely random. If something is known about likely properties of the desired solution,
  trees having these properties can be used to seed the initial population.

  All of the strategies should receive a random generation type to facilitate testing

  The user  is just expected to define the first argument list of the strategy (the particular parameters of the strategy)
  The subsequent arguments should represent the common information needed by all strategies, hence the use of currying
 */
object InitializationStrategy {

  /**
  The full method generates trees where all the leaves are at the same depth.
  This does not necessarily mean that all initial trees will have an identical number of nodes
  (often referred to as the size of a tree) or the same shape.
  This only happens, in fact, when all the functions in the primitive set have an equal arity.
  Nonetheless, even when mixed-arity primitive sets are used, the range of program sizes and shapes produced by the full method may be rather limited.
   */

  def full[T](depth: Int)(ts: TerminalSet[T], fs: FunctionSet, random: Random = Random): Program[T] = {

    def delivery(individual: Program[T] = Zygote, current_depth: Int = 0): Program[T] = {

      if (current_depth < depth - 1) {
        val function = random.shuffle(fs.functions).take(1).head
        val arity = arity_of(function.f)
        val branches = List.range(0, arity.head).map(_ => delivery(Zygote, current_depth + 1))
        Node(function, branches:_*)
      }

      else {
        Leaf(random.shuffle(ts.terminals).take(1).head)
      }
    }
    delivery()
  }

  /**
  The grow method allows for the creation of trees of more varied sizes and shapes.
  Nodes are selected from the whole primitive set (i.e., functions and terminals) until the depth limit is reached.
  Once the depth limit is reached only terminals may be chosen (just as in the full method)
   */
  def grow[T](maxDepth: Int)(ts: TerminalSet[T], fs: FunctionSet, random: Random = Random): Program[T] = {

    def delivery(individual: Program[T] = Zygote, current_depth: Int = 0): Program[T] = {

      if ((current_depth < maxDepth - 1) && (random.nextInt(fs.size + ts.size) < fs.size)) {

        val function = random.shuffle(fs.functions).take(1).head
        val arity = arity_of(function.f)
        val branches = List.range(0, arity.head).map(_ => delivery(Zygote, current_depth + 1))
        Node(function, branches:_*)
      }

      else {
        Leaf(random.shuffle(ts.terminals).take(1).head)
      }
    }
    delivery()
  }

  /**
  Because neither the grow or full method provide a very wide array of sizes or shapes on their own,
    a combination of both was proposed called ramped half-and-half.
  Half the initial population is constructed using full and half is constructed using grow.
  This is done using a range of depth limits (hence the term “ramped”) to help ensure that we generate trees having a variety of sizes and shapes.
    */

  def rampedHalfAndHalf[T](maxDepth: Int)(ts: TerminalSet[T], fs: FunctionSet, random: Random = Random): Program[T] = {
    if (random.nextFloat < 0.5) {
      grow(maxDepth)(ts, fs, random)
    }
    else {
      full(maxDepth)(ts, fs, random)
    }
  }

}
