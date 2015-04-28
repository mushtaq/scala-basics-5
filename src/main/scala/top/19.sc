val xs = List(5, 2, 9, 4, 2, 8, 5, 3, 1, 0, 7)

xs.filter(_ > 4)
xs.filterNot(_ > 4)
val (a, b) = xs.partition(_ > 4)

xs.take(5)
xs.drop(5)
val (x, y) = xs.splitAt(5)

xs.takeWhile(_ < 9)
xs.dropWhile(_ < 9)
val (m, n) = xs.span(_ < 9)

xs.exists(_ > 10)
xs.forall(_ > 2)

List.empty[Int].exists(_ > 10)
List.empty[Int].forall(_ > 10)

xs




