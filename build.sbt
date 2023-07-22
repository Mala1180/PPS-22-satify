ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

val scalaTest = "org.scalatest" %% "scalatest" % "3.2.16" % Test
val scalaSwing = "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
val junitTest = "com.github.sbt" % "junit-interface" % "0.13.2" % Test

lazy val root = (project in file("."))
  .settings(
    name := "satify",
    libraryDependencies += scalaTest,
    libraryDependencies += scalaSwing,
    libraryDependencies += junitTest
)
