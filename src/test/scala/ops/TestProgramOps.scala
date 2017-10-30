package core

import gplib.{Leaf, Node, Program}
import org.scalatest.FunSuite
import ops.ProgramOps.eval

class TestProgramOps extends FunSuite {

  test("We should be able to evaluate a Program (eg a single Leaf) into a single element of type A") {
    val program = Leaf(value=5.0)
    val result = eval(program)
    assert(result == 5.0)
  }

  test("A Program representing the 'addition' function over the type 'Double' should be executable") {
    val program = Node((x:Double, y:Double) => x + y, Leaf(5.0), Leaf(11.0))
    val result = eval(program)
    assert(result == 16.0)
  }

}