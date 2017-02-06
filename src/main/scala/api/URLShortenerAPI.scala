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

import akka.event.Logging
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.typesafe.config.Config
import service._

/**
  * It contains the routes and invokes the services
  */
trait URLShortenerAPI extends Protocols {
  val service: URLService
  val domain: String
  def route: Route =
    pathSingleSlash {
      post {
        logRequest(("shorten", Logging.InfoLevel)) {
          entity(as[LongURL]) { longURL =>
            complete {
              service.shorten(longURL) match {
                case Left(msg) => (BadRequest, msg)
                case Right(slug) => slugToURL(slug)
              }
            }
          }
        }
      }
    } ~
    pathSingleSlash {
      get {
        logRequest(("index", Logging.InfoLevel)) {
          getFromResource("index.html")
        }
      }
    } ~
    path("code.js") {
      get {
        logRequest(("index", Logging.InfoLevel)) {
          getFromResource("code.js")
        }
      }
    } ~
    path("style.css") {
      get {
        logRequest(("index", Logging.InfoLevel)) {
          getFromResource("style.css")
        }
      }
    } ~
    path(Remaining) { shortURL =>
      get {
        logRequest(("expand", Logging.InfoLevel)) {
          service.expand(Slug(shortURL)) match {
            case Left(msg) => complete(NotFound, msg)
            case Right(expanded) => redirect(expanded.url, TemporaryRedirect)
          }
        }
      }
    }

  private def slugToURL(slug: Slug): String = {
    domain+slug.slug
  }
}

trait URLApiWithMockService extends URLShortenerAPI {
  val config: Config
  val service = new SimpleURLService
  lazy val domain = config.getString("http.external_domain")
}

