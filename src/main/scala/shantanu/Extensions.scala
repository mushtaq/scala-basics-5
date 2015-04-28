package shantanu

import play.api.libs.json.{JsString, JsArray, JsObject}

import scala.annotation.tailrec

/**
 * Created by ssinghal
 * Created on 26-Apr-2015
 * If you refactor this code, remember: Code so clean you could eat off it!
 */
object Extensions {

  @tailrec
  def substitute(str: String, bookmarks: List[String]): String = {
    if(str.contains("[BM]")) {
      if(bookmarks.length > 0) substitute(str.replaceFirst("\\[BM\\]", "\\[BM|" + bookmarks.head + "\\]"), bookmarks.drop(1))
      else substitute(str.replaceFirst("\\[BM\\]", ""), bookmarks)
    }
    else str
  }

  trait BmWalaTrait {
    def countOfBm
  }

  implicit class RichJsObject(jsObj: JsObject) {
    val JsObject(obj) = jsObj
    def countOfBm = {
      obj
        .map(t => t._2 match {
//        case JsObject(o) => o.countOfBm
//        case JsArray(a) => countOfBmInArray(a)
        case JsString(s) => s.countOfBm
        case _ => 0
      }).sum
    }
  }

  implicit class RichString(val str: String) extends AnyVal {

    def countOfBm = s"$str ".split("\\[BM\\]").length - 1

    def substituteBmUsing(bookmarks: List[String]) = if(str.contains("[BM]")) substitute(str, bookmarks) else str

  }
}