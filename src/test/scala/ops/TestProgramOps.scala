//package core
//
//import org.scalatest.FunSuite
//import ops.ProgramOps.{resolve}
//
//class TestProgramOps extends FunSuite {
//
//  test("An empty Set should have size 0") {
//    assert(Set.empty.size == 0)
//  }
//
//  test("We should be able to evaluate a Program (eg a single Leaf) into a single element of type A") {
//    val program: Program[Double] = Leaf(value=5.0)
//    val result = resolve(program)
//    assert(result == 5.0)
//  }
//
//  test("We should be able to evaluate a Program (eg a single Node) into a single element of type A") {
//    val add = (x: Double, y: Double) => x + y
//    val program: Program[Double] = Node(add, 2.0, 1.0)
//    val result = resolve(program)
//    assert(result == 3.0)
//  }
//
//}