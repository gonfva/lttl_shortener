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

import java.util.concurrent.atomic.AtomicInteger

import utils.NumberToSlug

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Simple class to provide in memory functionality
  */
class SimpleURLService extends URLService with Validator {
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

  override def shorten(longUrl: LongURL): Future[Slug] = {
    checkValid(longUrl).map { url =>
      mapURLSlug.get(url).map(Slug(_)).getOrElse(createSlug(longUrl))
    }
  }

  override def expand(shortUrl: Slug): Future[LongURL] = {
    Future(
      mapSlugURL.
        get(shortUrl.slug).
        map(_.toString).
        map(url => LongURL(url)).
        getOrElse(throw new Exception("URL doesn't exist"))
    )
  }
}
