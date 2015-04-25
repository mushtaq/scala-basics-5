import scala.annotation.tailrec
import scala.collection.mutable
def map(xs: List[Int], f: Int => Int): List[Int] = {
  val it = xs.iterator
  val result = mutable.Buffer.empty[Int]
  while (it.hasNext) {
    result += f(it.next())
  }
  result.toList
}
map(List(1, 2, 3, 4), x => x * x)
def map2(xs: List[Int], f: Int => Int): List[Int] = {
  val it = xs.iterator
  var result = List.empty[Int]
  while (it.hasNext) {
    result = f(it.next()) :: result
  }
  result.reverse
}
map2(List(1, 2, 3, 4), x => x * x)
def map3(xs: List[Int], f: Int => Int): List[Int] = {
  if(xs.isEmpty) xs else f(xs.head) :: map3(xs.tail, f)
}
map3((1 to 1000).toList, x => x * x)


def map4(xs: List[Int], f: Int => Int): List[Int] = {
  @tailrec
  def loop(remaining: List[Int], acc: List[Int]): List[Int] = {
    if(remaining.isEmpty) acc else {
      loop(remaining.tail, f(remaining.head) :: acc)
    }
  }
  loop(xs, List.empty).reverse
}

map4((1 to 100000).toList, x => x * x)

Seq(1).fold()