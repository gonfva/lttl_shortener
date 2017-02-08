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
package api

import akka.http.scaladsl.model.MediaTypes._
import akka.http.scaladsl.model.{HttpEntity, MediaTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import org.scalatest.{Matchers, WordSpec}
import service._


/**
  * Test for the API specification. Brought directly from the README file
  */
class StaticFilesAPITest extends WordSpec with Matchers with ScalatestRouteTest {
  var api: StaticFilesAPI = _

  "The front-end" should {
    "When we visit root, we get a page" in {
      Get("/") ~> api.staticRoute ~> check {
        response.status shouldBe StatusCodes.OK
        mediaType shouldBe `text/html`
        responseAs[String] should include ("URL Shortener")
      }
    }

    "Return javascript code" in {
      Get("/code.js") ~> api.staticRoute ~> check {
        response.status shouldBe StatusCodes.OK
        mediaType shouldBe `application/javascript`
      }
    }

    "Return css code" in {
      Get("/style.css") ~> api.staticRoute ~> check {
        response.status shouldBe StatusCodes.OK
        mediaType shouldBe `text/css`
      }
    }
  }
  override def withFixture(test: NoArgTest) = {
    api = new StaticFilesAPI {}
    super.withFixture(test)
  }
}
