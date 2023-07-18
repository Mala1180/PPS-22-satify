ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

val scalatest = "org.scalatest" %% "scalatest" % "3.2.16" % Test

lazy val root = (project in file("."))
  .settings(
    name := "satify",
    libraryDependencies += scalatest
  )