
trait Ord[T] {
  def lessThan(a: T, b: T): Boolean
}

def sort[T](xs: List[T])(implicit ord: Ord[T]): List[T] = xs match {
  case Nil          => Nil
  case head :: tail => insert(head, sort(tail))
}

def insert[T](x: T, xs: List[T])(implicit ord: Ord[T]): List[T] = xs match {
  case Nil                                   => List(x)
  case head :: tail if ord.lessThan(x, head) => x :: xs
  case head :: tail                          => head :: insert(x, tail)
}

sort(List(2, 6, 8, 2, 5, 4, 1, 0, 9, 8))
