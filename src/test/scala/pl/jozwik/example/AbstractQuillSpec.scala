package pl.jozwik.example

import io.getquill.{H2JdbcContext, SnakeCase}
import org.scalatest.BeforeAndAfterAll
import pl.jozwik.quillgeneric.quillmacro.sync.Queries

trait AbstractQuillSpec extends AbstractSpec with BeforeAndAfterAll {
  lazy protected val ctx = new H2JdbcContext(SnakeCase, "h2") with Queries

  override def afterAll(): Unit = {
    ctx.close()
    super.afterAll()
  }
}
