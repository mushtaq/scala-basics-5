import play.api.libs.json._
import scala.collection._
val expectedJsonStr = """{
  "item": {
  "title": "title1 [BM]",
  "name": "name1 [BM]",
  "parts": [
  {
  "description": "good[BM] part [BM]",
  "bookmarks": ["one", "two", "abc", "xyz"],
  "part": {
  "innerA": "Inner1[BM] bookmark",
  "innerB": "Inner2[BM] bookmark"
  },
  "instruction": "good part [BM]"
  }
  ],
  "bookmarks": ["titleBM", "nameBM"]
  }
}"""
val expectedJson = Json.parse(expectedJsonStr)
def replaceBM(str: String, bm: String):
Option[String] =
  str.replaceFirst("\\[BM\\]",s"\\[BM|$bm\\]") match {
    case x: String if x != str => Some(x)
    case _ => None
  }


def replaceBMs(str: String, bms: List[String] ):(String,List[String]) =
  bms match {
    case Nil => (str.replaceAll("\\[BM\\]",""),Nil)
    case x::xs => replaceBM(str,x) match {
      case Some(repStr) => replaceBMs(repStr,xs)
      case None => (str,bms)
    }
  }

def transformAttributes(attrs: Seq[(String,JsValue)],
  bookMarks: List[String]): (Seq[(String,JsValue)],List[String]) = {
  var bms: List[String] = bookMarks
  (attrs.map({
    (attr: (String, JsValue)) => attr match {
      case ("bookmarks", value) => ("bookmarks",value)
      case (name,value) =>
        transform(value,bms) match {
          case (v,b) =>
            bms = b
            (name,v)
        }
    }
  }),bms)
}

def transform(json: JsValue,bookMarks: List[String]):(JsValue,List[String]) =
  json match {
    case JsString(str) =>
      bookMarks match {
        case _ =>
          replaceBMs(str,bookMarks) match {
            case (repStr,bms) =>
              (JsString(repStr),bms)
          }
      }
    case JsArray(values) =>
      var bms = bookMarks
      (JsArray(values map {
        value =>
          val r = transform(value,bms)
          bms = r._2
          r._1
      }),bms)
    case JsObject(attrs) =>
      val bms = findBookMarks(attrs)
      bms match {
        case Some(bms) =>
          transformAttributes(attrs,bms) match {
            case (attrs,bms) => (JsObject(attrs),bms)
          }
        case None =>
          transformAttributes(attrs,bookMarks) match {
            case (attrs,bms) => (JsObject(attrs),bms)
          }
      }
    case x: JsValue => (x,bookMarks)
  }

def findBookMarks(attrs: Seq[(String, JsValue)]):
Option[List[String]] = {
  attrs.find({ x => x._1 == "bookmarks"}).
    flatMap({ attr =>
    attr match {
      case (name, JsArray(values)) =>
        Some(values.map( {
          value => value match {
            case JsString(str) => str
            case x => x.toString()
          }
        }).toList)
      case _ => None
    }
  })
}
Json.prettyPrint(transform(expectedJson, Nil)._1)