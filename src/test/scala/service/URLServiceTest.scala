package service

import org.scalatest.FlatSpec

/**
  * Base test class for the service
  *
  * Created by gonzalo on 04/02/17.
  */
abstract class URLServiceTest extends FlatSpec {
  var service: URLService

  private val BAD_URL = "htt://www.google.com"
  private val GOOD_URL = "http://www.google.com"

  it should "validate a correct URL" in {
    assert(service.checkValid(LongURL(GOOD_URL)).isRight)
  }

  it should "flag a wrong URL" in {
    assert(service.checkValid(LongURL(BAD_URL)).isLeft)
  }

  it should "fail to shorten a wrong URL" in {
    assert(service.shorten(LongURL(BAD_URL)).isLeft)
  }

  it should "always return the same shortened slug for the same URL" in {
    val first = service.shorten(LongURL(GOOD_URL))
    val second = service.shorten(LongURL(GOOD_URL))
    assert(first.isRight)
    assert(second.isRight)
    assert(first.right == second.right)
  }

  it should "expand a shortened url to the same original URL" in {
    val shortened = service.shorten(LongURL(GOOD_URL))
    assert(shortened.isRight)
    val expanded = service.expand(shortened.right.get)
    assert(expanded.isRight)
    assert(expanded.right.get == LongURL(GOOD_URL))
  }
}
