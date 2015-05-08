organization := "com.github.scalax"

name := "play-json-ext"

scalaVersion := "2.11.6"

version := "0.0.1-SNAPSHOT"

resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json"   % "2.3.8" % "provided",
  "org.scalatest"     %% "scalatest"  % "2.2.1" % "test"
)
