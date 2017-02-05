package service

import java.util.concurrent.atomic.AtomicInteger

import utils.NumberToSlug

import scala.util.{Success, Try}

/**
  * Simple class to provide in memory functionality
  * Created by gonzalo on 04/02/17.
  */
class SimpleURLService extends URLService {
  val mapSlugURL = scala.collection.mutable.HashMap.empty[String,String]
  val mapURLSlug = scala.collection.mutable.HashMap.empty[String,String]

  private val counter = new AtomicInteger(0)
  def nextNumber = counter.getAndIncrement()


  def createSlug(longUrl: LongURL) = {
    val newSlugRef: String = NumberToSlug(nextNumber)
    mapSlugURL.put(newSlugRef, longUrl.url)
    mapURLSlug.put(longUrl.url, newSlugRef)
    Slug(newSlugRef)
  }

  override def shorten(longUrl: LongURL): Either[String, Slug] = {
    checkValid(longUrl).right.map { url =>
      mapURLSlug.get(url).map(Slug(_)).getOrElse(createSlug(longUrl))
    }
  }

  override def expand(shortUrl: Slug): Either[String, LongURL] = {
    mapSlugURL.get(shortUrl.slug).toRight("URL doesn't exist").right.map(url => LongURL(url))
  }
}
