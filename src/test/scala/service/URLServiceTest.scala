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

import java.net.MalformedURLException

import org.scalatest._


/**
  * Base test class for the service
  */
abstract class URLServiceTest extends AsyncFlatSpec {
  var service: URLService

  private val BAD_URL = "htt://www.google.com"
  private val GOOD_URL = "http://www.google.com"

  it should "fail to shorten a wrong URL" in {
    recoverToSucceededIf[MalformedURLException] {
      service.shorten(LongURL(BAD_URL))
    }
  }

  it should "always return the same shortened slug for the same URL" in {
    val pair = for {
      v1 <- service.shorten(LongURL(GOOD_URL))
      v2 <- service.shorten(LongURL(GOOD_URL))
    } yield (v1,v2)
    pair.map(pair => assert(pair._1==pair._2))
  }

  it should "expand a shortened url to the same original URL" in {
    val shortened = service.shorten(LongURL(GOOD_URL))
    shortened.flatMap(service.expand(_)).map(l => assert(l == LongURL(GOOD_URL)))
  }
}
