package pl.jozwik.example

import org.scalatest.TryValues._
import pl.jozwik.example.domain.model.{ Person, PersonId }
import pl.jozwik.example.domain.repository.PersonRepository
import pl.jozwik.example.repository.PersonRepositoryGen

import scala.util.{ Failure, Success }

trait PersonRepositoryImplSuite extends AbstractQuillSpec {

  private lazy val repository: PersonRepository = new PersonRepositoryGen(ctx, "Person2")

  "MyPersonRepository" should {
    "Call all operations on Person2 with auto generated id" in {
      val person = Person(PersonId.empty, "firstName", "lastName", today)

      repository.all shouldBe Success(Seq())
      val personId = repository.create(person, generateId) match {
        case Failure(th) =>
          logger.error("", th)
          fail(th.getMessage, th)
        case Success(value) =>
          value
      }
      val createdPatron = repository.read(personId).success.value.getOrElse(fail())
      repository.update(createdPatron) shouldBe 'success
      repository.all shouldBe Success(Seq(createdPatron))

      repository.delete(createdPatron.id) shouldBe 'success
      repository.read(createdPatron.id).success.value shouldBe empty
      repository.all shouldBe Success(Seq())
    }
  }
}
