package top

import language.implicitConversions

object Extensions {

  implicit class RichString(val str: String) extends AnyVal {
    def segments(p: String) = str.split(p)
  }

  class RichString2(val str: String) extends AnyVal {
    def segments2 = str.split("/")
  }

  implicit def makeRichString2(str: String) = new RichString2(str)

  implicit def strToInt(str: String) = str.length
  implicit def strToInt2(str: String) = str.length
}
