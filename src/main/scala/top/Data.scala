package top

import scala.io.Source

object Data {
  val bookText = Source.fromFile("/Users/mushtaq/projects/workshops/scala-basics-5/src/main/resources/aliceInWonderland.txt").mkString
  val stopWordText = Source.fromFile("/Users/mushtaq/projects/workshops/scala-basics-5/src/main/resources/stopWords.txt").mkString
}
