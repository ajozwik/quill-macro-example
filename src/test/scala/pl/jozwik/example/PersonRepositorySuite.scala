package pl.jozwik.example

import org.scalatest.TryValues._
import pl.jozwik.example.domain.model.{ Address, Person, PersonId }
import pl.jozwik.example.domain.repository.PersonRepository
import pl.jozwik.example.repository.PersonRepositoryGen

import scala.util.Success

trait PersonRepositorySuite extends AbstractQuillSpec {

  private lazy val personRepository: PersonRepository = new PersonRepositoryGen(ctx, "Person")

  "Person uses fixed id " should {
    "Call all operations on Person" in {

      val address = Address(addressId, "Poland", "Warsaw", Option("Podbipiety"))
      val person = Person(PersonId(1), "firstName", "lastName", today.minusYears(2), Option(addressId))
      val notExisting = Person(PersonId(2), "firstName", "lastName", today, Option(addressId))
      ctx.transaction {
        personRepository.all shouldBe Success(Seq())
        val addressIdTry = addressRepository.create(address)
        addressIdTry shouldBe 'success
        val id = addressIdTry.success.value
        personRepository.create(person.copy(addressId = Option(id)), false) shouldBe 'success
      }
      personRepository.read(notExisting.id).success.value shouldBe empty
      personRepository.read(person.id).success.value shouldBe Option(person)
      personRepository.createOrUpdate(person) shouldBe 'success
      personRepository.all shouldBe Success(Seq(person))
      personRepository.youngerThan(person.birthDate.minusDays(1))(offset, limit) shouldBe Success(Seq(person))
      personRepository.delete(person.id) shouldBe 'success
      personRepository.all shouldBe Success(Seq())

    }
  }
}
