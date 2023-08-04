ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

val scalaTest = "org.scalatest" %% "scalatest" % "3.2.16" % Test
val archUnit = "com.tngtech.archunit" % "archunit" % "1.0.0" % Test
val scalaSwing = "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
val slf4jSimpleLog = "org.slf4j" % "slf4j-simple" % "2.0.7" % Test
val cucumber = "io.cucumber" %% "cucumber-scala" % "8.14.1" % Test


lazy val root = (project in file("."))
  .settings(
    name := "satify",
    libraryDependencies += scalaTest,
    libraryDependencies += archUnit,
    libraryDependencies += slf4jSimpleLog,
    libraryDependencies += scalaSwing,
    libraryDependencies += cucumber
  )
