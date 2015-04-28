package shantanu

import play.api.libs.json.{JsString, JsArray, JsObject, JsValue}

/**
 * Created by poonams on 27/04/15.
 */

object Bookmark {

  var bookmarks: Option[List[String]] = None

  def transform(json: JsValue) = replaceBMs(json)

  def replaceBMs(json: JsValue): JsValue = {

    val currentBookmarks = (json \ "bookmarks").asOpt[List[String]]

    bookmarks = currentBookmarks match {
      case Some(currentBms) => bookmarks match {
        case Some(bms) => Option(currentBms ::: bms)
        case None      => Option(currentBms)
      }
      case None => bookmarks
    }

    json match {
      case JsString(str) if str.contains("[BM]")   =>
                val bookmark: String = bookmarks
                  .getOrElse(List.empty)
                  .headOption
                  .getOrElse("")
                bookmarks = Option(bookmarks.getOrElse(List.empty).drop(1))
                JsString(bookmark)
      case JsObject(Seq((key, a @ JsString(str)))) => JsObject(Seq((key, replaceBMs(a))))
      case JsObject(pairs)                         => JsObject(pairs.map(p => (p._1, replaceBMs(p._2))))
      case JsArray(elms)                           => JsArray(elms.map(replaceBMs(_)))
      case anyStr @ JsString(_)                    => anyStr
    }
  }
}