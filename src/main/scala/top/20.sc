
val xs = (1 to 5).toList
val ys = ('a' to 'e').toList

xs.map(x => ys.map(y => (x, y))).flatten

xs
  .filter(_ > 3)
  .flatMap(x => ys.filter(_ == 'c').map(y => (x, y)))


val result = for {
  x <- xs
  if x > 3
  y <- ys
  if y == 'c'
} yield (x, y)

val result2 = for {
  x <- xs
  if x > 3
  y <- ys
  if y == 'c'
} println((x, y))

