ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

val scalaTest = "org.scalatest" %% "scalatest" % "3.2.16" % Test
val scalaSwing = "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

lazy val root = (project in file("."))
  .settings(
    name := "satify",
    libraryDependencies += scalaTest,
    libraryDependencies += scalaSwing
)