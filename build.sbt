name := "GPLib"

version := "0.1"

scalaVersion := "2.12.1"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"

libraryDependencies += "com.github.dwickern" %% "scala-nameof" % "1.0.3" % "provided"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2"
)