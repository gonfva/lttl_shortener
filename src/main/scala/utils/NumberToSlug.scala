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
