package service

import java.net.MalformedURLException

import org.scalatest.AsyncFlatSpec

/**
  * Created by gonzalo on 11/02/17.
  */
class ValidatorTest extends AsyncFlatSpec {
  private val BAD_URL = "htt://www.google.com"
  private val GOOD_URL = "http://www.google.com"

  it should "validate a correct URL" in {
    val futResult = service.checkValid(LongURL(GOOD_URL))
    futResult.map(_ => assert(true))
  }

  it should "flag a wrong URL" in {
    recoverToSucceededIf[MalformedURLException] {
      service.checkValid(LongURL(BAD_URL))
    }
  }

  var service: Validator = _

  override def withFixture(test: NoArgAsyncTest) = {
    service = new Validator {}
    super.withFixture(test)
  }
}
