package service

import spray.json.DefaultJsonProtocol

case class LongURL(url: String)
case class Slug(slug: String)

trait Protocols extends DefaultJsonProtocol {
  implicit val longURLFormat = jsonFormat1(LongURL.apply)
}
