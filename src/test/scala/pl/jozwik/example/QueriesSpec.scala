package pl.jozwik.example

import java.time.LocalDate

import org.scalatest.TryValues._
import pl.jozwik.example.model.{Address, AddressId, Person, PersonId}
import pl.jozwik.example.repository.{AddressRepository, PersonRepository}

import scala.util.Try

class QueriesSpec extends AbstractQuillSpec {


  private lazy val personRepository = new PersonRepository(ctx, "Person")
  private lazy val personRepositoryAutoIncrement = new PersonRepository(ctx, "Person2")
  private lazy val addressRepository = new AddressRepository(ctx, "Address")

  private val generateId = true


  "QueriesSync " should {
    "Call all operations on Person" in {
      val addressId = AddressId(1)
      val address = Address(addressId, "Poland", "Warsaw", Option("Podbipiety"))
      val person = Person(PersonId(1), "firstName", "lastName", LocalDate.now, Option(addressId))
      val notExisting = Person(PersonId(2), "firstName", "lastName", LocalDate.now, Option(addressId))
      ctx.transaction {
        personRepository.all shouldBe Try(Seq())
        addressRepository.create(address) shouldBe 'success
        val id = addressRepository.create(address).success.value
        personRepository.create(person.copy(addressId = Option(id))) shouldBe 'success
      }
      personRepository.read(notExisting.id).success.value shouldBe empty
      personRepository.read(person.id).success.value shouldBe Option(person)
      personRepository.update(person) shouldBe 'success
      personRepository.all shouldBe Try(Seq(person))
      personRepository.delete(person.id) shouldBe 'success
      personRepository.all shouldBe Try(Seq())

    }

    "Call all operations on Person2 with auto generated id" in {
      val person = Person(PersonId.empty, "firstName", "lastName", LocalDate.now)
      personRepositoryAutoIncrement.all shouldBe Try(Seq())
      val personId = personRepositoryAutoIncrement.create(person, generateId)
      val personIdProvided = personId.success.value
      val createdPatron = personRepositoryAutoIncrement.read(personIdProvided).success.value.getOrElse(fail())
      personRepositoryAutoIncrement.update(createdPatron) shouldBe 'success
      personRepositoryAutoIncrement.all shouldBe Try(Seq(createdPatron))

      personRepositoryAutoIncrement.delete(createdPatron.id) shouldBe 'success
      personRepositoryAutoIncrement.read(createdPatron.id).success.value shouldBe empty
      personRepositoryAutoIncrement.all shouldBe Try(Seq())
    }
  }
}
