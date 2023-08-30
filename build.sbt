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
jacocoExcludes := Seq("*view*", "*update.Message*", "*Main*", "satify.features.*")

//disablePlugins(JacocoPlugin)

enablePlugins(CucumberPlugin)

CucumberPlugin.glues := List("satify.features")
CucumberPlugin.envProperties := Map("K" -> "2049")


//Test / fullClasspath := (Test / fullClasspath).value.filterNot(_.data.toString.contains("instrumented"))


//lazy val cuc = taskKey[Unit]("executes hey")
//cuc := {
//  Test / javaOptions := List("-Djacoco-agent.disable=true")
//  val result = CucumberPlugin.cucumber.inputTaskValue
//}
//lazy val example = taskKey[Unit]("d")
//Test / unmanagedClasspath ++= Seq(file("/target/scala-3.3.0/jacoco/instrumented-classes"))
//
// exclude from cucumber the following classpath "/target/scala-3.3.0/jacoco/instrumented-classes"

//CucumberPlugin.envProperties := Map("classpath" -> "/target/scala-3.3.0/classes")
//
//CucumberPlugin.plugin := List("pretty", "html:target/cucumber", "json:target/cucumber.json")

//example := {
//  val cp: Seq[File] = (Test / classpa).value.files
//  println(cp)
//}

//lazy val bddTest = taskKey[Unit]("d")
//bddTest := {
//  val args = "classpath:satify.features"
//  CucumberPlugin.run(args, Thread.currentThread.getContextClassLoader, Map("K" -> "2049"), "satify.Main")
//
//  //lassPath: List[sbt.File], env: Map[String,String], mainClass: String, javaOptions: Seq[String], cucumberParams: com.waioeka.sbt.CucumberParameters, logger: sbt.Logger)Int.
//  //Unspecified value parameters mainClass, javaOptions, cucumberParams...
//}
