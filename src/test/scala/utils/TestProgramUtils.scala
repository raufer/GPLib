package utils

import models._
import utils.ProgramUtils.arity_of
import utils.ProgramUtils.{maxDepth, minDepth}
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

    val add = Function((x: Double, y: Double, z: Double, w: Double) => x + y + z + w, "add")

    val mul = Function((x: Double, y: Double, z: Double, w: Double) => x * y * z * w, "f")

    val program = Node(mul, Leaf(Constant(5.0)), Leaf(Constant(7.0)), Leaf(Constant(10.0)), Leaf(Constant(10.0)))
    val repr = prettyPrint(program)
    assert(repr == "f ( 5.0 , 7.0 , 10.0 , 10.0 )")


    val program2 = Node(mul, Node(add, Leaf(Constant(5.0)), Leaf(Constant(5.0)),
      Leaf(Constant(7.0)), Leaf(Constant(10.0))), Leaf(Constant(7.0)), Leaf(Constant(10.0)))
    val repr2 = prettyPrint(program2)
    assert(repr2 == "f ( add ( 5.0 , 5.0 , 7.0 , 10.0 ) , 7.0 , 10.0 )")
  }

  test("Any more elaborated Program should also have a suitable string representation for easier debug") {

    import utils.ProgramUtils.prettyPrint

    val add = Function((x: Double, y: Double) => x + y, "add")
    val mul = Function((x: Double, y: Double) => x * y, "mul")
    val ln = Function((x: Double) => Math.log(x), "ln")

    val a = Leaf(Constant(5.0))
    val b = Leaf(Constant(7.0))
    val c = Leaf(Constant(10.0))

    val program = Node(add, Node(mul, a, Node(ln, b)), c)

    val repr = prettyPrint(program)

    assert(repr == "add ( mul ( 5.0 , ln ( 7.0 ) ) , 10.0 )")

  }

  test("Any complex Program should also have a suitable string representation for easier debug") {

    import utils.ProgramUtils.prettyPrint

    val add = Function((x: Double, y: Double, z: Double, w: Double) => x + y + z + w, "add")
    val mul = Function((x: Double, y: Double, z: Double) => x * y * z, "mul")
    val sub = Function((x: Double, y: Double) => x - y, "sub")
    val ln = Function((x: Double) => Math.log(x), "ln")

    val program = Node(mul,
      Leaf(Constant(5.0)),
      Node(mul,
        Leaf(Constant(5.0)),
        Node(ln,
          Leaf(Constant(7.0))),
        Node(ln,
          Node(sub,
            Leaf(Constant(5.0)),
            Leaf(Constant(7.0))))),
      Node(add,
        Leaf(Constant(5.0)),
        Leaf(Constant(7.0)),
        Leaf(Constant(10.0)),
        Leaf(Constant(3.0))))

    val repr = prettyPrint(program)

    assert(repr == "mul ( 5.0 , mul ( 5.0 , ln ( 7.0 ) , ln ( sub ( 5.0 , 7.0 ) ) ) , add ( 5.0 , 7.0 , 10.0 , 3.0 ) )")

  }

  test("The max depth of a 'empty' program is 0") {

    val program = Zygote

    val depth = maxDepth(program)

    assert(depth == 0)

  }

  test("The minimum depth of a 'empty' program is 0") {

    val program = Zygote

    val depth = minDepth(program)

    assert(depth == 0)

  }

  test("The max depth of a program with just one node is 1") {

    val program = Leaf(Constant(5.0))

    val depth = maxDepth(program)

    assert(depth == 1)

  }

  test("The minimum depth of a program with just one node is 1") {

    val program = Leaf(Constant(5.0))

    val depth = minDepth(program)

    assert(depth == 1)

  }

  test("We should be able to get a max depth a program, defined as the longest path from a leaf to the root") {

    val add = Function((x: Double, y: Double) => x + y)
    val mul = Function((x: Double, y: Double) => x * y)
    val ln = Function((x: Double) => Math.log(x))

    val a = Leaf(Constant(5.0))
    val b = Leaf(Constant(7.0))
    val c = Leaf(Constant(10.0))

    val program = Node(add, Node(mul, a, Node(ln, b)), c)

    val depth = maxDepth(program)

    assert(depth == 4)

  }

  test("We should be able to get a minimum depth a program, defined as the shortest path from any leaf to the root") {

    val add = Function((x: Double, y: Double) => x + y)
    val mul = Function((x: Double, y: Double) => x * y)
    val ln = Function((x: Double) => Math.log(x))

    val a = Leaf(Constant(5.0))
    val b = Leaf(Constant(7.0))
    val c = Leaf(Constant(10.0))

    val program = Node(add, Node(mul, a, Node(ln, b)), c)

    val depth = minDepth(program)

    assert(depth == 2)

  }

  test("We should be able to get a max depth a program, defined as the longest path from a leaf to the root 02") {

    val add = Function((x: Double, y: Double, z: Double, w: Double) => x + y + z + w)
    val mul = Function((x: Double, y: Double, z: Double) => x * y * z)
    val sub = Function((x: Double, y: Double) => x - y)
    val ln = Function((x: Double) => Math.log(x))

    val program = Node(mul,
      Leaf(Constant(5.0)),
      Node(mul,
        Leaf(Constant(5.0)),
        Node(ln,
          Leaf(Constant(7.0))),
        Node(ln,
          Node(sub,
            Leaf(Constant(5.0)),
            Leaf(Constant(7.0))))),
      Node(add,
        Leaf(Constant(5.0)),
        Leaf(Constant(7.0)),
        Leaf(Constant(10.0)),
        Leaf(Constant(3.0))))

    val depth = maxDepth(program)

    assert(depth == 5)

  }

  test("The arity of a function with no arguments (unit) is 0") {

    val unit = () => 2

    val arity = arity_of(unit)

    assert(arity == Some(0))

  }

  test("The arity of a function with two arguments is 2") {

    val add = (x: Double, y: Double) => x + y

    val arity = arity_of(add)

    assert(arity == Some(2))

  }

}
