package service

import java.net.URL

import scala.util.{Success, Try}

/**
  * Trait to define what should the service do.
  *
  */
trait URLService {
  def checkValid(longURL: LongURL): Either[String, String] = {
    try {
      new URL(longURL.url)
      Right(longURL.url)
    } catch {
      case e: Exception =>
        Left("Not a valid URL")
    }
  }
  def shorten(longUrl: LongURL): Either[String, Slug]
  def expand(slug: Slug): Either[String, LongURL]
}
