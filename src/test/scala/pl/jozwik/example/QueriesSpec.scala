package pl.jozwik.example

import java.time.LocalDate

import org.scalatest.TryValues._
import pl.jozwik.example.model.{Address, AddressId, Person, PersonId}
import pl.jozwik.example.repository.{AddressRepository, PersonRepository}

import scala.util.{Failure, Success, Try}

class QueriesSpec extends AbstractQuillSpec {


  private lazy val personRepository = new PersonRepository(ctx, "Person")
  private lazy val personRepositoryAutoIncrement = new PersonRepository(ctx, "Person2")
  private lazy val addressRepository = new AddressRepository(ctx, "Address")

  private val generateId = true
  private val addressId = AddressId(1)

  "QueriesSync " should {
    "Call all operations on Person" in {
      val address = Address(addressId, "Poland", "Warsaw", Option("Podbipiety"))
      val person = Person(PersonId(1), "firstName", "lastName", LocalDate.now, Option(addressId))
      val notExisting = Person(PersonId(2), "firstName", "lastName", LocalDate.now, Option(addressId))
      ctx.transaction {
        personRepository.all shouldBe Try(Seq())
        val addressIdTry = addressRepository.create(address)
        addressIdTry shouldBe 'success
        val id = addressIdTry.success.value
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
      val person = Person(PersonId(2), "firstName", "lastName", LocalDate.now)
      personRepositoryAutoIncrement.all shouldBe Try(Seq())
      val personId = personRepositoryAutoIncrement.create(person, generateId) match {
        case Failure(th) =>
          logger.error("", th)
          fail(th.getMessage, th)
        case Success(value) =>
          value
      }
      val createdPatron = personRepositoryAutoIncrement.read(personId).success.value.getOrElse(fail())
      personRepositoryAutoIncrement.update(createdPatron) shouldBe 'success
      personRepositoryAutoIncrement.all shouldBe Try(Seq(createdPatron))

      personRepositoryAutoIncrement.delete(createdPatron.id) shouldBe 'success
      personRepositoryAutoIncrement.read(createdPatron.id).success.value shouldBe empty
      personRepositoryAutoIncrement.all shouldBe Try(Seq())
    }
  }
}
