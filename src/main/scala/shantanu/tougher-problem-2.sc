import play.api.libs.json._
import top.Extensions2._

val input: JsValue = Json.parse("""{
                                   |    "item": {
                                   |        "title": "title1 [BM]",
                                   |        "name": "name1 [BM]",
                                   |        "parts": [
                                   |            {
                                   |                "description": "good[BM] part [BM]",
                                   |                "bookmarks": ["4", "5", "6", "7", "8", "9"],
                                   |                "part1": {
                                   |                    "innerA": "Inner[BM] bookmark",
                                   |                    "innerB": "Inner[BM] bookmark"
                                   |                },
                                   |                "part2": {
                                   |                    "innerA": "Inner[BM] bookmark",
                                   |                    "innerB": "Inner[BM] bookmark"
                                   |                },
                                   |                "instruction": "good part [BM]"
                                   |            }
                                   |        ],
                                   |        "bookmarks": ["1", "2", "3"]
                                   |    }
                                   |}""".stripMargin)

def jsOrder(jsVal: JsValue): Int = {
  jsVal match {
    case JsObject(j) => 3
    case JsArray(j) => 2
    case JsString(j) => 1
    case _ => Int.MaxValue
  }
}

def sortJson(json: JsValue): JsValue = {
  json match {
    case JsObject(obj) =>
      JsObject(obj
        .map(x => x._1 -> sortJson(x._2))
        .sortBy(x => jsOrder(x._2)))
    case JsArray(arr) => JsArray(arr
      .map(sortJson(_))
      .sortBy(jsOrder(_)))
    case JsString(str) => JsString(str)
    case _ => println("how did we get here captain?!"); JsNull
  }
}

def transform(json: JsValue): JsValue = _transform(sortJson(json))(List.empty[String])

def getBookmarks(jsonNode: JsValue, currentBookmark: List[String]): List[String] = {
  jsonNode.\("bookmarks").asOpt[List[String]] match {
    case Some(bookmarks) => bookmarks
    case None => currentBookmark
    case _ => println("you seem to be going crazy captain!! :O"); List.empty[String]
  }
}

def removeUsed(jsonNode: JsValue, bookmark: List[String]): List[String] = {
  jsonNode match {
    case obj @ JsObject(o) => bookmark.drop(obj.countOfBm)
    case arr @ JsArray(a) => bookmark.drop(arr.countOfBm)
    case JsString(str) if str.contains("[BM]") => bookmark.drop(str.countOfBm)
    case _ => bookmark
  }
}

case class BookmarkAndArray(bookmarks: List[String], jsArray: List[JsValue]) {

  def transformObject(elm: JsValue): List[JsValue] = _transform(elm)(bookmarks) :: jsArray

  def transformBookmark(elm: JsValue): List[String] = removeUsed(elm, bookmarks)

  def updateFor(elm: JsValue): BookmarkAndArray = BookmarkAndArray(transformBookmark(elm), transformObject(elm))
}

case class BookmarkAndObject(bookmarks: List[String], jsObject: List[(String, JsValue)]) {

  def transformObject(pair: (String, JsValue)): List[(String, JsValue)] = pair._1 -> _transform(pair._2)(bookmarks) :: jsObject

  def transformBookmark(pair: (String, JsValue)): List[String] = removeUsed(pair._2, bookmarks)

  def updateFor(pair: (String, JsValue)): BookmarkAndObject = BookmarkAndObject(transformBookmark(pair), transformObject(pair))
}

def _transform(json: JsValue)(bookmarks: List[String]): JsValue = {
  val objSeed = BookmarkAndObject(getBookmarks(json, bookmarks), List.empty[(String, JsValue)])
  val arrSeed = BookmarkAndArray(getBookmarks(json, bookmarks), List.empty[JsValue])
  json match {
    case JsObject(jsPairs) => {
      JsObject(jsPairs
        .foldLeft(objSeed) ((seed, pair) => seed.updateFor(pair))
        .jsObject
        .reverse
      )
    }
    case JsArray(jsList) => {
      JsArray(jsList
        .foldLeft(arrSeed) ((seed, pair) => seed.updateFor(pair))
        .jsArray
        .reverse
      )
    }
    case JsString(str) =>
        JsString(str.substituteBmUsing(bookmarks))
    case _ => println("you might be wrong here captain!"); JsNull
  }
}
println(Json.prettyPrint(transform(input)))