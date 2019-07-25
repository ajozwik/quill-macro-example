package pl.jozwik.example

import pl.jozwik.example.model.{Person, PersonId}
import pl.jozwik.quillgeneric.quillmacro.sync.Repository

trait MyPersonRepository extends Repository[PersonId,Person]
