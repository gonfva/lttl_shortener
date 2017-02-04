import sbt._

name := "blackboard coding test"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.0.3"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.3"
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.0.3"
