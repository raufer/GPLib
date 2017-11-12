package core

import core.Initialization.generatePopulation
import core.InitializationStrategy.{full, grow, rampedHalfAndHalf}
import models.{Constant, Function, FunctionSet, TerminalSet}
import ops.ProgramOps.eval
import org.scalatest.{FunSuite, Matchers}
import utils.ProgramUtils.{maxDepth, minDepth}

import scala.util.Success


class TestInitialization extends FunSuite with Matchers {

  test("We should be able chose 'grow' as a initialization strategy to initialize a population of individuals") {

    val functionSet = FunctionSet(
      Function((x: Double, y: Double) => x + y),
      Function((x: Double, y: Double) => x * y),
      Function((x: Double, y: Double) => x / y)
    )

    val terminalSet = TerminalSet(
      Constant(5.0),
      Constant(4.0),
      Constant(10.0),
      Constant(7.0)
    )


    val initialPopulation = generatePopulation(terminalSet, functionSet, size=10)(full(depth=4))

    assert(initialPopulation.size == 10)

    initialPopulation.map(x => maxDepth(x)).should(equal(List.fill(10)(4)))
  }

  test("We should be able chose 'full' as a initialization strategy to initialize a population of individuals") {

    val functionSet = FunctionSet(
      Function((x: Double, y: Double) => x + y),
      Function((x: Double, y: Double) => x * y),
      Function((x: Double, y: Double) => x / y)
    )

    val terminalSet = TerminalSet(
      Constant(5.0),
      Constant(4.0),
      Constant(10.0),
      Constant(7.0)
    )


    val initialPopulation = generatePopulation(terminalSet, functionSet, size=10)(grow(maxDepth=7))

    assert(initialPopulation.size == 10)

    initialPopulation.foreach{x =>
      assert(maxDepth(x) <= 7)
      assert(minDepth(x) >= 1)
    }
  }

  test("We should be able chose 'ramped half-and-half' as a initialization strategy to initialize a population of individuals") {

    val functionSet = FunctionSet(
      Function((x: Double, y: Double) => x + y),
      Function((x: Double, y: Double) => x * y),
      Function((x: Double, y: Double) => x / y)
    )

    val terminalSet = TerminalSet(
      Constant(5.0),
      Constant(4.0),
      Constant(10.0),
      Constant(7.0)
    )


    val initialPopulation = generatePopulation(terminalSet, functionSet, size=10)(rampedHalfAndHalf(maxDepth=7))

    assert(initialPopulation.size == 10)

    initialPopulation.foreach{x =>
      assert(maxDepth(x) <= 7)
      assert(minDepth(x) >= 1)
    }
  }

  test("With a controlled primitive set that does not admit exceptions, every individual generated in the initial population should successfully evaluate") {

    val functionSet = FunctionSet(
      Function((x: Double, y: Double) => x + y),
      Function((x: Double, y: Double) => x * y),
      Function((x: Double, y: Double) => x / y),
      Function((x: Double) => Math.pow(x, 2))
    )

    val terminalSet = TerminalSet(
      Constant(2.0),
      Constant(3.0),
      Constant(1.5),
      Constant(1.0)
    )


    val initialPopulation = generatePopulation(terminalSet, functionSet, size=100)(grow(maxDepth=7))

    assert(initialPopulation.size == 100)

    initialPopulation.foreach{x =>
      assert(eval(x).isInstanceOf[Success[Double]])
    }
  }



}
