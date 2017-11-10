package utils

import models.{Constant, Leaf, Node}
import org.scalatest.FunSuite

class TestProgramUtils extends FunSuite {

  test("Any simple Program should have a suitable string representation for easier debug") {

    import utils.ProgramUtils.prettyPrint

    val program = Leaf(Constant(5.0))

    val repr = prettyPrint(program)

    assert(repr == "5.0")

  }

  test("Any simple Program with arbitrary arity should have a suitable string representation for easier debug") {

    import utils.ProgramUtils.prettyPrint

    val add = (x: Double, y: Double, z: Double, w: Double) => x + y + z + w

    val mul = (x: Double, y: Double, z: Double, w: Double) => x * y * z * w

    val program = Node(mul, Leaf(Constant(5.0)), Leaf(Constant(7.0)), Leaf(Constant(10.0)), Leaf(Constant(10.0)))
    val repr = prettyPrint(program)
    assert(repr == "f ( 5.0 , 7.0 , 10.0 , 10.0 )")


    val program2 = Node(mul, Node(add, "add", Leaf(Constant(5.0)), Leaf(Constant(5.0)),
      Leaf(Constant(7.0)), Leaf(Constant(10.0))), Leaf(Constant(7.0)), Leaf(Constant(10.0)))
    val repr2 = prettyPrint(program2)
    assert(repr2 == "f ( add ( 5.0 , 5.0 , 7.0 , 10.0 ) , 7.0 , 10.0 )")
  }

  test("Any more elaborated Program should also have a suitable string representation for easier debug") {

    import utils.ProgramUtils.prettyPrint

    val add = (x: Double, y: Double) => x + y
    val mul = (x: Double, y: Double) => x * y
    val ln = (x: Double) => Math.log(x)

    val a = Leaf(Constant(5.0))
    val b = Leaf(Constant(7.0))
    val c = Leaf(Constant(10.0))

    val program = Node(add, "add", Node(mul, "mul", a, Node(ln, "ln", b)), c)

    val repr = prettyPrint(program)

    assert(repr == "add ( mul ( 5.0 , ln ( 7.0 ) ) , 10.0 )")

  }

  test("Any complex Program should also have a suitable string representation for easier debug") {

    import utils.ProgramUtils.prettyPrint

    val add = (x: Double, y: Double, z: Double, w: Double) => x + y + z + w
    val mul = (x: Double, y: Double, z: Double) => x * y * z
    val sub = (x: Double, y: Double) => x - y
    val ln = (x: Double) => Math.log(x)

    val program = Node(mul, "mul",
      Leaf(Constant(5.0)),
      Node(mul, "mul",
        Leaf(Constant(5.0)),
        Node(ln, "ln",
          Leaf(Constant(7.0))),
        Node(ln, "ln",
          Node(sub, "sub",
            Leaf(Constant(5.0)),
            Leaf(Constant(7.0))))),
      Node(add, "add",
        Leaf(Constant(5.0)),
        Leaf(Constant(7.0)),
        Leaf(Constant(10.0)),
        Leaf(Constant(3.0))))

    val repr = prettyPrint(program)

    assert(repr == "mul ( 5.0 , mul ( 5.0 , ln ( 7.0 ) , ln ( sub ( 5.0 , 7.0 ) ) ) , add ( 5.0 , 7.0 , 10.0 , 3.0 ) )")

  }

}
