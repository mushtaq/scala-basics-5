package tc2

trait Monoid[T] {
  def zero: T
  def combine(a: T, b: T): T
}

object Monoid {
  implicit val intM = new Monoid[Int] {
    def zero = 0
    def combine(a: Int, b: Int) = a + b
  }

  implicit val strM = new Monoid[String] {
    def zero = ""
    def combine(a: String, b: String) = a + b
  }

  implicit def optionM[T](implicit tM: Monoid[T]) = new Monoid[Option[T]] {
    def zero = None

    def combine(a: Option[T], b: Option[T]) = (a, b) match {
      case (Some(x), Some(y)) => Some(tM.combine(x, y))
      case (None, _) => b
      case (_, None) => a
    }
  }

  implicit def pairM[T1, T2](implicit t1M: Monoid[T1], t2M: Monoid[T2]) = new Monoid[(T1, T2)] {
    def zero = (t1M.zero, t2M.zero)
    def combine(a: (T1, T2), b: (T1, T2)) = (t1M.combine(a._1, b._1), t2M.combine(a._2, b._2))
  }
}
