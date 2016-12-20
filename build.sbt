name := "liftjson-scalajson-interop"

organization := "me.frmr.jsonutils"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.1"

crossScalaVersions := Seq("2.11.8", "2.12.1")

libraryDependencies ++= Seq(
  "net.liftweb"    %% "lift-json"       % "3.0.1"      % "provided",
  "org.mdedetrich" %% "scala-json-ast"  % "1.0.0-M6"   % "provided",

  "org.scalatest"  %% "scalatest"       % "3.0.1"      % "test",
  "org.scalacheck" %% "scalacheck"      % "1.13.4"     % "test"
)

scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation")
