import pl.jozwik.quillgeneric.sbt.RepositoryDescription
import scalariform.formatter.preferences._

val `scala_2.12` = "2.12.8"

resolvers += Resolver.sonatypeRepo("releases")

ThisBuild / scapegoatVersion := "1.3.9"

ThisBuild / organization := "pl.jozwik.demo"

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

val `com.h2database_h2` = "com.h2database" % "h2" % "1.4.199"

val domainModelPackage = "pl.jozwik.example.domain.model"
val implementationPackage = "pl.jozwik.example.impl"

lazy val root = Project("quill-macro-example", file(".")).settings(
  generateDescription := Seq(
    RepositoryDescription(s"$domainModelPackage.Person",
      s"$domainModelPackage.PersonId",
      s"pl.jozwik.example.repository.PersonRepositoryGen",
      true,
      Option(s"$implementationPackage.PersonRepositoryImpl[Dialect, Naming]"),
      None),
    RepositoryDescription(s"$domainModelPackage.Address",
      s"$domainModelPackage.AddressId",
      "pl.jozwik.example.repository.AddressRepositoryGen",
      true,
      Option(s"$implementationPackage.AddressRepositoryImpl[Dialect, Naming]"),
      None,
      Map("city" -> "city")),
    RepositoryDescription(s"$domainModelPackage.Configuration",
      s"$domainModelPackage.ConfigurationId",
      "pl.jozwik.example.ConfigurationRepositoryGen",
      false,
      None,
      None,
      Map("id" -> "key")
    )
  ),
  scalariformPreferences := scalariformPreferences.value
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(DanglingCloseParenthesis, Preserve),
  libraryDependencies ++= Seq(
    `org.scalatest_scalatest`,
    `org.scalacheck_scalacheck`,
    `com.typesafe.scala-logging_scala-logging`,
    `ch.qos.logback_logback-classic`,
    `com.h2database_h2` % Test
  )
)
  .enablePlugins(QuillRepositoryPlugin)


