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

import org.scalatest.FlatSpec
import service.URLService

/**
  * Test class to check we parse correctly a number
  */
class Number2SlugTest extends FlatSpec {

  it should "return 0 when invoked with a 0" in {
    assert(NumberToSlug(0L)=="0")
  }

  it should "return z when invoked with a 61" in {
    assert(NumberToSlug(61L)=="z")
  }

  it should "return 10 when invoked with a 62" in {
    assert(NumberToSlug(62L)=="10")
  }
}