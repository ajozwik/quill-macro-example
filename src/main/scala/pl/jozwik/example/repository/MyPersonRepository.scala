package pl.jozwik.example.repository

import java.time.LocalDate

import io.getquill.NamingStrategy
import io.getquill.context.sql.idiom.SqlIdiom
import pl.jozwik.example.model.{Person, PersonId}
import pl.jozwik.quillgeneric.quillmacro.sync.JdbcRepositoryWithGeneratedId

trait MyPersonRepository[Dialect <: SqlIdiom, Naming <: NamingStrategy]
  extends JdbcRepositoryWithGeneratedId[PersonId, Person, Dialect, Naming] {

  def max: Option[LocalDate] = {
    import context._
    val r = dynamicSchema.map(p => p.birthDate)
    run(r.max)
  }
}