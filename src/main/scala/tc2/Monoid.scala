package tc2

trait Monoid[T] {
  def zero: T
  def combine(a: T, b: T): T
}

object Monoid {

  def apply[T: Monoid] = implicitly[Monoid[T]]

  implicit val intM = new Monoid[Int] {
    def zero = 0
    def combine(a: Int, b: Int) = a + b
  }

  implicit val strM = new Monoid[String] {
    def zero = ""
    def combine(a: String, b: String) = a + b
  }

  implicit def optionM[T: Monoid] = new Monoid[Option[T]] {
    def zero = None

    def combine(a: Option[T], b: Option[T]) = (a, b) match {
      case (Some(x), Some(y)) => Some(Monoid[T].combine(x, y))
      case (None, _) => b
      case (_, None) => a
    }
  }

  implicit def pairM[T1: Monoid, T2: Monoid] = new Monoid[(T1, T2)] {
    def zero = (Monoid[T1].zero, Monoid[T2].zero)
    def combine(a: (T1, T2), b: (T1, T2)) = (Monoid[T1].combine(a._1, b._1), Monoid[T2].combine(a._2, b._2))
  }
}
