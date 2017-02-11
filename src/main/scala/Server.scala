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

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._
import akka.stream.ActorMaterializer
import api.{StaticFilesAPI, URLApiWithSimpleService}
import com.typesafe.config.ConfigFactory

/*
 * Main object server
 */
object Server extends App with URLApiWithSimpleService with StaticFilesAPI {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  val logger = Logging(system, getClass)
  val route: Route = staticRoute ~ shortenerRoutes

  Http().bindAndHandle(route, config.getString("http.interface"), config.getInt("http.port"))
}
