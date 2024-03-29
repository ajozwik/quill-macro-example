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
  with PersonRepository[Try] {

  def searchByFirstName(name: String)(offset: Int, limit: Int): Try[Seq[Person]] = Try {
    import context._
    searchByFilter((p: Person) => p.firstName == lift(name) && p.lastName != lift(""))(offset, limit)(dynamicSchema)
  }

  def maxBirthDate: Try[Option[LocalDate]] = Try {
    import context._
    val r = dynamicSchema.map(p => p.birthDate)
    run(r.max)
  }

  def youngerThan(date: LocalDate)(offset: Int, limit: Int): Try[Seq[Person]] = Try {
    import context._
    searchByFilter((p: Person) => quote(p.birthDate > lift(date)))(offset, limit)(dynamicSchema)
  }

  def count: Try[Long] = Try {
    context.count((_: Person) => true)(dynamicSchema)
  }
}
