package library

object Extensions {
  implicit class RichSeq[T](val xs: Seq[T]) extends AnyVal {
    def indexBy[K](f: T => K) = xs.map(x => f(x) -> x).toMap
  }
}
