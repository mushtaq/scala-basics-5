import top.Data

val words: List[String] =
  Data.bookText.split("\\s+").take(100)
    .map(_.toLowerCase).toList
val stopWords: Set[String] =
  Data.stopWordText.split("\\s+")
    .map(_.toLowerCase).toSet

words.filterNot(stopWords)

stopWords: String => Boolean

stopWords("asdasd")

words: Int => String

words(10)

val m = Map(1 -> "a", 2 -> "b")


m: Int => String

val xs = Seq(1, 2)

xs.map(x => m(x))


words.zipWithIndex.toMap
words.zip(stopWords).toMap


