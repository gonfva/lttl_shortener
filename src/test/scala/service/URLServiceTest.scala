/**
  *      Copyright 2017 Gonzalo Fernandez-Victorio
  *
  *  Licensed under the Apache License, Version 2.0 (the "License");
  *  you may not use this file except in compliance with the License.
  *  You may obtain a copy of the License at
  *
  *       http://www.apache.org/licenses/LICENSE-2.0
  *
  *   Unless required by applicable law or agreed to in writing, software
  *   distributed under the License is distributed on an "AS IS" BASIS,
  *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  *   See the License for the specific language governing permissions and
  *   limitations under the License.
  **/
package service

import org.scalatest.FlatSpec

/**
  * Base test class for the service
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
