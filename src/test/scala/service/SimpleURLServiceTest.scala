package service

/**
  * Created by gonzalo on 05/02/17.
  */
class SimpleURLServiceTest extends URLServiceTest {
  override var service: URLService = _

  override def withFixture(test: NoArgTest) = {
    service = new SimpleURLService
    super.withFixture(test)
  }
}
