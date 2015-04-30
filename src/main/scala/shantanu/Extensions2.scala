package top

import play.api.libs.json.{JsValue, JsString, JsArray, JsObject}

import scala.annotation.tailrec

/**
 * Created by ssinghal
 * Created on 26-Apr-2015
 * If you refactor this code, remember: Code so clean you could eat off it!
 */
object Extensions2 {
  
  implicit class RichJsObject(jsObj: JsObject) {
    def countOfBm = _countOfBmInObject(jsObj.fields)
  }

  implicit class RichJsArray(jsArr: JsArray) {
    def countOfBm = _countOfBmInArray(jsArr.value)
  }

  implicit class RichString(val str: String) extends AnyVal {
    def countOfBm = s"$str ".split("\\[BM\\]").length - 1
    def substituteBmUsing(bookmarks: List[String]) = if(str.contains("[BM]")) _substitute(str, bookmarks) else str
  }

  /*** PRIVATE METHODS ***/

  def counter(jsVal: JsValue) = jsVal match {
    case JsObject(o) => _countOfBmInObject(o)
    case JsArray(a) => _countOfBmInArray(a)
    case JsString(s) => s.countOfBm
    case _ => 0
  }

  def _countOfBmInArray(jsArray: Seq[JsValue]): Int = {
    jsArray.map(counter).sum
  }

  def _countOfBmInObject(jsObject: Seq[(String, JsValue)]): Int = {
    jsObject.map(t => counter(t._2)).sum
  }

  @tailrec
  def _substitute(str: String, bookmarks: List[String]): String = {
    if(str.contains("[BM]")) {
      if(bookmarks.length > 0) _substitute(str.replaceFirst("\\[BM\\]", "\\[BM|" + bookmarks.head + "\\]"), bookmarks.drop(1))
      else _substitute(str.replaceFirst("\\[BM\\]", ""), bookmarks)
    }
    else str
  }

}
