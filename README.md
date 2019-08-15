# quill-macro-example

[![Build Status](https://travis-ci.org/ajozwik/quill-macro-example.svg?branch=master)](https://travis-ci.org/ajozwik/quill-macro-example)

Example of usage [sbt-quill-crud-generic](https://github.com/ajozwik/sbt-quill-crud-generic) plugin

- create model classes (must be visible during compilation)

```
package pl.jozwik.example.domain.model

import java.time.LocalDate

import pl.jozwik.quillgeneric.quillmacro.WithId

object PersonId {
  val empty: PersonId = PersonId(0)
}

final case class PersonId(value: Int) extends AnyVal

final case class Person(
    id: PersonId,
    firstName: String,
    lastName: String,
    birthDate: LocalDate,
    addressId: Option[AddressId] = None) extends WithId[PersonId]

object AddressId {
  val empty: AddressId = AddressId(0)
}

final case class AddressId(value: Int) extends AnyVal

final case class Address(
    id: AddressId,
    country: String,
    city: String,
    street: Option[String] = None,
    buildingNumber: Option[String] = None,
    updated: Option[LocalDateTime] = None,
    localNumber: Option[String] = None) extends WithId[AddressId]

```

- add plugin (project/plugins.sbt)
```
addSbtPlugin("com.github.ajozwik" % "sbt-quill-crud-generic" % "<version>")
```

- add imports (to build.sbt):
```
import pl.jozwik.quillgeneric.sbt.RepositoryDescription
```
- define variables (optional)
```
val domainModelPackage = "pl.jozwik.example.domain.model"
val implementationPackage = "pl.jozwik.example.sync.impl"
```

- add settings (to build.sbt):
```
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
        Map("city" -> "city"))
```

Create your implementation (optional) use the same as `s"$implementationPackage.PersonRepositoryImpl[Dialect, Naming]"` in settings section:

```
package pl.jozwik.example.sync.impl

import java.time.LocalDate

import io.getquill.NamingStrategy
import io.getquill.context.sql.idiom.SqlIdiom
import pl.jozwik.example.domain.model.{ Person, PersonId }
import pl.jozwik.example.domain.repository.PersonRepository
import pl.jozwik.quillgeneric.quillmacro.sync.JdbcRepositoryWithGeneratedId

import scala.util.Try

trait PersonRepositoryImpl[Dialect <: SqlIdiom, Naming <: NamingStrategy]
  extends JdbcRepositoryWithGeneratedId[PersonId, Person, Dialect, Naming]
  with PersonRepository {

  def searchByFirstName(name: String)(offset: Int, limit: Int): Try[Seq[Person]] = {
    import context._
    searchByFilter((p: Person) =>
      p.firstName == lift(name) && p.lastName != lift(""))(offset, limit)(dynamicSchema)
  }

  def maxBirthDate: Try[Option[LocalDate]] = Try {
    import context._
    val r = dynamicSchema.map(p => p.birthDate)
    run(r.max)
  }

  def youngerThan(date: LocalDate)(offset: Int, limit: Int): Try[Seq[Person]] = {
    import context._
    searchByFilter((p: Person) => quote(p.birthDate > lift(date)))(offset, limit)(dynamicSchema)
  }

  def count: Try[Long] = {
    context.count((_: Person) => true)(dynamicSchema)
  }

}
```

- and domain trait 

```
package pl.jozwik.example.domain.repository

import java.time.LocalDate

import pl.jozwik.example.domain.model.{Person, PersonId}
import pl.jozwik.quillgeneric.quillmacro.sync.RepositoryWithGeneratedId

import scala.util.Try

trait PersonRepository extends RepositoryWithGeneratedId[PersonId, Person] {

  def count: Try[Long]

  def searchByFirstName(name: String)(offset: Int, limit: Int): Try[Seq[Person]]

  def maxBirthDate: Try[Option[LocalDate]]

  def youngerThan(date: LocalDate)(offset: Int, limit: Int): Try[Seq[Person]]

}
```

- enable auto plugin (in build.sbt):

```
  enablePlugins(QuillRepositoryPlugin)
```

- run compile task

```
sbt compile
```

The generated repositories are in:
```
target/scala-2.12/src_managed/main/
```

