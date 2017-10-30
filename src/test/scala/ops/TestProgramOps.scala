package core

import gplib.{Leaf, Node, Program}

import scala.util.{Try, Success, Failure}

import org.scalatest.FunSuite
import ops.ProgramOps.eval

class TestProgramOps extends FunSuite {

  test("The evalutaion of syntactally correct program with no domain violations should result in a successful evaluation") {
    val program = Node((x:Double, y:Double) => x / y, Leaf(5.0), Leaf(11.0))
    val result = eval(program)
    assert(result.isInstanceOf[Success[Double]])
  }


  test("We should be able to evaluate a Program (eg a single Leaf) into a single element of type A") {
    val program = Leaf(value=5.0)
    val result = eval(program)
    assert(result == Success(5.0))
  }

  test("A Program representing the 'addition' function over the type 'Double' should be executable") {
    val program = Node((x:Double, y:Double) => x + y, Leaf(5.0), Leaf(11.0))
    val result = eval(program)
    assert(result == Success(16.0))
  }

  test("We should be able to execute a nested program and obtain a correct result: 01") {
    val add = (x: Double, y: Double) => x + y
    val mul = (x: Double, y: Double) => x * y
    val ln = (x: Double) => Math.log(x)

    val a = Leaf(5.0)
    val b = Leaf(1.0)
    val c = Leaf(10.0)

    val program = Node(add, Node(mul, a, Node(ln, b)), c)
    val result = eval(program)
    assert(result == Success(10.0))
  }

  test("We should be able to execute a nested program and obtain a correct result: 02") {
    val add = (x: Double, y: Double, z: Double, w: Double) => x + y + z + w

    val program = Node(add, Leaf(5.0), Leaf(7.0), Leaf(10.0), Leaf(3.0))
    val result = eval(program)
    assert(result == Success(25.0))
  }

  test("We should be able to execute a nested program and obtain a correct result: 03") {
    val add = (x: Double, y: Double, z: Double, w: Double) => x + y + z + w
    val mul = (x: Double, y: Double, z: Double) => x * y * z
    val sub = (x: Double, y: Double) => x - y
    val ln = (x: Double) => Math.log(x)

    val program = Node(mul, "mul",
      Leaf(5.0),
      Node(mul, "mul",
        Leaf(5.0),
        Node(ln, "ln",
          Leaf(7.0)),
        Node(ln, "ln",
          Node(sub, "sub",
            Leaf(7.0),
            Leaf(5.0)))),
      Node(add, "add",
        Leaf(5.0),
        Leaf(7.0),
        Leaf(10.0),
        Leaf(3.0)))

    val result = eval(program)
    assert(result == Success(843.0013334004208))
  }

  test("We should be able to execute a nested program and obtain a correct result: 04") {
    val div = (x: Double, y: Double) => x / y

    val program = Node(div, Leaf(5.0), Leaf(0.0))

    val result = eval(program)
    assert(result == Success(Double.PositiveInfinity))
  }

  test("A syntactically wrong program should result in a failure") {

    val add = (x: Double, y: Double, z: Double) => x + y
    val mul = (x: Double, y: Double, z: Double) => x

    val program = Node(add, Node(mul, Leaf(5.0), Leaf(10.0)), Leaf(3.8))

    val result = eval(program)

    assert(result.isInstanceOf[Failure[Double]])

  }
}