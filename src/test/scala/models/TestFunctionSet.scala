package models

import org.scalatest.{FunSuite}
import org.scalatest.Matchers

class TestFunctionSet extends FunSuite with Matchers {

  test("We should be able to create a Function set by passing a variable number of functions") {

    val add = (x: Int, y: Int) => x + y
    val mul = (x: Int, y: Int) => x * y
    val div = (x: Int, y: Int) => x / y

    val fs = FunctionSet(add, mul, div)
  }

  test("If we dont specify a name for them they it will be generated for us") {

    val add = (x: Int, y: Int) => x + y
    val mul = (x: Int, y: Int) => x * y
    val div = (x: Int, y: Int) => x / y

    val fs = FunctionSet(add, mul, div)

    fs.names.should(equal(List("function0", "function1", "function2")))

  }

  test("Or we can provide concrete names") {

    val add = (x: Int, y: Int) => x + y
    val mul = (x: Int, y: Int) => x * y
    val div = (x: Int, y: Int) => x / y

    val fs = FunctionSet((add, "add"), (mul, "mul"), (div, "div"))

    fs.names.should(equal(List("add", "mul", "div")))
  }

}