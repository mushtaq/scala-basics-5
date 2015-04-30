
trait Ord[T] {
  def lessThan(a: T, b: T): Boolean
}

object Ord {
  def apply[T: Ord] = implicitly[Ord[T]]
}
def sortBy[T, U: Ord](xs: List[T])(f: T => U): List[T] = xs match {
  case Nil          => Nil
  case head :: tail => insertBy(head, sortBy(tail)(f))(f)
}

def insertBy[T, U: Ord](x: T, xs: List[T])(f: T => U): List[T] = xs match {
  case Nil                                   => List(x)
  case head :: tail if Ord[U].lessThan(f(x), f(head)) => x :: xs
  case head :: tail                          => head :: insertBy(x, tail)(f)
}

sortBy(List(2, 6, 8, 2, 5, 4, 1, 0, 9, 8))(identity)



