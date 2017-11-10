import org.scalatest.FunSuite


class TestInitialization extends FunSuite{

  test("The 'grow' initialization initializes all programs to respect a given defined depth") {

    val depth = 3

    val functionSet = List(
      (x: Int, y: Int) => x + y,
      (x: Int, y: Int) => x * y,
      (x: Int, y: Int) => x / y
    )

  }

}
