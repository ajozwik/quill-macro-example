# quill-macro-example

Example of usage [sbt-quill-crud-generic](https://github.com/ajozwik/sbt-quill-crud-generic) plugin

- create model classes (must be visible during compilation)

```
package pl.jozwik.example.model

import java.time.LocalDate

import pl.jozwik.quillgeneric.quillmacro.WithId

final case class PersonId(value: Int) extends AnyVal

final case class Person(id: PersonId, firstName: String, lastName: String, birthDate: LocalDate) extends WithId[PersonId]
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

- enable auto plugin (in build.sbt):

```
  enablePlugins(QuillRepositoryPlugin)
```

