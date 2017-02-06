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
package utils

import scala.annotation.tailrec

/**
  * Created by gonzalo on 04/02/17.
  */
object NumberToSlug {
  val alphabet="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
  def apply(number: Long): String = {
    @tailrec
    def processDigit(currentValue: Long, currentList: List[Char] = Nil): List[Char] = {
      val quotient = currentValue/alphabet.length
      val modulo = (currentValue % alphabet.length).asInstanceOf[Int]
      quotient match {
        case q if q>0 => processDigit(q, alphabet(modulo) :: currentList)
        case _ => alphabet(modulo) :: currentList
      }
    }

    processDigit(number).mkString
  }
}
