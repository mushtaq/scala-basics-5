
val xs = List(2, 6, 8, 2, 5, 4, 1, 0, 9, 8)

xs.map(_ + 1)

xs.sortBy(x => (x, -x))

val m = Map.apply(1.->("a"), 2.->("b"))


m.map(t => t._2)

m.map { case (k, v) => v}

xs.collect {
  case x if x > 2 => x * x
}

xs.filter(_ > 2).map(x => x * x)

def divide(n: Int):PartialFunction[Int, Int] = {
  case x if x != 0 => n /x
}

val divider = divide(10)

divider.isDefinedAt(0)
divider.isDefinedAt(100)
