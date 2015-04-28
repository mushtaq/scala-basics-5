trait Num[T] {
  def zero: T
  def add(a: T, b: T): T
}
val xs = List(1, 2, 3, 4)
def sum[T](xs: List[T])(num: Num[T]): T =
  xs.foldLeft(num.zero)(num.add)

val intNum = new Num[Int] {
  def zero = 0
  def add(a: Int, b: Int) = a + b
}

sum(xs)(intNum)


