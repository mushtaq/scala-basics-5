//package shantanu
//
//import play.api.libs.json.{JsArray, JsObject, JsString, JsValue}
//
//
//class BookmarkParser {
//
//  case class JsonPair(key : String, value : JsValue)
//
//  def parse(jsValue: JsValue, bookmarks : Iterator[String]) : JsValue = jsValue match {
//      case JsString(string) if string.contains("[BM]") => JsString(replaceAllSequentially(string, bookmarks))//JsString(bookmarkToken.replaceFirstIn(string, "[BM|" + bookmarks.next() + "]"))
//      case JsArray(pairs) => JsArray(pairs.map(jsValue => parse(jsValue, bookmarks)))
//      case JsObject(pairs) => val newBookmarks = getBookmarks(pairs).getOrElse(bookmarks)
//        JsObject(
//          pairs.sortBy(_._2.isInstanceOf[JsObject]).toList.map(pair => (
//            pair._1,
//            parse(pair._2, newBookmarks)
//            )
//          )
//        )
//      case a => a
//    }
//
//  def getBookmarks(pairs : Seq[(String, JsValue)]) : Option[Iterator[String]] = {
//    pairs.filter(pair => pair._1 == "bookmarks") match {
//      case Seq((key: String, JsArray(seq: Seq[JsString]))) => Option(seq.map(jsString => jsString.value).iterator)
//      case _ => None
//    }
//  }
//
//  def replaceAllSequentially(string : String, iterator: Iterator[String]) : String =
//    if(string.contains("[BM]"))
//      if(iterator.hasNext)
//        replaceAllSequentially(string.replaceFirst("\\[BM\\]", s"[BM|${iterator.next()}]"), iterator)
//      else
//        string.replaceAll("\\[BM\\]", "")
//    else
//      string
//
//
//
//}
