name := "lift-commonjson-interop"

organization := "me.frmr.liftweb"

version := "0.0.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "net.liftweb"    %% "lift-json"       % "3.0-M8",
  "scala-json-ast" %% "scala-json-ast"  % "1.0.0-SNAPSHOT"
)

enablePlugins(ScalaJSPlugin)
