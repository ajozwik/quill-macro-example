import pl.jozwik.quillgeneric.sbt._

val `scalaVersion_2.12` = "2.12.9"

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("snapshots")

ThisBuild / scalaVersion := `scalaVersion_2.12`

ThisBuild / scapegoatVersion := "1.3.9"

ThisBuild / organization := "pl.jozwik.demo"

ThisBuild / scalacOptions ++= Seq(
  "-encoding",
  "utf8",             // Option and arguments on same line
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

val basePackage        = "pl.jozwik.example"
val domainModelPackage = s"$basePackage.domain.model"

val generateRepositoryPackage = s"$basePackage.repository"
val repositoryPackage         = s"$basePackage.impl"

val monixPackage           = s"$basePackage.monix"
val monixRepositoryPackage = s"$monixPackage.impl"

val generateMonixRepositoryPackage = s"$monixPackage.repository"

lazy val root = Project("quill-macro-example", file("."))
  .settings(
    generateDescription := Seq(
          RepositoryDescription(
            s"$domainModelPackage.Address",
            BeanIdClass(s"$domainModelPackage.AddressId"),
            s"$generateRepositoryPackage.AddressRepositoryGen",
            true,
            Option(s"$repositoryPackage.AddressRepositoryImpl[Dialect, Naming]"),
            None,
            Map("city" -> "city")
          ),
          RepositoryDescription(
            s"$domainModelPackage.Cell4d",
            BeanIdClass(s"$domainModelPackage.Cell4dId", KeyType.Composite),
            s"$generateRepositoryPackage.Cell4dRepositoryGen",
            false,
            None,
            None,
            Map("id.fk1" -> "x", "id.fk2" -> "y", "id.fk3" -> "z", "id.fk4" -> "t")
          ),
          RepositoryDescription(
            s"$domainModelPackage.Configuration",
            BeanIdClass(s"$domainModelPackage.ConfigurationId"),
            s"$basePackage.ConfigurationRepositoryGen",
            false,
            None,
            None,
            Map("id" -> "key")
          ),
          RepositoryDescription(
            s"$domainModelPackage.Person",
            BeanIdClass(s"$domainModelPackage.PersonId"),
            s"$generateRepositoryPackage.PersonRepositoryGen",
            true,
            Option(s"$repositoryPackage.PersonRepositoryImpl[Dialect, Naming]"),
            None
          ),
          RepositoryDescription(
            s"$domainModelPackage.Product",
            BeanIdClass(s"$domainModelPackage.ProductId"),
            s"$generateRepositoryPackage.ProductRepositoryGen",
            true
          ),
          RepositoryDescription(
            s"$domainModelPackage.Sale",
            BeanIdClass(s"$domainModelPackage.SaleId", KeyType.Composite),
            s"$generateRepositoryPackage.SaleRepositoryGen",
            false,
            None,
            None,
            Map("id.fk1" -> "productId", "id.fk2" -> "personId")
          )
        ),
    generateMonixRepositories ++= Seq(
          RepositoryDescription(
            s"$domainModelPackage.Address",
            BeanIdClass(s"$domainModelPackage.AddressId"),
            s"$generateMonixRepositoryPackage.AddressRepositoryGen",
            true,
            Option(s"$monixRepositoryPackage.AddressRepositoryImpl[Dialect, Naming]"),
            None,
            Map("city" -> "city")
          ),
          RepositoryDescription(
            s"$domainModelPackage.Cell4d",
            BeanIdClass(s"$domainModelPackage.Cell4dId", KeyType.Composite),
            s"$generateMonixRepositoryPackage.Cell4dRepositoryGen",
            false,
            None,
            None,
            Map("id.fk1" -> "x", "id.fk2" -> "y", "id.fk3" -> "z", "id.fk4" -> "t")
          ),
          RepositoryDescription(
            s"$domainModelPackage.Configuration",
            BeanIdClass(s"$domainModelPackage.ConfigurationId"),
            s"$generateMonixRepositoryPackage.ConfigurationRepositoryGen",
            false,
            None,
            None,
            Map("id" -> "key")
          ),
          RepositoryDescription(
            s"$domainModelPackage.Person",
            BeanIdClass(s"$domainModelPackage.PersonId"),
            s"$generateMonixRepositoryPackage.PersonRepositoryGen",
            true,
            Option(s"$monixRepositoryPackage.PersonRepositoryImpl[Dialect, Naming]"),
            None,
            Map("birthDate" -> "dob")
          ),
          RepositoryDescription(
            s"$domainModelPackage.Product",
            BeanIdClass(s"$domainModelPackage.ProductId"),
            s"$generateMonixRepositoryPackage.ProductRepositoryGen",
            true
          ),
          RepositoryDescription(
            s"$domainModelPackage.Sale",
            BeanIdClass(s"$domainModelPackage.SaleId", KeyType.Composite),
            s"$generateMonixRepositoryPackage.SaleRepositoryGen",
            false,
            None,
            None,
            Map("id.fk1" -> "productId", "id.fk2" -> "personId")
          )
        ),
    libraryDependencies ++= Seq(
          `org.scalatest_scalatest`,
          `org.scalacheck_scalacheck`,
          `com.typesafe.scala-logging_scala-logging`,
          `ch.qos.logback_logback-classic`,
          `com.h2database_h2` % Test
        )
  )
  .enablePlugins(QuillRepositoryPlugin)
