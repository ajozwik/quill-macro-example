package pl.jozwik.model

import java.time.LocalDate

final case class PersonId(value: Int) extends AnyVal

final case class Person(id: PersonId, firstName: String, lastName: String, birthDate: LocalDate)