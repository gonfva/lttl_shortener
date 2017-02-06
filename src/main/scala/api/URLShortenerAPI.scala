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
  *
  * Created by gonzalo on 03/02/17.
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

