import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import api.URLApiWithMockService
import com.typesafe.config.ConfigFactory


object Server extends App with URLApiWithMockService {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  val logger = Logging(system, getClass)

  Http().bindAndHandle(route, config.getString("http.interface"), config.getInt("http.port"))
}