package api

import akka.event.Logging
import akka.http.scaladsl.server.Directives.{get, getFromResource, logRequest, path, pathSingleSlash}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._

/**
  * Routes for static files
  */
trait StaticFilesAPI {
  val staticRoute: Route =
    pathSingleSlash {
      get {
        logRequest(("static", Logging.InfoLevel)) {
          getFromResource("index.html")
        }
      }
    } ~
    path("code.js") {
      get {
        logRequest(("static", Logging.InfoLevel)) {
          getFromResource("code.js")
        }
      }
    } ~
    path("style.css") {
      get {
        logRequest(("static", Logging.InfoLevel)) {
          getFromResource("style.css")
        }
      }
    }
}
