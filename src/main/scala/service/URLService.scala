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

import java.net.URL

import java.net.MalformedURLException

import scala.concurrent.Future
import scala.util.{Success, Try}

/**
  * Trait to define what should the service do.
  *
  */
trait URLService {
  def shorten(longUrl: LongURL): Future[Slug]
  def expand(slug: Slug): Future[LongURL]
}

trait Validator {
  def checkValid(longURL: LongURL): Future[String] = {
    try {
      new URL(longURL.url)
      Future.successful(longURL.url)
    } catch {
      case e: MalformedURLException =>
        Future.failed(e)
    }
  }
}
