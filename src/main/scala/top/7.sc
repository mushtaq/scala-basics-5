import top.Data

val words: List[String] =
  Data.bookText.split("\\s+")
    .map(_.toLowerCase).toList
val stopWords: Set[String] =
  Data.stopWordText.split("\\s+")
    .map(_.toLowerCase).toSet

words.view
  .filterNot(stopWords)
  .groupBy(identity)
  .mapValues(_.length)
  .toList
  .sortBy(_._2)
  .takeRight(10)
  .reverseMap(_._1)

