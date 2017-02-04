package utils

import org.scalatest.FlatSpec
import service.URLService

/**
  * Test class to check we parse correctly a number
  *
  * Created by gonzalo on 04/02/17.
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