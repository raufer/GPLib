package models

import org.scalatest.{FunSuite}
import org.scalatest.Matchers

class TestFunctionSet extends FunSuite with Matchers {

  test("We should be able to create a Function set by passing a variable number of functions") {

    val add = Function((x: Int, y: Int) => x + y)
    val mul = Function((x: Int, y: Int) => x * y)
    val div = Function((x: Int, y: Int) => x / y)

    val fs = FunctionSet(add, mul, div)
  }

  test("If we dont specify a name for them they it will be generated for us") {


    val add = Function((x: Int, y: Int) => x + y)
    val mul = Function((x: Int, y: Int) => x * y)
    val div = Function((x: Int, y: Int) => x / y)

    val fs = FunctionSet(add, mul, div)

    fs.names.foreach { n => assert(n contains "function")}

  }

  test("Or we can provide concrete names") {

    val add = Function((x: Int, y: Int) => x + y, "add")
    val mul = Function((x: Int, y: Int) => x * y, "mul")
    val div = Function((x: Int, y: Int) => x / y, "div")

    val fs = FunctionSet(add, mul, div)

    fs.names.should(equal(List("add", "mul", "div")))
  }

}