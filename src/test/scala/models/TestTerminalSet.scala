package models

import org.scalatest.{FunSuite}
import org.scalatest.Matchers

class TestTerminalSet extends FunSuite with Matchers {

  test("We should be able to create a Terminal set by passing a variable number terminals, a sum type which can either by a constant or a variable") {

    val a = Constant(1)
    val b = Constant(1)
    val c = Constant(1)

    val x = Variable[Int](1, "x")

    val ts = TerminalSet(a, x)
  }

  test("The names are extracted from the values passed") {

    val a = Constant(1)
    val b = Constant(2)
    val c = Constant(3)

    val x = Variable[Int](1, "x")

    val ts = TerminalSet(a, b, c, x)

    ts.names.should(equal(List("1", "2", "3", "x")))

  }

}