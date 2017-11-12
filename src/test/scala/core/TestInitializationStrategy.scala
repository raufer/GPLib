package core

import core.InitializationStrategy.{full, grow}
import models.{Constant, Function, FunctionSet, TerminalSet}
import org.scalatest.{FunSuite, Matchers}
import utils.ProgramUtils.{maxDepth, minDepth}

import scala.util.Random


class TestInitializationStrategy extends FunSuite with Matchers {

  test("The 'full' initialization strategy, receives depth as a parameter and randomly generates individuals where all of the leafs are the same depth") {

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

    val random = new Random(0)

    List.range(0, 10).map(_ => full(depth=4)(terminalSet, functionSet, random)).foreach{ x =>
      assert(maxDepth(x) == 4)
      assert(minDepth(x) == 4)
    }

  }

  test("In the 'grow' initialization strategy, just the maximum depth is defined, but the minimum is allowed to vary between [1, max depth]") {

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

    val random = new Random(0)

    var found_different_min_max = false

    List.range(0, 10).map(_ => grow(maxDepth=4)(terminalSet, functionSet, random)).foreach{ x =>
      assert(maxDepth(x) <= 4)
      assert(minDepth(x) > 0)
      if (maxDepth(x) != minDepth(x)) {
        found_different_min_max = true
      }
    }

    assert(found_different_min_max)
  }

}
