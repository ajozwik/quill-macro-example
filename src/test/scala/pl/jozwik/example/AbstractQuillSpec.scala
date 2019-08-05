package pl.jozwik.example

import java.time.LocalDate

import io.getquill.{ H2JdbcContext, SnakeCase }
import org.scalatest.BeforeAndAfterAll
import pl.jozwik.example.domain.model.AddressId
import pl.jozwik.example.domain.repository.AddressRepository
import pl.jozwik.example.repository.AddressRepositoryGen
import pl.jozwik.quillgeneric.quillmacro.quotes.DateQuotes
import pl.jozwik.quillgeneric.quillmacro.sync.QuillCrudWithContext

trait AbstractQuillSpec extends AbstractSpec with BeforeAndAfterAll {
  lazy protected val ctx = new H2JdbcContext(SnakeCase, "h2") with QuillCrudWithContext with DateQuotes
  protected lazy val addressRepository: AddressRepository = new AddressRepositoryGen(ctx, "Address")
  protected val today: LocalDate = LocalDate.now()
  protected val (offset, limit) = (0, 100)
  protected val generateId = true
  protected val addressId = AddressId(1)

  override def afterAll(): Unit = {
    ctx.close()
    super.afterAll()
  }
}
