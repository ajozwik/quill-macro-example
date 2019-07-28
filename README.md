# quill-macro-example

[![Build Status](https://travis-ci.org/ajozwik/quill-macro-example.svg?branch=master)](https://travis-ci.org/ajozwik/quill-macro-example)

Example of usage [sbt-quill-crud-generic](https://github.com/ajozwik/sbt-quill-crud-generic) plugin

- create model classes (must be visible during compilation)

```
package pl.jozwik.example.model

import java.time.LocalDate

import pl.jozwik.quillgeneric.quillmacro.WithId

final case class PersonId(value: Int) extends AnyVal

final case class Person(id: PersonId, firstName: String, lastName: String, birthDate: LocalDate) extends WithId[PersonId]
```

- add plugin (project/plugins.sbt)
```
addSbtPlugin("com.github.ajozwik" % "sbt-quill-crud-generic" % "<version>")
```

- add imports (to build.sbt):
```
import pl.jozwik.quillgeneric.sbt.RepositoryDescription
import pl.jozwik.quillgeneric.sbt.QuillRepositoryPlugin._
```
- add settings (to build.sbt):
```
  generateDescription := Seq(
    RepositoryDescription("pl.jozwik.example.model.Person",
    "pl.jozwik.example.model.PersonId",
    "pl.jozwik.example.repository.PersonRepository"),
    RepositoryDescription("pl.jozwik.example.model.Address",
      "pl.jozwik.example.model.AddressId",
      "pl.jozwik.example.repository.AddressRepository")
      )
```

For simpler inject support (guice/spring) you can use own trait

```
package pl.jozwik.example

import java.time.LocalDate

import io.getquill.NamingStrategy
import io.getquill.context.sql.idiom.SqlIdiom
import pl.jozwik.quillgeneric.quillmacro.sync.JdbcRepository
import pl.jozwik.quillgeneric.sbt.model.{ Person, PersonId }

trait MyPersonRepository[Dialect <: SqlIdiom, Naming <: NamingStrategy]
  extends JdbcRepository[PersonId, Person, Dialect, Naming] {
  def max: Option[LocalDate] = {
    import context._
    val r = dynamicSchema.map(p => p.birthDate)
    context.run(r.max)
  }
}
```
and point to them
```
 RepositoryDescription("pl.jozwik.example.model.Person",
      "pl.jozwik.example.model.PersonId",
      "pl.jozwik.example.PersonRepository",
      Option("pl.jozwik.example.repository.MyPersonRepository[Dialect,Naming]")
  )
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

