import play.api.libs.json._
import top.Extensions._

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
//val input: JsObject = Json.obj(
//  "item" -> Json.obj(
//    "title" -> "title1 [BM]",
//    "name" -> "name1 [BM]",
//    "parts" -> Json.arr(
//      Json.obj(
//        "description" -> "good[BM] part [BM]",
//        "extra" -> Json.arr( "another[BM]", "some[BM]" ),
//        "bookmarks" -> Json.arr("one", "two", "abc", "xyz", "foo", "boo"),
//        "part" -> Json.obj(
//          "innerA" -> "Inner1[BM] bookmark",
//          "innerB" -> "Inner2[BM] bookmark"
//        ),
//        "instruction" -> "good part [BM]"
//      )
//    ),
//    "bookmarks" -> Json.arr("titleBM", "nameBM")
//  )
//)

def transform(json: JsValue): JsValue = _transform(json, List.empty[String])

def getBookmarks(jsonNode: JsValue, currentBookmark: List[String]): List[String] = {
  jsonNode.\("bookmarks").asOpt[List[String]] match {
    case Some(bookmarks) => bookmarks
    case None => currentBookmark
    case _ => println("you seem to be going crazy captain!! :O"); List.empty[String]
  }
}

def _countOfBmInStrings(jsonNode: JsValue, currentNode: JsValue):Int = {
  jsonNode match {
    case JsObject(jsList) => {
      jsList
        .dropWhile(_._2 != currentNode)
        .map(t => if(t._2.isInstanceOf[JsString]) t._2.as[String].countOfBm else 0)
        .sum
    }
    case _ => 0
  }
}

def _countOfBmInStringsAndArrays(jsonNode: JsValue, currentNode: JsValue):Int = {
  jsonNode match {
    case JsObject(jsList) => {
      val pair = jsList.span(_._2 != currentNode)
      val s1 = pair._1
        .dropWhile(t => !(t._2.isInstanceOf[JsArray]) && !(t._2.isInstanceOf[JsObject]))
        .map(t => t._2 match {
          case JsObject(o) => countOfBmInObject(o)
          case JsArray(a) => countOfBmInArray(a)
          case _ => 0
        })
        .sum
      val s2 = pair._2
        .map(t => if(t._2.isInstanceOf[JsString]) t._2.as[String].countOfBm else 0)
        .sum
      s1 + s2
    }
    case _ => 0
  }
}

def countOfBmInObject(jsObject: Seq[(String, JsValue)]): Int = {
  jsObject
    .map(t => t._2 match {
    case JsObject(o) => countOfBmInObject(o)
    case JsArray(a) => countOfBmInArray(a)
    case JsString(s) => s.countOfBm
    case _ => 0
  }).sum
}

def countOfBmInArray(jsArray: Seq[JsValue]): Int = {
  jsArray.map(t => t match {
    case JsObject(o) => countOfBmInObject(o)
    case JsArray(a) => countOfBmInArray(a)
    case JsString(s) => s.countOfBm
    case _ => 0
  }).sum
}

def removeUsed(jsonNode: JsValue, bookmark: List[String]): List[String] = {
  jsonNode match {
    case JsString(str) if str.contains("[BM]") => bookmark.drop(str.countOfBm)
    case _ => bookmark
  }
}

def removeExpected(jsonNode: JsValue, jsonParent: JsValue, bookmarks: List[String]): List[String] = {
  jsonNode match {
    case JsString(s) =>
      bookmarks
    case JsObject(o) =>
      bookmarks.drop(_countOfBmInStringsAndArrays(jsonParent, jsonNode))
    case JsArray(a) =>
      bookmarks.drop(_countOfBmInStrings(jsonParent, jsonNode))
    case _ =>
      bookmarks
  }
}

case class BookmarkAndArray(bookmarks: List[String], jsArray: List[JsValue])
case class BookmarkAndObject(bookmarks: List[String], jsObject: List[(String, JsValue)])

def _transform(json: JsValue, bookmarks: List[String]): JsValue = {
  json match {
    case JsObject(jsPairs) => {
      val seed = BookmarkAndObject(getBookmarks(json, bookmarks), List.empty[(String, JsValue)])
      JsObject(
        jsPairs
          .foldLeft(seed)
            ((seed, pair) =>
              BookmarkAndObject(
                removeUsed(pair._2, seed.bookmarks),
                (pair._1 -> _transform(pair._2, removeExpected(pair._2, json, seed.bookmarks))) :: seed.jsObject
              )
            )
          .jsObject
          .reverse
      )
    }
    case JsArray(jsList) => {
      val seed = BookmarkAndArray(getBookmarks(json, bookmarks), List.empty[JsValue])
      JsArray(
        jsList
          .foldLeft(seed)
            ((seed, elm) =>
              BookmarkAndArray(
                removeUsed(elm, seed.bookmarks),
                _transform(elm, removeExpected(elm, json, seed.bookmarks)) :: seed.jsArray)
              )
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