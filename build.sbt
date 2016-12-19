name := "lift-commonjson-interop"

organization := "me.frmr.liftweb"

version := "0.0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "net.liftweb"    %% "lift-json"       % "3.0.1",
  "org.mdedetrich" %% "scala-json-ast"  % "1.0.0-M6",

  "org.scalatest"  %% "scalatest"       % "3.0.1" % "test",
  "org.scalacheck" %% "scalacheck"      % "1.13.4"% "test"
)

scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation")
