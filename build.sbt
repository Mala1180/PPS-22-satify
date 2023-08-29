ThisBuild / version := "0.1.0-SNAPSHOT"

val languageVersion = "3.3.0"
ThisBuild / scalaVersion := languageVersion

val scalaTest = "org.scalatest" %% "scalatest" % "3.2.16" % Test
val archUnit = "com.tngtech.archunit" % "archunit" % "1.0.0" % Test
val scalaSwing = "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
val slf4jSimpleLog = "org.slf4j" % "slf4j-simple" % "2.0.7" % Test
val scalaCompiler = "org.scala-lang" %% "scala3-compiler" % languageVersion
val cucumber = "io.cucumber" %% "cucumber-scala" % "8.14.1" % Test

lazy val root = (project in file("."))
  .settings(
    name := "satify",
    libraryDependencies ++= Seq(scalaTest, archUnit, slf4jSimpleLog, scalaSwing, scalaCompiler, cucumber),
    assembly / assemblyJarName := "satify.jar"
  )

jacocoReportSettings := JacocoReportSettings()
  .withTitle("Jacoco Satify Coverage Report")
  .withThresholds(
    JacocoThresholds(branch = 40, line = 70)
  )
  .withFormats(JacocoReportFormats.ScalaHTML)
jacocoExcludes := Seq("*view*", "*update.Message*", "*Main*")

enablePlugins(CucumberPlugin)

CucumberPlugin.glues := List("satify.features")
CucumberPlugin.envProperties := Map("K" -> "2049")
