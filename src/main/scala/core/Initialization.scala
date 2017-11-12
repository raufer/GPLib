package core

import models.{FunctionSet, Program, TerminalSet}

import scala.util.Random

object Initialization {

  /**
  We assume the strategy is expecting a random generator type to facilitate testing

  The currying here is used to exploit type inference
  Otherwise the user will need to specify the type constructor of the strategy
   */

  def generatePopulation[T](ts: TerminalSet[T], fs: FunctionSet,  size: Int, seed: Int = -1)
                           (strategy: (TerminalSet[T], FunctionSet, Random) => Program[T]): List[Program[T]] = {

    val random = if (seed != -1) new Random(seed) else new Random()

    List.range(0, size).map(_ => strategy(ts, fs, random))

  }

}
