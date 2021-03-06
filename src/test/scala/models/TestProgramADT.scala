package models

import org.scalatest.FunSuite

class TestProgramADT extends FunSuite {

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("It should be possible to create a program just with a Leaf node whose value is constant") {
    val program = Leaf(Constant(5.0))
    assert(program.isInstanceOf[Leaf[Double]])
  }

  test("A simple 'Leaf' element should be able to hold any type of value") {
    case class Person(name: String)

    val terminal = Leaf(Constant(Person("John")))
    assert(terminal.isInstanceOf[Leaf[Constant[Person]]])
  }

  test("A 'Leaf' element can have a custom name or take as default the value string representation") {
    val leaf = Leaf(Constant(5, "custom name"))
    assert(leaf.terminal.name == "custom name")

    val leaf2 = Leaf(Constant(5.0))
    assert(leaf2.terminal.name == "5.0")
  }

  test("A 'Node' element should hold behaviour and accept other 'Programs' as input") {
    val add = Function((x: Double, y: Double) => x + y)

    val a = Leaf(Constant(5.0))
    val b = Leaf(Constant(7.0))

    val node = Node(add, a, b)
    assert(node.isInstanceOf[Node[Double]])
  }

  test("It should be possible to create a program that represents a function with a single argument") {
    val ln = Function((x: Double) => Math.log(x))
    val program = Node(ln, Leaf(Constant(10.0)))
    assert(program.isInstanceOf[Node[Double]])
  }

  test("It should be possible to create a 'initializer' node going from '() => A'") {
    val init = Function(() => 10.0)
    val program = Node(init)
  }

  test("We should be able to create nested Programs by nesting them") {

    val add = Function((x: Double, y: Double) => x + y)
    val mul = Function((x: Double, y: Double) => x * y)
    val ln = Function((x: Double) => Math.log(x))

    val a = Leaf(Constant(5.0))
    val b = Leaf(Constant(7.0))
    val c = Leaf(Constant(10.0))

    val program = Node(add, Node(mul, a, Node(ln, b)), c)

  }

  test("Every node of a program should have a unique ID") {

    val add = Function((x: Double, y: Double) => x + y)
    val mul = Function((x: Double, y: Double) => x * y)
    val ln = Function((x: Double) => Math.log(x))

    val a = Leaf(Constant(5.0))
    val b = Leaf(Constant(7.0))
    val c = Leaf(Constant(10.0))

    val program1 = Node(ln, a)
    val program2 = Node(add, a, b)
    val program3 = Node(mul, b, c)

    val ids = Seq(a.id, b.id, c.id, program1.id, program2.id, program3.id)

    assert(ids.size == Set(ids: _*).size)

  }
}