trait Num[T <: Num[T]] {
  def zero: T
  def add(other: T): T
}
case class IntNum(x: Int) extends Num[IntNum] {
  def zero = IntNum(0)
  def add(other: IntNum) = IntNum(x + other.x)
}
val xs = List(1, 2, 3, 4)
def sum[T <: Num[T]](xs: List[T]): T =
  xs.foldLeft(xs.head.zero)(_ add _)
sum(xs.map(IntNum)).x
