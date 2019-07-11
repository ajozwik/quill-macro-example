

val `scala_2.12` = "2.12.8"

ThisBuild / organization := "pl.jozwik.demo"

ThisBuild / name := "quill-macro-example"

ThisBuild / scalacOptions ++= Seq(
  "-encoding", "utf8", // Option and arguments on same line
  "-Xfatal-warnings", // New lines for each options
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps"
)


val `org.scalatest_scalatest` = "org.scalatest" %% "scalatest" % "3.0.8" % "test"

val `org.scalacheck_scalacheck` = "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"

val `com.typesafe.scala-logging_scala-logging` = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

val `ch.qos.logback_logback-classic` = "ch.qos.logback" % "logback-classic" % "1.2.3"

lazy val root = project.in(file(".")).settings(
  libraryDependencies ++= Seq(
    `org.scalatest_scalatest`,
    `org.scalacheck_scalacheck`,
    `com.typesafe.scala-logging_scala-logging`,
    `ch.qos.logback_logback-classic`
  ),
  repositories := Seq(RepositoryDescription("pl.jozwik.model.Person", "pl.jozwik.model.PersonId", "pl.jozwik.repository.PersonRepository"))
)
  .enablePlugins(SbtQuillCrudGenericPlugin)


