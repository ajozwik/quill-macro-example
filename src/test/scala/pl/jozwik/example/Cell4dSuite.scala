package pl.jozwik.example

import pl.jozwik.example.domain.model.{ Cell4d, Cell4dId }
import pl.jozwik.example.repository.Cell4dRepositoryGen

trait Cell4dSuite extends AbstractQuillSpec {
  private val repository = new Cell4dRepositoryGen(ctx)

  "Cell4dSuite " should {
      "Call crud operations " in {
        val entity = Cell4d(Cell4dId(0, 1, 0, 1), false)
        repository.createOrUpdateAndRead(entity) shouldBe 'success

      }
    }
}
